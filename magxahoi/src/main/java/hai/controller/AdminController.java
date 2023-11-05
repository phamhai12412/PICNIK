package hai.controller;

import hai.dto.Postfindall;
import hai.dto.SeachPost;
import hai.model.User;
import hai.service.PostServiceIMPL;
import hai.service.UserServiceIMPL;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PropertySource({"classpath:upload.properties","classpath:application.properties"})
public class AdminController {
    @Value("${upload-path}")
    private String pathUpload;
    @Autowired
    private UserServiceIMPL userServiceIMPL;
    @Autowired
    private PostServiceIMPL postServiceIMPL;

    @GetMapping("")
    public String admin(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");


        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }

        List<User> userList = userServiceIMPL.getAllUsers();
        model.addAttribute("users", userList);

        return "account";
    }


    @GetMapping("/unlock_acc/{id}")

    public String unlockAcc(@PathVariable("id") Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }
        userServiceIMPL.updateUserStatus(id, true);
        return "redirect:/admin";
    }

    @GetMapping("/block_acc/{id}")
    public String blockAcc(@PathVariable("id") Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }
        userServiceIMPL.updateUserStatus(id, false);
        return "redirect:/admin";
    }

    @GetMapping("post")
    public String post(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }
        List<Postfindall> postfindalls = postServiceIMPL.getAllpostadmin();


        model.addAttribute("posts", postfindalls);
        return "postadmin";
    }

    @GetMapping("/unlock_post/{id}")
    public String unlockpost(@PathVariable("id") Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }
        postServiceIMPL.changePostStatus(id, true);
        return "redirect:/admin/post";
    }

    @GetMapping("/block_post/{id}")
    public String blockpost(@PathVariable("id") Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }
        postServiceIMPL.changePostStatus(id, false);
        return "redirect:/admin/post";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("seach") SeachPost seach,
                         Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }
        List<Postfindall> posts = new ArrayList<>();
        for (Postfindall p : postServiceIMPL.getAllpostadmin()) {
            if (p.getPostContent().toLowerCase().contains(seach.getSearchName().toLowerCase())) {
                posts.add(p);
            }
        }
        // sắp xếp
        switch (seach.getSort()) {
            case "like":
                if (seach.getBy().equalsIgnoreCase("ASC")) {
                    posts.sort(Comparator.comparingInt(Postfindall::getTotalLikes));
                } else {
                    posts.sort(Comparator.comparingInt(Postfindall::getTotalLikes).reversed());
                }
                break;
            case "comment":
                if (seach.getBy().equalsIgnoreCase("ASC")) {
                    posts.sort(Comparator.comparingInt(Postfindall::getTotalComments));
                } else {
                    posts.sort(Comparator.comparingInt(Postfindall::getTotalComments).reversed());
                }
                break;
        }
        model.addAttribute("seach", seach);
        model.addAttribute("posts", posts);
        return "postadmin";
    }

    @GetMapping("/searchUse")
    public String seachusse(@RequestParam("search") String search,
                            Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.isRole()) {
            return "404";
        }
        List<User> userList = userServiceIMPL.searchUsers(search);
        model.addAttribute("users", userList);

return "account";
    }
}