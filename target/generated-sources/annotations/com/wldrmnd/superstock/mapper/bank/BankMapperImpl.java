package com.wldrmnd.superstock.mapper.bank;

import com.wldrmnd.superstock.domain.tables.records.BankRecord;
import com.wldrmnd.superstock.model.bank.Bank;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
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
