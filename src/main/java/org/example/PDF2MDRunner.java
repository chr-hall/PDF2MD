package org.example;

import java.io.File;
import java.io.IOException;

public class PDF2MDRunner {
    public static void RunPDF2MD(String inputPath, String outputPath) {

        String inputPdfPath = "--inputFolderPath=" + inputPath;
        String outputMdPath = "--outputFolderPath=" + outputPath;

        try {

            // npx @opendocsg/pdf2md --inputFolderPath=[your input folder path] --outputFolderPath=[your output folder path] --recursive
            String[] command = { "cmd.exe", "/c", "npx", "@opendocsg/pdf2md", inputPdfPath, outputMdPath, "--recursive" };

            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Wait for the process to finish
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Conversion successful!");
            } else {
                System.out.println("Conversion failed!");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
