package com.dev.storesystem.domain.helpers.impl;

import com.dev.storesystem.common.dtos.sale.ShowPdfSaleDto;
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
                            ShowPdfSaleDto pdfSale) {
        try (var document = new Document(PageSize.SMALL_PAPERBACK)) {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            var pdfTitle = generateParagraph("Loja Minha Make", 20);

            document.add(pdfTitle);

            var productsTable = new PdfPTable(4);
            var cell = generatePdfCell();
            var totalPrice = new BigDecimal(0);

            productsTable.setSpacingBefore(10);
            cell.setPhrase(new Phrase("ID"));
            productsTable.addCell(cell);
            cell.setPhrase(new Phrase("Nome"));
            productsTable.addCell(cell);
            cell.setPhrase(new Phrase("Preço (UNIT.)"));
            productsTable.addCell(cell);
            cell.setPhrase(new Phrase("QTD"));
            productsTable.addCell(cell);

            for (int i = 0; i < pdfSale.getProducts().size(); i++) {
                var idCell = generatePdfCell();
                var nameCell = generatePdfCell();
                var priceCell = generatePdfCell();
                var amountCell = generatePdfCell();

                idCell.setPhrase(new Phrase(products.get(i).getId().toString()));
                nameCell.setPhrase(new Phrase(products.get(i).getName()));
                priceCell.setPhrase(new Phrase(String.format("%.2f", products.get(i).getPrice())));
                amountCell.setPhrase(new Phrase(pdfSale.getProducts().get(i).getAmount().toString()));

                productsTable.addCell(idCell);
                productsTable.addCell(nameCell);
                productsTable.addCell(priceCell);
                productsTable.addCell(amountCell);

                totalPrice = totalPrice.add(products.get(i).getPrice().multiply(
                        new BigDecimal(pdfSale.getProducts().get(i)
                                .getAmount())));
            }
            document.add(productsTable);
            if (pdfSale.getPercentDiscount() == null || pdfSale.getPercentDiscount() < 0D || pdfSale.getPercentDiscount() > 100D) {
                pdfSale.setPercentDiscount(0D);
            }

            var discount = BigDecimal.valueOf(pdfSale.getPercentDiscount() / 100D).multiply(totalPrice);
            var subTotal = generateParagraph(String.format("Sub-Total: R$ %.2f", totalPrice), 14);
            var showDiscount = generateParagraph(String.format("Descontos: R$ %.2f", discount), 14);
            var total = totalPrice.subtract(discount);
            document.add(subTotal);
            document.add(showDiscount);
            var showTotal = generateParagraph(String.format("Total: R$ %.2f", total), 14);
            document.add(showTotal);
        } catch (IOException exception) {
            throw new BusinessException("Não foi possível gerar o PDF!");
        }
    }

    private Paragraph generateParagraph(String content, Integer fontSize) {
        var fontStyle = FontFactory.getFont(FontFactory.defaultEncoding);
        fontStyle.setSize(fontSize);
        var paragraph = new Paragraph(content, fontStyle);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    private PdfPCell generatePdfCell() {
        var pdfCell = new PdfPCell();
        pdfCell.setBorder(Rectangle.NO_BORDER);
        return pdfCell;
    }
}
