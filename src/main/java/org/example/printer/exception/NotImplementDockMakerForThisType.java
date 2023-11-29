package org.example.printer.exception;

public class NotImplementDockMakerForThisType extends IllegalArgumentException {

    public NotImplementDockMakerForThisType() {
        super("Dock maker not implement");
    }
}
