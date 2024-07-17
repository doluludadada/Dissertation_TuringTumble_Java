package com.gu.turingtumble;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DirectoryTreePrinter {

    public static void main(String[] args) {
        // 專案根目錄路徑
        String projectRootPath = "B:\\2840781t-development-project"; // 請替換為您的專案根目錄路徑
        // 輸出文件路徑
        String outputFilePath = "project_structure.txt";

        File projectRoot = new File(projectRootPath);
        if (!projectRoot.exists() || !projectRoot.isDirectory()) {
            System.out.println("無效的專案根目錄路徑");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            printDirectoryTree(projectRoot, 0, writer);
            System.out.println("專案目錄結構已寫入 " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printDirectoryTree(File folder, int indentLevel, BufferedWriter writer) throws IOException {
        if (!folder.isDirectory()) {
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            for (int i = 0; i < indentLevel; i++) {
                writer.write("    ");
            }
            writer.write("|-- ");
            writer.write(file.getName());
            writer.newLine();

            if (file.isDirectory()) {
                printDirectoryTree(file, indentLevel + 1, writer);
            }
        }
    }
}
