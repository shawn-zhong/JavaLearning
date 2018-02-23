package com.tseong.learning.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class _01_FilesOperation {

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();

            startTime = System.currentTimeMillis();
            createFile();
            endTime = System.currentTimeMillis();
            System.out.println("Creation Operation consumes : " + (endTime-startTime));

            startTime = System.currentTimeMillis();
            copyFile();
            endTime = System.currentTimeMillis();
            System.out.println("Copying Operation consumes : " + (endTime-startTime));

            startTime = System.currentTimeMillis();
            moveFile();
            endTime = System.currentTimeMillis();
            System.out.println("Movement Operation consumes : " + (endTime-startTime));

            startTime = System.currentTimeMillis();
           // deleteFile();
            endTime = System.currentTimeMillis();
            System.out.println("Deletion Operation consumes : " + (endTime-startTime));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 创建文件
    public static void createFile() throws IOException {
        Path target = Paths.get("/Users/Shawn/Documents/workspace/JavaLearning/target/newfile.txt");
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
        FileAttribute<Set<PosixFilePermission>> attribute = PosixFilePermissions.asFileAttribute(perms);
        Files.createFile(target, attribute);
    }

    // 删除文件
    public static void deleteFile() throws IOException {
        Path target = Paths.get("/Users/Shawn/Documents/workspace/JavaLearning/target/newfile4.txt");
        Files.delete(target);
    }

    // 拷贝文件
    public static void copyFile() throws IOException {
        Path source = Paths.get("/Users/Shawn/Documents/workspace/JavaLearning/target/newfile.txt");
        Path target = Paths.get("/Users/Shawn/Documents/workspace/JavaLearning/target/newfile2.txt");
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    // 移动文件
    public static void moveFile() throws IOException {
        Path source = Paths.get("/Users/Shawn/Documents/workspace/JavaLearning/target/newfile2.txt");
        Path target = Paths.get("/Users/Shawn/Documents/workspace/JavaLearning/target/newfile4.txt");
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

}
