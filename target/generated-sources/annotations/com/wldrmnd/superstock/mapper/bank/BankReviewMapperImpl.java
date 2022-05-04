package com.wldrmnd.superstock.mapper.bank;

import com.wldrmnd.superstock.domain.tables.records.BankReviewRecord;
import com.wldrmnd.superstock.model.bank.BankReview;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class BankReviewMapperImpl implements BankReviewMapper {

    @Override
    public BankReview toModel(BankReviewRecord record) {
        if ( record == null ) {
            return null;
        }

        BankReview bankReview = new BankReview();

        if ( record.getId() != null ) {
            bankReview.setId( record.getId() );
        }
        if ( record.getUserId() != null ) {
            bankReview.setUserId( record.getUserId() );
        }
        if ( record.getBankId() != null ) {
            bankReview.setBankId( record.getBankId() );
        }
        if ( record.getRate() != null ) {
            bankReview.setRate( record.getRate() );
        }
        if ( record.getReview() != null ) {
            bankReview.setReview( record.getReview() );
        }
        if ( record.getCreatedAt() != null ) {
            bankReview.setCreatedAt( record.getCreatedAt() );
        }

        return bankReview;
    }
}
