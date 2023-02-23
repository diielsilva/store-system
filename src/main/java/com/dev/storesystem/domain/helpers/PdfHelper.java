package com.dev.storesystem.domain.helpers;

import com.dev.storesystem.common.dtos.sale.SaveSaleProductDto;
import com.dev.storesystem.common.dtos.sale.ShowPdfSaleDto;
import com.dev.storesystem.domain.entities.ProductEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface PdfHelper {
    void generatePdf(HttpServletResponse response, List<ProductEntity> products,
                     ShowPdfSaleDto pdfSale);
}
