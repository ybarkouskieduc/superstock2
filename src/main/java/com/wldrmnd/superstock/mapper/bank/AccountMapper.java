package com.wldrmnd.superstock.mapper.bank;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.model.bank.Account;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AccountMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "default", target = "default")
    @Mapping(source = "currency", target = "currency")
    Account toModel(AccountRecord record);
}
