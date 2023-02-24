package com.dev.storesystem.domain.helpers.impl;

import com.dev.storesystem.common.dtos.sale.CartPdfDto;
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
    public void generateCartPdf(HttpServletResponse response, List<ProductEntity> products,
                                CartPdfDto cartPdf) {
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

            for (int i = 0; i < cartPdf.getProducts().size(); i++) {
                var idCell = generatePdfCell();
                var nameCell = generatePdfCell();
                var priceCell = generatePdfCell();
                var amountCell = generatePdfCell();

                idCell.setPhrase(generatePhrase(products.get(i).getId()));
                nameCell.setPhrase(generatePhrase(products.get(i).getName()));
                priceCell.setPhrase(generatePhrase(String.format("%.2f", products.get(i).getPrice())));
                amountCell.setPhrase(generatePhrase(cartPdf.getProducts().get(i).getAmount()));

                productsTable.addCell(idCell);
                productsTable.addCell(nameCell);
                productsTable.addCell(priceCell);
                productsTable.addCell(amountCell);

                totalPrice = totalPrice.add(calculateTotalPricePerProduct(products.get(i), cartPdf.getProducts().get(i)));
            }
            document.add(productsTable);
            if (cartPdf.getPercentDiscount() == null || cartPdf.getPercentDiscount() < 0D || cartPdf.getPercentDiscount() > 100D) {
                cartPdf.setPercentDiscount(0D);
            }

            var discountValue = BigDecimal.valueOf(cartPdf.getPercentDiscount() / 100D).multiply(totalPrice);
            var subTotal = generateParagraph(String.format("Sub-Total: R$ %.2f", totalPrice), 14);
            var discount = generateParagraph(String.format("Descontos: R$ %.2f", discountValue), 14);
            var total = totalPrice.subtract(discountValue);
            document.add(subTotal);
            document.add(discount);
            var showTotal = generateParagraph(String.format("Total: R$ %.2f", total), 14);
            document.add(showTotal);
        } catch (IOException exception) {
            throw new BusinessException("Não foi possível gerar o PDF!");
        }
    }

    private BigDecimal calculateTotalPricePerProduct(ProductEntity product, SaveSaleProductDto saleProduct) {
        return product.getPrice().multiply(BigDecimal.valueOf(saleProduct.getAmount()));
    }

    private Phrase generatePhrase(Object content) {
        return new Phrase(content.toString());
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
