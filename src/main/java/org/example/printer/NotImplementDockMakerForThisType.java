package org.example.printer;

public class NotImplementDockMakerForThisType extends IllegalArgumentException {

    public NotImplementDockMakerForThisType() {
        super("Dock maker not implement");
    }
}
