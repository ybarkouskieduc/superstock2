package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.model.stock.StockPrice;
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
public interface StockPriceMapper {

    StockPrice toModel(StockPriceRecord record);
}
