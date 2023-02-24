package com.dev.storesystem.domain.helpers;

import com.dev.storesystem.common.dtos.sale.CartPdfDto;
import com.dev.storesystem.domain.entities.ProductEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface PdfHelper {
    void generateCartPdf(HttpServletResponse response, List<ProductEntity> products,
                         CartPdfDto pdfSale);
}
