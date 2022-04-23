package com.wldrmnd.superstock.request.stock;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindStockRequest {

    private Long id;
    private String name;
    private String sign;
    private boolean findAllOnEmptyCriteria;
}
