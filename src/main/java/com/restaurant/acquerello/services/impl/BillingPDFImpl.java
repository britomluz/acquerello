package com.restaurant.acquerello.services.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderDetails;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.services.BillingPDFService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class BillingPDFImpl implements BillingPDFService {
    @Override
    public void exportPDF(HttpServletResponse response, User user, Order order, List<OrderDetails> orderDetails) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        DateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String currentDateTime= dateFormat.format(new Date());

        document.open();
        Image image = Image.getInstance("src/main/resources/static/assets/acquerellogreen.png");
        image.scaleAbsolute(270,80);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(20);
        fontTitle.setColor(182, 26, 26);

        Paragraph paragraph = new Paragraph("I T A L I A N    F O O D", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
       // paragraph.setSpacingBefore(20);
        document.add(paragraph);

        Font fontDate = FontFactory.getFont(FontFactory.HELVETICA);
        fontDate.setSize(12);
        Paragraph paragraphDate = new Paragraph("Date: "+currentDateTime, fontDate);
        paragraphDate.setSpacingBefore(20);
        paragraphDate.setSpacingAfter(15);
        document.add(paragraphDate);

        Font fontParagraphTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontParagraphTitle.setSize(12);
        Font fontParagraph2 = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph2.setSize(12);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{3.5f, 7.5f});
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPhrase(new Phrase("Name:", fontParagraphTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase(user.getFirstName()+" "+user.getLastName(), fontParagraph2));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email:", fontParagraphTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase(user.getEmail(), fontParagraph2));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Phone:", fontParagraphTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase(user.getNumber().toString(), fontParagraph2));
        table.addCell(cell);
        table.setSpacingAfter(20);
        document.add(table);

        PdfPTable tableBilling = new PdfPTable(4);
        tableBilling.setWidthPercentage(100f);
        tableBilling.setWidths(new float[]{1.5f,6.5f, 2.5f, 2.5f});
        Font billTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        billTitle.setColor(Color.white);
        Font bill = FontFactory.getFont(FontFactory.HELVETICA);
        bill.setColor(Color.white);
        PdfPCell cellBill3 = new PdfPCell();
        Color billColor = new Color(157,171,134);
        cellBill3.setPadding(5);
        cellBill3.setBackgroundColor(billColor);
        cellBill3.setPhrase(new Phrase("Details", billTitle));
        cellBill3.setColspan(4);
        cellBill3.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableBilling.addCell(cellBill3);
        PdfPCell cellBill4 = new PdfPCell();
        cellBill4.setBackgroundColor(billColor);
        cellBill4.setPadding(5);
        cellBill4.setPhrase(new Phrase("Qty", bill));
        tableBilling.addCell(cellBill4);
        cellBill4.setPhrase(new Phrase("Description", bill));
        tableBilling.addCell(cellBill4);
        cellBill4.setPhrase(new Phrase("Price", bill));
        tableBilling.addCell(cellBill4);
        cellBill4.setPhrase(new Phrase("total p/p", bill));
        tableBilling.addCell(cellBill4);
        PdfPCell cellBill = new PdfPCell();
        cellBill.setPadding(5);
        for (OrderDetails details : orderDetails){
            cellBill.setPhrase(new Phrase(details.getQuantity().toString(), fontParagraph2));
            tableBilling.addCell(cellBill);
            cellBill.setPhrase(new Phrase(details.getName(), fontParagraph2));
            tableBilling.addCell(cellBill);
            Locale locale = new Locale("en", "US");
            NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
            String amountFormatCurrency = currency.format(details.getPrice());
            cellBill.setPhrase(new Phrase(amountFormatCurrency, fontParagraph2));
            tableBilling.addCell(cellBill);
            Locale locale1 = new Locale("en", "US");
            NumberFormat currency1 = NumberFormat.getCurrencyInstance(locale);
            String amountFormatCurrency1 = currency1.format(details.getTotalProduct());
            cellBill.setPhrase(new Phrase(amountFormatCurrency1, fontParagraph2));
            tableBilling.addCell(cellBill);
        }
        Locale locale = new Locale("en", "US");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        String amountFormatCurrency = currency.format(order.getTotal());
        PdfPCell cellBill2 = new PdfPCell();
        cellBill2.setPadding(5);
        cellBill2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBill2.setPhrase(new Phrase("Total Order:", fontParagraphTitle));
        cellBill2.setColspan(3);
        tableBilling.addCell(cellBill2);
        cellBill.setPhrase(new Phrase(amountFormatCurrency, fontParagraphTitle));
        tableBilling.addCell(cellBill);
        document.add(tableBilling);


        document.close();
    }
}
