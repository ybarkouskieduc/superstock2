package com.wldrmnd.superstock.request.bank;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindBankRequest {

    private Long id;
    private String name;
    private boolean findAllOnEmptyCriteria;
}
