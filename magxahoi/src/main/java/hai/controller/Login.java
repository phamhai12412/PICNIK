package hai.controller;

import hai.model.User;
import hai.service.PostServiceIMPL;
import hai.service.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class Login {

    @Autowired
    private UserServiceIMPL userServiceIMPL;
    @Autowired
    private PostServiceIMPL postServiceIMPL;
    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }



    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {

        if (user.getEmail().trim().isEmpty() || user.getEmail().trim().length() < 10 ||
                !user.getEmail().trim().matches("^[A-Za-z0-9+_.-]{3,}@[A-Za-z0-9.-]{3,}(\\.com?|\\.com\\.vn?|\\.vn)$")) {
            model.addAttribute("checklogin", "emailerr");
            return "login";
        }

        // Validate password
        if (user.getPassword().trim().length() < 5) {
            model.addAttribute("checklogin", "passerr");
            return "login";
        }


        User uselogin = userServiceIMPL.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (uselogin == null) {
            model.addAttribute("checklogin", "thất bại");
            return "login";
        } else if (!uselogin.isStatus()) {
            model.addAttribute("checklogin", "bị khóa");
            return "login";
        } else {
            model.addAttribute("checklogin", "thành công");
            model.addAttribute("uselogin", uselogin.getName());
            model.addAttribute("userole", uselogin.isRole());
            uselogin.setListidpostlike(postServiceIMPL.getAllLikedPostIdsForUser(uselogin.getId()));
            uselogin.setListidcommentlike(postServiceIMPL.getAllLikedCommentIdsForUser(uselogin.getId()));
            session.setAttribute("loggedInUser", uselogin);

            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }


}



