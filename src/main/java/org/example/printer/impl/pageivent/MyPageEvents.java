package org.example.printer.impl.pageivent;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;

import static org.example.printer.impl.PdfMakerClevertec.getWatermarkPathPdf;

public class MyPageEvents extends PdfPageEventHelper {

    protected float startpos = -1;

    @Override
    @SneakyThrows
    public void onStartPage(PdfWriter writer, Document document) {
        PdfReader pdfReader = new PdfReader(getWatermarkPathPdf());
        PdfImportedPage importedPage = writer.getImportedPage(pdfReader, 1);
        PdfContentByte contentByte = writer.getDirectContentUnder();
        contentByte.addTemplate(importedPage, 0, 0);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle pagesize = document.getPageSize();
        ColumnText.showTextAligned(
                writer.getDirectContent(),
                Element.ALIGN_CENTER,
                new Phrase(String.valueOf(writer.getPageNumber())),
                (pagesize.getLeft() + pagesize.getRight()) / 2,
                pagesize.getBottom() + 15,
                0);
        if (startpos != -1)
            onParagraphEnd(writer, document,
                    pagesize.getBottom(document.bottomMargin()));
        startpos = pagesize.getTop(document.topMargin());
    }

    @Override
    public void onParagraph(PdfWriter writer, Document document,
                            float paragraphPosition) {
        startpos = paragraphPosition;
    }
}
