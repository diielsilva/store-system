package com.dev.storesystem.api.controllers;

import com.dev.storesystem.common.dtos.product.SaveProductDto;
import com.dev.storesystem.common.dtos.product.ShowProductDto;
import com.dev.storesystem.domain.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShowProductDto> save(@RequestBody @Valid SaveProductDto saveProductDto) {
        var product = service.save(saveProductDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ShowProductDto>> findAllActive(Pageable pageable) {
        var products = service.findAllActive(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<ShowProductDto>> findActivesByName(@RequestParam String name, Pageable pageable) {
        var products = service.findActivesByName(name, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/inactive")
    public ResponseEntity<Page<ShowProductDto>> findAllInactive(Pageable pageable) {
        var products = service.findAllInactive(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ShowProductDto> findActiveById(@PathVariable Long id) {
        var product = service.findActiveById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "/inactive/{id}")
    public ResponseEntity<ShowProductDto> findInactiveById(@PathVariable Long id) {
        var product = service.findInactiveById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ShowProductDto> update(@PathVariable Long id, @RequestBody @Valid SaveProductDto saveProductDto) {
        var product = service.update(id, saveProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PatchMapping(value = "/{id}/restore")
    public ResponseEntity<Void> restore(@PathVariable Long id) {
        service.restore(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
