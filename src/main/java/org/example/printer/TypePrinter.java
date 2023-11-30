package org.example.printer;

public enum TypePrinter {
    PDF(".pdf"), TXT(".txt");

    public final String extension;

    TypePrinter(String extension) {
        this.extension = extension;
    }
}
