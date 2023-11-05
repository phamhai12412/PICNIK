package hai.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hai.dto.*;
import hai.model.Comment;
import hai.model.Post;
import hai.model.User;
import hai.service.CommentServic;
import hai.service.PostServiceIMPL;
import hai.service.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/timeline")
@PropertySource({"classpath:upload.properties","classpath:application.properties"})

public class Timelinecontroller{
    @Value("${upload-path}")
    private String pathUpload;
    @Autowired
    private UserServiceIMPL userServiceIMPL;
    @Autowired
    private PostServiceIMPL postServiceIMPL;
    @Autowired
    private CommentServic commentServic;
    @GetMapping("")
    public String timeline(Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if(loggedInUser==null){
            model.addAttribute("checklogin","no");
            return "404";
        }
        List<Postfindall> postfindalls = postServiceIMPL.getPostsByUserId(loggedInUser.getId());
        for (Postfindall p : postfindalls
        ) {
            p.setCommentbyidpostList(postServiceIMPL.getallcommentbyid(p.getPostId()));
        }
        model.addAttribute("posts", postfindalls);
        PostAdd postAdd = new PostAdd();
        model.addAttribute("post", postAdd);


        return "timeline";
    }

    @PostMapping("/add")
    public String ADD(@ModelAttribute("post") PostAdd post, HttpSession session) {
        Post newpost = new Post();
        if (post.getImgUrl().getSize() != 0) {
            File file = new File(pathUpload);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = post.getImgUrl().getOriginalFilename();
            try {
                FileCopyUtils.copy(post.getImgUrl().getBytes(), new File(pathUpload + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            newpost.setImgUrl(fileName);
        }

        newpost.setId(0);
        newpost.setContent(post.getContent());
        newpost.setUserId(((User) session.getAttribute("loggedInUser")).getId());
        postServiceIMPL.save(newpost);
        return "redirect:/timeline";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("postid") int id) {
        postServiceIMPL.delete(id);
        return "redirect:/timeline";
    }

    private static final Gson GSON = new GsonBuilder().create();


    @RequestMapping("/api/findById/{id}")
    public void findById(HttpServletResponse response, @PathVariable("id") Long id) {
        // gọi qua database để lấy dữ liệu
        Post post = postServiceIMPL.findById(Math.toIntExact(id));
        String data = GSON.toJson(post);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(200);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
        }

    }

    @PostMapping("/update")
    public ResponseEntity<String> updatePost(@RequestParam("id") int id, @RequestParam("content") String content) {
        Post post = postServiceIMPL.findById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        post.setContent(content);
        postServiceIMPL.save(post);
        return ResponseEntity.ok("Post updated successfully");
    }
    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestParam("postId") int postId, @RequestParam("isLiked") boolean isLiked, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");


        List<Integer> lisidpostlike=loggedInUser.getListidpostlike();

        if (isLiked) {
            lisidpostlike.remove((Integer)postId);
            postServiceIMPL.removePostLike(postId, loggedInUser.getId());
        } else {

            postServiceIMPL.AddPostLike(postId, loggedInUser.getId());
            lisidpostlike.add(postId);
        }

        session.setAttribute("loggedInUser", loggedInUser);

        return ResponseEntity.ok("Like/Unlike successful");
    }

    @PostMapping("/likecmt")
    public ResponseEntity<String> likeComment(@RequestParam("commentId") int commentId, @RequestParam("isLiked") boolean isLiked, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        List<Integer> listIdCommentLike = loggedInUser.getListidcommentlike();

        if (isLiked) {
            listIdCommentLike.remove((Integer) commentId);
            commentServic.removeCommentLike(loggedInUser.getId(),commentId);
        } else {
            listIdCommentLike.add(commentId);
            commentServic.addCommentLike(loggedInUser.getId(),commentId);

        }

        session.setAttribute("loggedInUser", loggedInUser);

        return ResponseEntity.ok("Like/Unlike successful");
    }
    @PostMapping("/addcomment")
    public ResponseEntity<String> AddComment(@RequestParam("postId") int postId, @RequestParam("commentText") String commentText, HttpSession session, HttpServletResponse response) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        int idnewpost=commentServic.AddComment(postId, loggedInUser.getId(), commentText);
        Commenadd commenadd=commentServic.GetCommentInfo(idnewpost);

        String data = GSON.toJson(commenadd);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(200);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
        }

        return ResponseEntity.ok(" successful");
    }

    @GetMapping("/deletecomment/{postId}/{commentId}")
    public ResponseEntity<Integer> deleteComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        int totalCommentCount = commentServic.DeleteCommentAndLikesAndGetRemainingCount(postId, commentId);

        return ResponseEntity.ok(totalCommentCount);
    }
    @PostMapping("/editcomment")
    public ResponseEntity<String> editComment(
            @RequestParam int commentId,
            @RequestParam String editedContent)
    {


        commentServic.editComment(commentId,editedContent);


        return ResponseEntity.ok(editedContent);
    }
    @GetMapping("/getcomment/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable("commentId") int commentId) {

        Comment comment = commentServic.getCommentById(commentId);



        return ResponseEntity.ok(comment);
    }
    @PostMapping("/update-avatar")
    public String updateAvatar(@RequestParam("newAvatar") MultipartFile newAvatar, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (!newAvatar.isEmpty()) {
            try {
                File file = new File(pathUpload);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String fileName = newAvatar.getOriginalFilename();
                FileCopyUtils.copy(newAvatar.getBytes(), new File(pathUpload + fileName));
loggedInUser.setAvatar(fileName);

                session.setAttribute("loggedInUser", loggedInUser);
                userServiceIMPL.updateUserAvatar(loggedInUser.getId(), fileName);

            } catch (IOException e) {
                throw new RuntimeException("Could not upload avatar: " + e.getMessage());
            }
        }
        return "redirect:/timeline"; // Chuyển hướng sau khi tải lên thành công
    }
}

