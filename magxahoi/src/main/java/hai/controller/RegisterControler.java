package hai.controller;

import hai.model.User;
import hai.service.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("register")
@PropertySource({"classpath:upload.properties","classpath:application.properties"})
public class RegisterControler {
    @Autowired
    private UserServiceIMPL userServiceIMPL;

    @GetMapping("")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("thongbao", "");
        return "register";
    }

    @PostMapping("/add")
    public String registerSubmit(@ModelAttribute User user, Model model) {

        if (user.getEmail().trim().isEmpty() || user.getEmail().trim().length() < 10 ||
                !user.getEmail().trim().matches("^[A-Za-z0-9+_.-]{3,}@[A-Za-z0-9.-]{3,}(\\.com?|\\.com\\.vn?|\\.vn)$")) {
            model.addAttribute("thongbao", "emailerr");
        } else if (user.getName().trim().isEmpty() || user.getName().trim().length() < 5) {
            model.addAttribute("thongbao", "usernameerr");
        } else if (user.getPassword().trim().length() < 5) {
            model.addAttribute("thongbao", "passerr");
        } else if (!user.getPhone().matches("^(\\+?84|0)\\d{9}$")) {
            model.addAttribute("thongbao", "phoneerr");
        } else if (userServiceIMPL.checkEmailExists(user.getEmail())) {
            model.addAttribute("thongbao", "thatbai");
        } else{

            model.addAttribute("thongbao", "thanhcong");
    userServiceIMPL.insertUser(user);
        }
        return "register";

    }
}