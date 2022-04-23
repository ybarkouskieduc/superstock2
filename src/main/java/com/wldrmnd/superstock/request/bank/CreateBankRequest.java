package com.wldrmnd.superstock.request.bank;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateBankRequest {

    private String name;
    private String description;
    private String country;
}
