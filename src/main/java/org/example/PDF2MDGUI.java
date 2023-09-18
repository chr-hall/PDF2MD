package org.example;


import com.formdev.flatlaf.FlatDarculaLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

public class PDF2MDGUI extends JFrame {
    private static JButton inputButton;
    private JButton outputButton;
    private JButton convertButton;
    private JLabel inputLabel;
    private JLabel outputLabel;
    static File outputFolder;
    private static ArrayList<File> inputFiles;

    public PDF2MDGUI() throws UnsupportedLookAndFeelException {
        UIManager.put("Component.focusWidth", 0);
        UIManager.put( "Button.innerFocusWidth", 0 );
        setTitle("PDF to Markdown Converter");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inputFiles = new ArrayList<>();

        inputLabel = new JLabel("PDF files to convert to MD: ");
        outputLabel = new JLabel("Output Folder: ");
        inputButton = new JButton("Select");
        outputButton = new JButton("Select");
        convertButton = new JButton("Convert");

        // Shows file list
        JTextArea inputText = new JTextArea(10, 40);
        JScrollPane inputPane = new JScrollPane(inputText);
        inputText.append("");
        inputText.setEditable(false);

        // Shows output folder
        JTextArea outputText = new JTextArea(5, 40);
        JScrollPane outputPane = new JScrollPane(outputText);
        outputText.append("");
        outputText.setEditable(false);

        // Add components to panel
        JPanel panel = new JPanel(new MigLayout("align center, gap 5px 10px"));
        panel.add(inputLabel, "cell 0 0, gaptop 10px");
        panel.add(inputPane, "cell 0 1, push, grow");
        panel.add(inputButton, "cell 0 2");
        panel.add(outputLabel, "cell 0 3, gaptop 10px");
        panel.add(outputPane, "cell 0 4, push, grow");
        panel.add(outputButton, "cell 0 5");
        panel.add(convertButton, "cell 0 5");


        add(panel);
        pack();
        setMinimumSize(getSize());
        inputButton.requestFocus();


        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(PDF2MDGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] filesArray = fileChooser.getSelectedFiles();
                    inputFiles.addAll(Arrays.asList(filesArray));
                    for (File f : inputFiles) {
                        inputText.append(f.toString() + "\n");
                    }
                }

            }
        });

        outputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(PDF2MDGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    outputFolder = fileChooser.getSelectedFile();
                    outputText.setText(outputFolder.toString());
                }
            }
        });

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (inputFiles != null && outputFolder != null) {
                    // Create popup window
                    JDialog popup = new JDialog();
                    popup.setUndecorated(true); // Remove window decorations
                    popup.setSize(200, 100);
                    popup.setLocationRelativeTo(PDF2MDGUI.this); // Center the popup on your main frame

                    // Create a label with the message
                    JLabel messageLabel = new JLabel("Working...");
                    messageLabel.setHorizontalAlignment(JLabel.CENTER);

                    // Set the content pane's layout manager and add the label
                    popup.getContentPane().setLayout(new BorderLayout());
                    popup.getContentPane().add(messageLabel, BorderLayout.CENTER);

                    // Display the popup window
                    popup.setModal(false); // Non-modal (doesn't block other windows)
                    popup.setVisible(true);

                    // Use SwingWorker to perform tasks in the background
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            // PDF2MD converts contents of entire folders
                            // To convert selected files - copy to output folder before converting
                            // And then delete
                            for (File f : inputFiles) {
                                Path sourcePath = f.toPath();
                                Path destinationPath = outputFolder.toPath().resolve(sourcePath.getFileName());
                                try {
                                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            // Run PDF2MD (input folder same as output)
                            PDF2MDRunner.RunPDF2MD(PDF2MDGUI.outputFolder.toString(), PDF2MDGUI.outputFolder.toString());

                            // Delete temp PDF files
                            for (File f : inputFiles) {
                                Path sourcePath = f.toPath();
                                Path destinationPath = outputFolder.toPath().resolve(sourcePath.getFileName());
                                try {
                                    Files.delete(destinationPath);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void done() {
                            popup.dispose(); // Close the popup
                            JOptionPane.showMessageDialog(PDF2MDGUI.this, "Done!", null, JOptionPane.INFORMATION_MESSAGE);
                        }
                    };

                    worker.execute(); // Start the SwingWorker

                } else {
                    JOptionPane.showMessageDialog(PDF2MDGUI.this, "Pick files and output folder before converting", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void setUpGUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                new PDF2MDGUI().setVisible(true);
                inputButton.requestFocus();
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
