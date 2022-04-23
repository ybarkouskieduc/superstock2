package com.wldrmnd.superstock.model.bank;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Bank implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String country;
    private Timestamp createdAt;
}
