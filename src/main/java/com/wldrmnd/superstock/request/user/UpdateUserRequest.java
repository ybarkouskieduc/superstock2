package com.wldrmnd.superstock.request.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateUserRequest {

    private Long userId;
    private String username;
    private String password;
}
