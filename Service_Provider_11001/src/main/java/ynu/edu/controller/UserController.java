package ynu.edu.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ynu.edu.entity.User;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @GetMapping("/getUserById/{userId}")
    public User GetUserById(@PathVariable("userId") Integer userId){
        User user = new User(userId,"张三1","123456");
        return user;
    }
}
