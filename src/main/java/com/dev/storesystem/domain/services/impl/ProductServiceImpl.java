package com.dev.storesystem.domain.services.impl;

import com.dev.storesystem.common.dtos.product.SaveProductDto;
import com.dev.storesystem.common.dtos.product.ShowProductDto;
import com.dev.storesystem.common.mappers.EntityMapper;
import com.dev.storesystem.domain.exceptions.NameInUse;
import com.dev.storesystem.domain.helpers.DateHelper;
import com.dev.storesystem.domain.providers.ProductProvider;
import com.dev.storesystem.domain.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductProvider provider;
    private final EntityMapper entityMapper;
    private final DateHelper dateHelper;

    public ProductServiceImpl(ProductProvider provider, EntityMapper entityMapper, DateHelper dateHelper) {
        this.provider = provider;
        this.entityMapper = entityMapper;
        this.dateHelper = dateHelper;
    }

    @Override
    public ShowProductDto save(SaveProductDto saveProductDto) {
        var productEntity = entityMapper.mapFromSaveProductDtoToProductEntity(saveProductDto);
        if (isNameInUse(productEntity.getName())) {
            throw new NameInUse("O nome do produto: " + productEntity.getName() + " já está em uso!");
        }
        dateHelper.setSaveTime(productEntity);
        provider.save(productEntity);
        return entityMapper.mapFromProductEntityToShowProductDto(productEntity);
    }

    @Override
    public Page<ShowProductDto> findAllActive(Pageable pageable) {
        var activeProducts = provider.findAllActive(pageable);
        return activeProducts.map(entityMapper::mapFromProductEntityToShowProductDto);
    }

    @Override
    public Page<ShowProductDto> findActivesByName(String name, Pageable pageable) {
        var activeProducts = provider.findActivesByName(name, pageable);
        return activeProducts.map(entityMapper::mapFromProductEntityToShowProductDto);
    }

    @Override
    public Page<ShowProductDto> findAllInactive(Pageable pageable) {
        var inactiveProducts = provider.findAllInactive(pageable);
        return inactiveProducts.map(entityMapper::mapFromProductEntityToShowProductDto);
    }

    @Override
    public ShowProductDto findActiveById(Long id) {
        var activeProduct = provider.findActiveById(id);
        return entityMapper.mapFromProductEntityToShowProductDto(activeProduct);
    }

    @Override
    public ShowProductDto findInactiveById(Long id) {
        var inactiveProduct = provider.findInactiveById(id);
        return entityMapper.mapFromProductEntityToShowProductDto(inactiveProduct);
    }

    @Override
    public ShowProductDto update(Long id, SaveProductDto saveProductDto) {
        var databaseProduct = provider.findActiveById(id);
        var isNameEquals = databaseProduct.getName().equals(saveProductDto.getName());
        if (!isNameEquals && isNameInUse(saveProductDto.getName())) {
            throw new NameInUse("O nome do produto: " + saveProductDto.getName() + " já está em uso!");
        }
        databaseProduct.setName(saveProductDto.getName());
        databaseProduct.setPrice(saveProductDto.getPrice());
        databaseProduct.setAmount(saveProductDto.getAmount());
        dateHelper.setUpdateTime(databaseProduct);
        provider.save(databaseProduct);
        return entityMapper.mapFromProductEntityToShowProductDto(databaseProduct);
    }

    @Override
    public void restore(Long id) {
        var product = provider.findInactiveById(id);
        product.setDeletedAt(null);
        dateHelper.setUpdateTime(product);
        provider.save(product);
    }

    @Override
    public void delete(Long id) {
        var product = provider.findActiveById(id);
        dateHelper.setDeleteTime(product);
        provider.save(product);
    }

    private boolean isNameInUse(String name) {
        return provider.findByName(name).isPresent();
    }
}
