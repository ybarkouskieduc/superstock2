package com.wldrmnd.superstock.controller;

import com.wldrmnd.superstock.mapper.UserRecordMapper;
import com.wldrmnd.superstock.model.user.User;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import com.wldrmnd.superstock.request.user.FindUserRequest;
import com.wldrmnd.superstock.request.user.LoginUserRequest;
import com.wldrmnd.superstock.request.user.UpdateUserRequest;
import com.wldrmnd.superstock.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserRecordMapper userRecordMapper;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll().stream().map(userRecordMapper::toModel).toList();
    }

    @GetMapping("/find")
    public User findById(@RequestBody FindUserRequest request) {
        return userRecordMapper.toModel(userService.find(request).stream().findFirst().get());
    }

    @PostMapping("/create")
    public User create(@RequestBody CreateUserRequest request) {
        return userRecordMapper.toModel(userService.createUserAndDefaultAccount(request));
    }

    @PutMapping("/update")
    public User update(@RequestBody UpdateUserRequest request) {
        return userRecordMapper.toModel(userService.update(request));
    }

    @GetMapping("/login")
    public User login(@RequestBody LoginUserRequest request) {
        return userRecordMapper.toModel(userService.login(request));
    }
}
