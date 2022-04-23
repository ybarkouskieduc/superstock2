package com.wldrmnd.superstock.request.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginUserRequest {

    private String username;
    private String password;
}
