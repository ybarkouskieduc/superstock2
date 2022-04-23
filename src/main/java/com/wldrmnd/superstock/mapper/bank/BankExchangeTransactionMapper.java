package com.wldrmnd.superstock.mapper.bank;


import com.wldrmnd.superstock.domain.tables.records.BankExchangeTransactionRecord;
import com.wldrmnd.superstock.model.bank.BankExchangeTransaction;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BankExchangeTransactionMapper {

    BankExchangeTransaction toModel(BankExchangeTransactionRecord record);
}
