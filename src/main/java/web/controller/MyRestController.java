package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> usersList() {
        return userService.getListUsers() != null &&  !userService.getListUsers().isEmpty()
                ? new ResponseEntity<>(userService.getListUsers(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User>  getUser(@PathVariable Integer id) {
        return userService.show(id) != null
                ? new ResponseEntity<>(userService.show(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/new")
    public ResponseEntity<Void> addUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/admin/edit")
    public ResponseEntity<Void> update(@RequestBody User user){
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


