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
import java.math.BigDecimal;
import java.util.List;

@Component
public class PdfHelperImpl implements PdfHelper {
    @Override
    public void generatePdf(HttpServletResponse response, List<ProductEntity> products,
                            List<SaveSaleProductDto> saleProducts) {
        try (var document = new Document(PageSize.SMALL_PAPERBACK)) {

            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            var fontTitle = FontFactory.getFont(FontFactory.defaultEncoding);
            fontTitle.setSize(20);

            var paragraph = new Paragraph("Loja Minha Make", fontTitle);

            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            var table = new PdfPTable(4);
            var cell = new PdfPCell();
            var totalPrice = new BigDecimal(0);

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
                var idCell = new PdfPCell();
                var nameCell = new PdfPCell();
                var priceCell = new PdfPCell();
                var amountCell = new PdfPCell();

                idCell.setBorder(Rectangle.NO_BORDER);
                nameCell.setBorder(Rectangle.NO_BORDER);
                priceCell.setBorder(Rectangle.NO_BORDER);
                amountCell.setBorder(Rectangle.NO_BORDER);

                idCell.setPadding(3);
                nameCell.setPadding(3);
                priceCell.setPadding(3);
                amountCell.setPadding(3);

                idCell.setPhrase(new Phrase(products.get(i).getId().toString()));
                nameCell.setPhrase(new Phrase(products.get(i).getName()));
                priceCell.setPhrase(new Phrase(products.get(i).getPrice().toString()));
                amountCell.setPhrase(new Phrase(saleProducts.get(i).getAmount().toString()));

                table.addCell(idCell);
                table.addCell(nameCell);
                table.addCell(priceCell);
                table.addCell(amountCell);

                totalPrice = totalPrice.add(products.get(i).getPrice().multiply(new BigDecimal(saleProducts.get(i)
                        .getAmount())));
            }

            document.add(table);
            fontTitle.setSize(16);

            var totalCart = new Paragraph("Total R$ " + totalPrice, fontTitle);
            totalCart.setAlignment(Element.ALIGN_CENTER);
            document.add(totalCart);
        } catch (IOException exception) {
            throw new BusinessException("Não foi possível gerar o PDF!");
        }
    }
}
