package com.dev.storesystem.domain.helpers.impl;

import com.dev.storesystem.common.dtos.sale.SaveSaleProductDto;
import com.dev.storesystem.domain.entities.ProductEntity;
import com.dev.storesystem.domain.exceptions.BusinessException;
import com.dev.storesystem.domain.helpers.PdfHelper;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PdfHelperImpl implements PdfHelper {
    @Override
    public void generatePdf(HttpServletResponse response, List<ProductEntity> products,
                            List<SaveSaleProductDto> saleProducts) {
        try (var document = new Document(PageSize.A4)) {

            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            var fontTitle = FontFactory.getFont(FontFactory.defaultEncoding);
            fontTitle.setSize(20);
            var paragraph = new Paragraph("Loja Minha Make", fontTitle);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            var table = new PdfPTable(4);
            var cell = new PdfPCell();

            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(3);
            table.setSpacingBefore(10);
            cell.setPhrase(new Phrase("ID"));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Nome"));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Preço"));
            table.addCell(cell);
            cell.setPhrase(new Phrase("QTD"));
            table.addCell(cell);

            for (int i = 0; i < saleProducts.size(); i++) {
                var dynamicCell01 = new PdfPCell();
                var dynamicCell02 = new PdfPCell();
                var dynamicCell03 = new PdfPCell();
                var dynamicCell04 = new PdfPCell();

                dynamicCell01.setBorder(Rectangle.NO_BORDER);
                dynamicCell02.setBorder(Rectangle.NO_BORDER);
                dynamicCell03.setBorder(Rectangle.NO_BORDER);
                dynamicCell04.setBorder(Rectangle.NO_BORDER);
                dynamicCell01.setPadding(3);
                dynamicCell02.setPadding(3);
                dynamicCell03.setPadding(3);
                dynamicCell04.setPadding(3);

                dynamicCell01.setPhrase(new Phrase(products.get(i).getId().toString()));
                dynamicCell02.setPhrase(new Phrase(products.get(i).getName()));
                dynamicCell03.setPhrase(new Phrase(products.get(i).getPrice().toString()));
                dynamicCell04.setPhrase(new Phrase(saleProducts.get(i).getAmount().toString()));

                table.addCell(dynamicCell01);
                table.addCell(dynamicCell02);
                table.addCell(dynamicCell03);
                table.addCell(dynamicCell04);
            }

            document.add(table);
        } catch (IOException exception) {
            throw new BusinessException("Não foi possível gerar o PDF!");
        }
    }
}
