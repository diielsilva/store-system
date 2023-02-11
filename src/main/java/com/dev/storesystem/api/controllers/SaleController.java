package com.dev.storesystem.api.controllers;

import com.dev.storesystem.common.dtos.sale.SaveSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleDto;
import com.dev.storesystem.common.dtos.sale.ShowSaleProductDto;
import com.dev.storesystem.domain.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {
    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShowSaleDto> save(Principal onlineUser, @RequestBody @Valid SaveSaleDto saveSaleDto) {
        var sale = service.save(onlineUser.getName(), saveSaleDto);
        return new ResponseEntity<>(sale, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ShowSaleDto>> findAllActive(Pageable pageable) {
        var sales = service.findAllActive(pageable);
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping(value = "/{saleId}/details")
    public ResponseEntity<List<ShowSaleProductDto>> details(@PathVariable Long saleId) {
        var saleProducts = service.details(saleId);
        return new ResponseEntity<>(saleProducts, HttpStatus.OK);
    }

    @GetMapping(value = "/today")
    public ResponseEntity<List<ShowSaleDto>> todaySales() {
        var sales = service.findTodaySales();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }
}
