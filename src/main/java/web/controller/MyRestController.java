package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public List<User> usersList() {
        return userService.getListUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.show(id);
    }

    @PostMapping("/admin/new")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PutMapping(value = "/admin/edit")
    public void update(@RequestBody User user){
        userService.update(user);
    }

    @DeleteMapping("/admin/delete/{id}")
    public void delete(@PathVariable Integer id) {
        userService.remove(id);
    }

}


