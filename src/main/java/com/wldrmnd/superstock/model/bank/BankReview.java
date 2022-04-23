package com.wldrmnd.superstock.model.bank;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class BankReview implements Serializable {

    private Long id;
    private Long userId;
    private Long bankId;
    private Integer rate;
    private String review;
    private Timestamp createdAt;
}
