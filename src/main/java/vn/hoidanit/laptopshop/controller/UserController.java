package vn.hoidanit.laptopshop.controller;


import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    // Dependency Injection
    private final UserService userService;
  
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        // List<User> listUser = this.userService.getFindAllUser();
        // System.out.println(listUser);
        // List<User> listByUser = this.userService.getFindByEmailUser("huy@gmail.com");
        // System.out.println(listByUser);
        // String test = this.userService.handHello();
        // model.addAttribute("test", test);
        return "hello";
    }
    @RequestMapping("/admin/user") // nếu chỉ truyền String vào thì mặc định là doGet
    public String getViewTableUser(Model model) {
        List<User> users = this.userService.getFindAllUser();
        // System.out.println(">>> check user: "+users);
        model.addAttribute("users", users);
        return "/admin/user/table-user";
    }
    @RequestMapping("/admin/user/{id}") 
    public String getDetailUserPage(Model model,@PathVariable Long id) {
        System.out.println("Check path id: "+id);
        model.addAttribute("id", id);
        User users = this.userService.handleDetailUser(id);
        model.addAttribute("userId", users);
        return "/admin/user/detail-user";
    }

    @RequestMapping("/admin/user/create") 
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String CreateUser(Model model, @ModelAttribute("newUser") User hoidanit)  {//thêm ModelAttribue ở bên form và controller
        // System.out.println("be run " +hoidanit);
        userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }
}
