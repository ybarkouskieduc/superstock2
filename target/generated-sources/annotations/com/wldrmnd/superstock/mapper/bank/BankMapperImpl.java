package com.wldrmnd.superstock.mapper.bank;

import com.wldrmnd.superstock.domain.tables.records.BankRecord;
import com.wldrmnd.superstock.model.bank.Bank;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-24T15:28:08+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class BankMapperImpl implements BankMapper {

    @Override
    public Bank toModel(BankRecord record) {
        if ( record == null ) {
            return null;
        }

        Bank bank = new Bank();

        if ( record.getId() != null ) {
            bank.setId( record.getId() );
        }
        if ( record.getName() != null ) {
            bank.setName( record.getName() );
        }
        if ( record.getDescription() != null ) {
            bank.setDescription( record.getDescription() );
        }
        if ( record.getCountry() != null ) {
            bank.setCountry( record.getCountry() );
        }
        if ( record.getCreatedAt() != null ) {
            bank.setCreatedAt( record.getCreatedAt() );
        }

        return bank;
    }
}
