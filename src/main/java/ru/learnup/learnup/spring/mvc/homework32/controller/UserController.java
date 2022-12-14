package ru.learnup.learnup.spring.mvc.homework32.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.learnup.learnup.spring.mvc.homework32.mapper.UserMapper;
import ru.learnup.learnup.spring.mvc.homework32.model.UserDto;
import ru.learnup.learnup.spring.mvc.homework32.service.UserService;
import ru.learnup.learnup.spring.mvc.homework32.view.UserView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/secured")
    public Object secured(Authentication authentication) {
        return authentication.getName();
    }


    @GetMapping
    public List<UserView> getUsers() {
        List<UserDto> users = userService.getUsers();
        return users.stream()
                .map(userMapper::mapToView)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userid}")
    public UserView getUser(@PathVariable(name = "userid") int userid) {
        UserDto user = userService.findById(userid);
        return userMapper.mapToView(user);
    }


    @PostMapping
    public UserView createUser(@RequestBody UserView user) {
        UserDto dto = userMapper.mapFromView(user);
        return userMapper.mapToView(
                userService.createUser(dto)
        );
    }

    @PutMapping("/{userid}")
    public UserView updateUser(@PathVariable(name = "userid") int userid,
                               @RequestBody UserView user) {
        UserDto dto = userMapper.mapFromView(user);
        dto = userService.updateUser(dto);
        return userMapper.mapToView(
                userService.createUser(dto)
        );
    }

    @DeleteMapping("/{userId}")
//    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable(name = "userId") int userId) {
        userService.delete(userId);
    }
}

