package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

@Controller
@RequestMapping("")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String index1(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentUser", currentUser);
        return "adminPage";
    }

//    @PostMapping("/admin/new")
//    public String post(@ModelAttribute("newUser") User user){
//        userService.addUser(user);
//        return "redirect:/admin";
//    }

    @PatchMapping(value = "/admin/edit")
    public String update(@ModelAttribute("updateUser") User user){
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String delete(@ModelAttribute("userForDelete") User user) {
        userService.remove(user.getId());
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showUser(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentUser", userService.show(currentUser.getId()));
        return "/userPage";
    }
}
