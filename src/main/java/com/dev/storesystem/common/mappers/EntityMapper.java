package com.dev.storesystem.common.mappers;

import com.dev.storesystem.common.dtos.product.SaveProductDto;
import com.dev.storesystem.common.dtos.product.ShowProductDto;
import com.dev.storesystem.common.dtos.sale.SaveSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleProductDto;
import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.domain.entities.ProductEntity;
import com.dev.storesystem.domain.entities.SaleEntity;
import com.dev.storesystem.domain.entities.SaleProductEntity;
import com.dev.storesystem.domain.entities.UserEntity;

public interface EntityMapper {
    UserEntity mapFromSaveUserDtoToUserEntity(SaveUserDto saveUserDto);

    ShowUserDto mapFromUserEntityToShowUserDto(UserEntity userEntity);

    ProductEntity mapFromSaveProductDtoToProductEntity(SaveProductDto saveProductDto);

    ShowProductDto mapFromProductEntityToShowProductDto(ProductEntity productEntity);

    SaleEntity mapFromSaveSaleDtoToSaleEntity(SaveSaleDto saveSaleDto);

    ShowSaleDto mapFromSaleEntityToShowSaleDto(SaleEntity saleEntity);

    ShowSaleProductDto mapFromSaleProductEntityToShowSaleProductDto(SaleProductEntity saleProductEntity);
}
