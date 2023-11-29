package org.example.printer;

import java.util.List;

public interface DockMaker<T> {

    String generateDock(String baseFilePath, List<T> content);

    /**
     * метод для изменения директории для сохранения файлов по умолчанию
     * @param newPath
     */
    void setCustomPathForSave(String newPath);
}
