package com.tianduan.base.Util;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    public static void saveFile(byte[] file, String path, String name) throws Exception {
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(path + name);
        out.write(file);
        out.flush();
        out.close();
        System.out.println(path + name);
    }

    public static void saveFile(byte[] file, String name) throws Exception {
        String path = PropertiesUtil.getProperties("upload.file.save.base-path");
        saveFile(file, path, name);
    }

}
