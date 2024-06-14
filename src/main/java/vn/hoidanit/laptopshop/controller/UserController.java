package vn.hoidanit.laptopshop.controller;


import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    // Dependency Injection
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handHello();
        model.addAttribute("test", test);
        return "hello";
    }
    @RequestMapping("/admin/user") // nếu chỉ truyền String vào thì mặc định là doGet
    public String getCreate(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String CreateUser(Model model, @ModelAttribute("newUser") User hoidanit)  {//thêm ModelAttribue ở bên form và controller
        // String test = this.userService.handHello();
        // model.addAttribute("test", test);
        System.out.println("be run" +hoidanit);
        return "hello";
    }
}

// @RestController
// public class UserController {

// // Dependency Injection
// private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.handHello();
// }

// }
