package org.example.helper;

import java.io.File;

public class PathVerify {

    public static String getPathForSaveDoc(String nameFolder) {
        return createDocFolder(nameFolder);
    }

    /**
     * Указывать по умолчанию как разделитель вложенности unix разделитель - '/'
     * @param nameFolder - вложенность относительно рабочей директории
     * @return
     */
    private static String createDocFolder(String nameFolder) {
        String[] folders = nameFolder.split("/");
        String newPath = updatePathFosOS(getCurrentPath());

        for (String fold: folders) {
            File folder = new File(updatePathFosOS(newPath + "/" + fold));
            if (!folder.exists()) {
                folder.mkdir();
            }
            newPath = folder.getPath();
        }
        return updatePathFosOS(newPath + "/");
    }

    public static String getCurrentPath() {
        String path = System.getProperty("user.dir");
        return path;
    }

    public static String updatePathFosOS(String path) {
        String osName = System.getProperty("os.name");
        if (osName.contains("Wind")) {
            return path.replace("/", "\\");
        } else {
            return path.replace("\\", "/");
        }
    }
}
