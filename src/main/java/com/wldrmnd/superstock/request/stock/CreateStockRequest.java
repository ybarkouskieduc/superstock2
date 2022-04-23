package com.wldrmnd.superstock.request.stock;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateStockRequest {

    private String name;
    private String sign;
    private String description;
}
