package com.restaurant.acquerello.services.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderDetails;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.services.BillingPDFService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BillingPDFImpl implements BillingPDFService {
    @Override
    public void exportPDF(HttpServletResponse response, User user, Order order, List<OrderDetails> orderDetails) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        DateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String currentDateTime= dateFormat.format(new Date());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(20);

        Paragraph paragraph = new Paragraph("Acquerello Restaurant", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        Font fontDate = FontFactory.getFont(FontFactory.HELVETICA);
        fontDate.setSize(12);
        Paragraph paragraphDate = new Paragraph("Date: "+currentDateTime, fontDate);
        paragraphDate.setSpacingAfter(15);
        document.add(paragraphDate);

        Font fontParagraphTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontParagraphTitle.setSize(12);
        Font fontParagraph2 = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph2.setSize(12);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{5.5f, 5.5f});
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPhrase(new Phrase("Client:", fontParagraphTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase(user.getFirstName()+" "+user.getLastName(), fontParagraph2));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email:", fontParagraphTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase(user.getEmail(), fontParagraph2));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Status Order:", fontParagraphTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase(order.getState().toString(), fontParagraph2));
        table.addCell(cell);
        table.setSpacingAfter(20);
        document.add(table);

        PdfPTable tableBilling = new PdfPTable(4);


        document.close();
    }
}
