package org.example.printer;

import java.util.List;

public interface DockMaker<T> {

    void generateDock(String baseFilePath, List<T> content);
}
