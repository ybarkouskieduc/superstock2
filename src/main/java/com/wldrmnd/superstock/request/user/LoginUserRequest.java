package com.wldrmnd.superstock.request.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@Data
public class LoginUserRequest implements Serializable {

    private String username;
    private String password;
}
