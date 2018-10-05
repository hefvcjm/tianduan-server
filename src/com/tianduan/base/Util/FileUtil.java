package com.tianduan.base.Util;

import com.tianduan.exception.IllegalFileTypeException;
import com.tianduan.model.User;
import org.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtil {

    public enum LegalFileType {
        PICTURE("Picture", new HashSet(new JSONArray(PropertiesUtil.getProperties("upload.file.extension.legal-picture").toLowerCase()).toList())),
        AUDIO("Audio", new HashSet(new JSONArray(PropertiesUtil.getProperties("upload.file.extension.legal-audio").toLowerCase()).toList())),
        VIDEO("Video", new HashSet(new JSONArray(PropertiesUtil.getProperties("upload.file.extension.legal-video").toLowerCase()).toList()));


        private String name;

        private Set<String> typeSet;

        LegalFileType(String name, Set<String> typeSet) {
            this.name = name;
            this.typeSet = typeSet;
        }

        public String getName() {
            return name;
        }

        public Set<String> getTypeSet() {
            return typeSet;
        }
    }

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

    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        if (fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    public static String getFileExtension(File file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    public static String getFileExtension(MultipartFile file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        if (fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private static boolean isLegalFileType(String fileType, Set<String> legalSet) {
        if (legalSet == null) {
            return true;
        }
        if (legalSet.contains(fileType.toLowerCase())) {
            return true;
        }
        return false;
    }

    public static boolean isLegalFileType(String fileType, LegalFileType legalFileType) {
        Set<String> legalSet = legalFileType.getTypeSet();
        return isLegalFileType(fileType, legalSet);
    }

    public static boolean isLegalFileType(File file, LegalFileType legalFileType) {
        Set<String> legalSet = legalFileType.getTypeSet();
        String fileType = getFileExtension(file);
        return isLegalFileType(fileType, legalSet);
    }

    public static boolean isLegalFileType(MultipartFile file, LegalFileType legalFileType) {
        Set<String> legalSet = legalFileType.getTypeSet();
        String fileType = getFileExtension(file);
        return isLegalFileType(fileType, legalSet);
    }

    public static List<String> saveFiles(MultipartFile[] files, User user, String objectId, String superclass, String subclass, FileUtil.LegalFileType fileType) throws IllegalFileTypeException {
        String basePath = PropertiesUtil.getProperties("upload.file.save.base-path");
        String uri = "\\files\\" + user.getObjectId() + "\\" + superclass + "\\" + objectId;
        int i = 0;
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!FileUtil.isLegalFileType(file, fileType)) {
                throw new IllegalFileTypeException(FileUtil.getFileExtension(file), fileType.getName());
            }
            String realPath = uri + "\\" + subclass + "\\";
            String name = i + "-" + file.getOriginalFilename();
            try {
                FileUtil.saveFile(file.getBytes(), basePath + realPath, name);
                paths.add((realPath + name).replace("\\", "/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
        return paths;
    }

}
