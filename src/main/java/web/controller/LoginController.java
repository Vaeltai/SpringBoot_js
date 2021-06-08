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
@RequestMapping("/admin")
public class LoginController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index1(@ModelAttribute User user, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.getListUsers());
        model.addAttribute("addUser", new User());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "index1";
    }

    @PostMapping("/new")
    public String post(@ModelAttribute("newUser") User user){
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("updateUser") User user,
                         @PathVariable("id") Long id){
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("userForDelete") User user) {
        userService.remove(user.getId());
        return "redirect:/admin";
    }
}