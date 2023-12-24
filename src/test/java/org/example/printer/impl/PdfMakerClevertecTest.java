package org.example.printer.impl;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.dao.pojo.Customer;
import org.example.dao.pojo.CustomerForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class PdfMakerClevertecTest {

    String filePath = "";
    PdfMakerClevertec maker;

    @BeforeEach
    public void createTestFile() {
        maker = new PdfMakerClevertec();
        maker.setWatermarkPathPdf(System.getProperty("user.dir") + "/src/main/resources/watermark/ClevertecWatermark.pdf");
        maker.setFontPathPdf(System.getProperty("user.dir") + "/src/main/resources/font/robo.ttf");
    }

    @AfterEach
    public void rmTestFile() {
        new File(filePath).delete();
    }

    @Test
    public void testCreatePdf() {
        //Given
        CustomerForTest forTest = new CustomerForTest();
        Customer customer = forTest.getCustomer();

        //When
        String nameActualFile = maker.generateDock("File test", List.of(customer));
        filePath = maker.outputDockPath + nameActualFile;
        File actual = new File(filePath);

        //Then
        Assertions.assertTrue(actual.exists());
    }

    @Test
    @SneakyThrows
    public void testCreatePdfWithVerifyDataAndSimpleData() {
        //Given
        CustomerForTest forTest = new CustomerForTest();
        Customer customer = forTest.getCustomer();
        String row = forTest.getRow();

        //When
        PdfMakerClevertec maker = new PdfMakerClevertec();
        String nameActualFile = maker.generateDock("File test",
                List.of(customer));
        filePath = maker.outputDockPath + nameActualFile;

        //Then
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfContent = pdfTextStripper.getText(document);
            System.out.println(pdfContent);

            Assertions.assertTrue(pdfContent.contains(row),
                    "PDF не содержит ожидаемого текста.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @SneakyThrows
    public void testCreatePdfWithVerifyDataAndMultipleData() {
        //Given
        List<Customer> customers = new ArrayList<>();
        List<String> rows = new ArrayList<>();

        for (int i=0; i<150; i++) {
            CustomerForTest forTest1 = new CustomerForTest();
            Customer customer = forTest1.getCustomer();
            String row = forTest1.getRow();
            customers.add(customer);
            rows.add(row);
        }

        //When
        PdfMakerClevertec maker = new PdfMakerClevertec();
        String nameActualFile = maker.generateDock("File test",
                customers);
        filePath = maker.outputDockPath + nameActualFile;

        //Then
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfContent = pdfTextStripper.getText(document);
            System.out.println(pdfContent);

            Assertions.assertTrue(rows.stream().allMatch(pdfContent::contains),
                    "PDF не содержит ожидаемого текста.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
