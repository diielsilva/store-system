package com.dev.storesystem.common.mappers.impl;

import com.dev.storesystem.common.dtos.product.SaveProductDto;
import com.dev.storesystem.common.dtos.product.ShowProductDto;
import com.dev.storesystem.common.dtos.sale.SaveSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleProductDto;
import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.common.mappers.EntityMapper;
import com.dev.storesystem.domain.entities.ProductEntity;
import com.dev.storesystem.domain.entities.SaleEntity;
import com.dev.storesystem.domain.entities.SaleProductEntity;
import com.dev.storesystem.domain.entities.UserEntity;
import com.dev.storesystem.domain.enums.PaymentType;
import com.dev.storesystem.domain.enums.UserPermission;
import org.springframework.stereotype.Component;

@Component
public class EntityMapperImpl implements EntityMapper {
    @Override
    public UserEntity mapFromSaveUserDtoToUserEntity(SaveUserDto saveUserDto) {
        var userEntity = new UserEntity();
        userEntity.setName(saveUserDto.getName());
        userEntity.setUsername(saveUserDto.getUsername());
        userEntity.setPassword(saveUserDto.getPassword());
        userEntity.setPermission(UserPermission.toEnum(saveUserDto.getPermission()));
        return userEntity;
    }

    @Override
    public ShowUserDto mapFromUserEntityToShowUserDto(UserEntity userEntity) {
        var showUserDto = new ShowUserDto(userEntity.getId(),
                userEntity.getCreatedAt(), userEntity.getUpdatedAt(), userEntity.getDeletedAt());
        showUserDto.setName(userEntity.getName());
        showUserDto.setUsername(userEntity.getUsername());
        showUserDto.setPermission(userEntity.getPermission());
        return showUserDto;
    }

    @Override
    public ProductEntity mapFromSaveProductDtoToProductEntity(SaveProductDto saveProductDto) {
        var productEntity = new ProductEntity();
        productEntity.setName(saveProductDto.getName());
        productEntity.setPrice(saveProductDto.getPrice());
        productEntity.setAmount(saveProductDto.getAmount());
        return productEntity;
    }

    @Override
    public ShowProductDto mapFromProductEntityToShowProductDto(ProductEntity productEntity) {
        var showProductDto = new ShowProductDto(productEntity.getId(), productEntity.getCreatedAt(),
                productEntity.getUpdatedAt(), productEntity.getDeletedAt());
        showProductDto.setName(productEntity.getName());
        showProductDto.setPrice(productEntity.getPrice());
        showProductDto.setAmount(productEntity.getAmount());
        return showProductDto;
    }

    @Override
    public SaleEntity mapFromSaveSaleDtoToSaleEntity(SaveSaleDto saveSaleDto) {
        var saleEntity = new SaleEntity();
        saleEntity.setPaymentType(PaymentType.toEnum(saveSaleDto.getPaymentType()));
        return saleEntity;
    }

    @Override
    public ShowSaleDto mapFromSaleEntityToShowSaleDto(SaleEntity saleEntity) {
        var showSaleDto = new ShowSaleDto(saleEntity.getId(), saleEntity.getCreatedAt(),
                saleEntity.getUpdatedAt(), saleEntity.getDeletedAt());
        showSaleDto.setUser(mapFromUserEntityToShowUserDto(saleEntity.getUser()));
        showSaleDto.setTotal(saleEntity.getTotal());
        showSaleDto.setPaymentType(saleEntity.getPaymentType());
        return showSaleDto;
    }

    @Override
    public ShowSaleProductDto mapFromSaleProductEntityToShowSaleProductDto(SaleProductEntity saleProductEntity) {
        var showSaleProductDto = new ShowSaleProductDto(saleProductEntity.getId(), saleProductEntity.getCreatedAt(),
                saleProductEntity.getUpdatedAt(), saleProductEntity.getDeletedAt());
        showSaleProductDto.setProduct(mapFromProductEntityToShowProductDto(saleProductEntity.getProduct()));
        showSaleProductDto.setPriceAtSale(saleProductEntity.getPriceAtSale());
        showSaleProductDto.setAmount(saleProductEntity.getAmount());
        return showSaleProductDto;
    }
}
