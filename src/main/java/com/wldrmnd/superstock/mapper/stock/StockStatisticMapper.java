package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.domain.tables.records.StockStatisticRecord;
import com.wldrmnd.superstock.model.stock.Stock;
import com.wldrmnd.superstock.model.stock.StockStatistic;
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
public interface StockStatisticMapper {

    StockStatistic toModel(StockStatisticRecord record);
}
