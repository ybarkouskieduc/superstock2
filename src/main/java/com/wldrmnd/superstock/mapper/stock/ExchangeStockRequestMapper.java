package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.ExchangeStockRequestRecord;
import com.wldrmnd.superstock.model.stock.ExchangeStockRequestModel;
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
public interface ExchangeStockRequestMapper {

    ExchangeStockRequestModel toModel(ExchangeStockRequestRecord record);
}
