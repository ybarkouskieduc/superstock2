package com.wldrmnd.superstock.controller;

import com.wldrmnd.superstock.mapper.UserRecordMapper;
import com.wldrmnd.superstock.model.user.User;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import com.wldrmnd.superstock.request.user.FindUserRequest;
import com.wldrmnd.superstock.request.user.LoginUserRequest;
import com.wldrmnd.superstock.request.user.UpdateUserRequest;
import com.wldrmnd.superstock.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserRecordMapper userRecordMapper;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll().stream().map(userRecordMapper::toModel).toList();
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public User find(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false, defaultValue = "false") boolean loadAllOnEmptyCriteria
    ) {
        return userRecordMapper.toModel(userService.find(FindUserRequest.builder()
                .username(username)
                .userId(userId)
                .findAllOnEmptyCriteria(loadAllOnEmptyCriteria)
                .build()
        ).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("User is not found.")));
    }

    @PostMapping("/create")
    public User create(@RequestBody CreateUserRequest request) {
        return userRecordMapper.toModel(userService.createUserAndDefaultAccount(request));
    }

    @PutMapping("/update")
    public User update(@RequestBody UpdateUserRequest request) {
        return userRecordMapper.toModel(userService.update(request));
    }

    @PostMapping(value = "/login")
    public User login(@RequestBody LoginUserRequest request) {
        return userRecordMapper.toModel(userService.login(request));
    }
}
