package com.wldrmnd.superstock.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
}
