package com.wldrmnd.superstock.request.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class FindUserRequest {

    private Long userId;
    private String username;
    private boolean findAllOnEmptyCriteria;
}
