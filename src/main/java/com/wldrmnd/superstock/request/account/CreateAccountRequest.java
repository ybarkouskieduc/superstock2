package com.wldrmnd.superstock.request.account;

import com.wldrmnd.superstock.model.bank.Currency;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class CreateAccountRequest implements Serializable {

    private Long userId;
    private Currency currency;
    private BigDecimal amount;
    private Boolean isDefault;

    CreateAccountRequest(Long userId, Currency currency, BigDecimal amount, Boolean isDefault) {
        this.userId = userId;
        this.currency = currency;
        this.amount = amount;
        this.isDefault = isDefault;
    }

    public static CreateAccountRequestBuilder builder() {
        return new CreateAccountRequestBuilder();
    }

    public static class CreateAccountRequestBuilder {
        private Long userId;
        private Currency currency;
        private BigDecimal amount;
        private Boolean isDefault;

        CreateAccountRequestBuilder() {
        }

        public CreateAccountRequestBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public CreateAccountRequestBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public CreateAccountRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public CreateAccountRequestBuilder isDefault(Boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public CreateAccountRequest build() {
            return new CreateAccountRequest(userId, currency, amount, isDefault);
        }

        public String toString() {
            return "CreateAccountRequest.CreateAccountRequestBuilder(userId=" + this.userId + ", currency=" + this.currency + ", amount=" + this.amount + ", isDefault=" + this.isDefault + ")";
        }
    }
}
