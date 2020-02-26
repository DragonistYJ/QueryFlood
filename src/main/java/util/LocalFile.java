package util;

import bean.Property;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LocalFile {
    private File file;
    private boolean exists;

    public LocalFile(String filename) {
        this.file = searchDir(filename);
        this.exists = this.file != null;
    }

    private File searchDir(String filename) {
        List<File> files = new ArrayList<>();
        files.add(new File(Property.getProperty("shared_dir")));

        int index = 0;
        while (index < files.size()) {
            File file = files.get(index);
            if (file.getName().equals(filename)) {
                return file;
            }
            File[] subFiles = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || pathname.getName().equals(filename);
                }
            });
            if (subFiles != null) {
                files.addAll(Arrays.asList(subFiles));
            }
            index += 1;
        }
        return null;
    }

    public boolean isExist() {
        return this.exists;
    }

    public byte[] getBytes() {
        FileInputStream fileInputStream = null;
        byte[] bytes = new byte[0];
        try {
            fileInputStream = new FileInputStream(this.file);
            bytes = new byte[fileInputStream.available()];
            int l = fileInputStream.read(bytes);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
