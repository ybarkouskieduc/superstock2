package com.wldrmnd.superstock.request.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserRequest {

    private String username;
    private String password;
}
