package com.wldrmnd.superstock.model.stock;

import lombok.Data;

import java.io.Serializable;

@Data
public class Stock implements Serializable {

    private Long id;
    private String name;
    private String sign;
    private String description;
}
