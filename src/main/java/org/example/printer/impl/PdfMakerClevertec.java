package org.example.printer.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.example.dao.pojo.Customer;
import org.example.printer.DockMaker;
import org.example.printer.impl.pageivent.MyPageEvents;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.example.helper.PathVerify.getPathForSaveDoc;

public class PdfMakerClevertec implements DockMaker<Customer> {

    private static final String EXTENSION_FOR_SAVE = ".pdf";
    public String outputPdfPath = getPathForSaveDoc("pdf");
    public static final String WATERMARK_PDF = System.getProperty("user.dir") + "/src/main/resources/watermark/ClevertecWatermark.pdf";

    @Override
    public String generateDock(String fileNamePart, List<Customer> content) {
        Document document = new Document();
        String fileName = getNameForPdf(fileNamePart);
        document.setMargins(30, 30, 150, 30);
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(outputPdfPath +
                            fileName));
            MyPageEvents events = new MyPageEvents();
            writer.setPageEvent(events);
            document.open();

            // Заголовок файла
            Paragraph header = new Paragraph(fileNamePart.toUpperCase(), getTextRoboFont());
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(20f);
            document.add(header);

            // Заполнение
            PdfPTable table = createTableHeader(Customer.class);
            insertInTable(table, content);
            document.add(table);

            document.close();

            System.out.println("PDF created successfully.");
            return fileName;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return "";
    }


    @Override
    public void setCustomPathForSave(String relativePath) {
        this.outputPdfPath = getPathForSaveDoc(relativePath);
    }

    private String getNameForPdf(String fileNamePart) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss_")) +
                fileNamePart.replace(" ", "_") +
                EXTENSION_FOR_SAVE;
    }

    protected PdfPTable createTableHeader(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();

        PdfPTable table = new PdfPTable(fields.length);
        table.setWidthPercentage(100);

        for (Field fl : fields) {
            table.addCell(new Paragraph(fl.getName().toUpperCase(), getTextRoboFont()));
        }
        return table;
    }

    protected void insertInTable(PdfPTable table, List<Customer> customers) {
        for (Customer cst : customers) {
            Arrays.stream(Customer.class.getDeclaredFields()).iterator().forEachRemaining(f -> {
                f.setAccessible(true);
                try {
                    table.addCell(new Paragraph(f.get(cst).toString(), getTextRoboFont()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }

    @SneakyThrows
    protected Font getTextRoboFont() {
        BaseFont unicode = BaseFont.createFont(
                System.getProperty("user.dir") + "/src/main/resources/font/robo.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(unicode, 12);
        return font;
    }
}
