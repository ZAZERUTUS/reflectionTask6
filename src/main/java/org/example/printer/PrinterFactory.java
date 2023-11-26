package org.example.printer;

import org.example.dao.pojo.Customer;
import org.example.printer.impl.PdfMakerClevertec;

public class PrinterFactory {

    /**
     * Текстовый тип добавлен в качестве заглушки на перспективу реализации
     * @param typePrinter
     * @return
     */

    public static DockMaker<Customer> createDockMakerByType(TypePrinter typePrinter) {
        switch (typePrinter) {
//            case PDF -> new PdfMakerClevertec();
            case TXT -> throw new NotImplementDockMakerForThisType();

        }
        return new PdfMakerClevertec();
    }
}
