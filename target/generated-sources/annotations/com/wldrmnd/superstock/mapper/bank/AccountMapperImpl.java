package com.wldrmnd.superstock.mapper.bank;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.model.bank.Account;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toModel(AccountRecord record) {
        if ( record == null ) {
            return null;
        }

        Account account = new Account();

        if ( record.getId() != null ) {
            account.setId( record.getId() );
        }
        if ( record.getUserId() != null ) {
            account.setUserId( record.getUserId() );
        }
        if ( record.getAmount() != null ) {
            account.setAmount( record.getAmount() );
        }
        if ( record.getDefault() != null ) {
            account.setDefault( record.getDefault() );
        }
        if ( record.getCurrency() != null ) {
            account.setCurrency( record.getCurrency() );
        }

        return account;
    }
}
