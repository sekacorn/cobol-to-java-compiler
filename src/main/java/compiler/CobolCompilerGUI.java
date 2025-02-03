package compiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * CobolCompilerGUI - A graphical interface for the COBOL to Java Compiler.
 */
public class CobolCompilerGUI {
    private static JTextArea cobolInput;
    private static JTextArea javaOutput;
    private static JCheckBox debugMode;
    private static JCheckBox darkMode;
    private static JFileChooser fileChooser;
    private static JFileChooser outputFolderChooser;
    private static File selectedFile;
    private static File outputFolder;
    private static JFrame frame;
    private static JLabel logoLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CobolCompilerGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame("COBOL to Java Compiler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 650);
        frame.setLayout(new BorderLayout());

        // Top Panel for Logo and File Selection
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        logoLabel = new JLabel("Corn's COBOL to Java Compiler", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(logoLabel, BorderLayout.NORTH);

        JPanel filePanel = new JPanel();
        JButton browseButton = new JButton("Browse COBOL File");
        browseButton.addActionListener(CobolCompilerGUI::browseFile);

        JButton outputFolderButton = new JButton("Select Output Folder");
        outputFolderButton.addActionListener(CobolCompilerGUI::selectOutputFolder);

        debugMode = new JCheckBox("Enable Debugging");
        darkMode = new JCheckBox("Dark Mode");
        darkMode.addActionListener(CobolCompilerGUI::toggleDarkMode);

        filePanel.add(browseButton);
        filePanel.add(outputFolderButton);
        filePanel.add(debugMode);
        filePanel.add(darkMode);
        topPanel.add(filePanel, BorderLayout.SOUTH);

        // Center Panel for COBOL Input and Java Output
        cobolInput = new JTextArea(15, 60);
        javaOutput = new JTextArea(15, 60);
        javaOutput.setEditable(false);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(new JScrollPane(cobolInput));
        textPanel.add(new JScrollPane(javaOutput));

        // Bottom Panel for Controls
        JPanel controlPanel = new JPanel();
        JButton compileButton = new JButton("Compile");
        compileButton.addActionListener(CobolCompilerGUI::compileCobol);

        JButton cancelButton = new JButton("Cancel Compilation");
        cancelButton.addActionListener(CobolCompilerGUI::cancelCompilation);

        controlPanel.add(compileButton);
        controlPanel.add(cancelButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(textPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void cancelCompilation(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to cancel?", "Cancel Compilation", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            javaOutput.setText("Compilation Canceled.");
            javaOutput.setForeground(Color.RED);
        }
    }

    private static void browseFile(ActionEvent e) {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select COBOL File");
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            try {
                cobolInput.setText(new String(Files.readAllBytes(selectedFile.toPath())));
            } catch (Exception ex) {
                showError("Error reading file: " + ex.getMessage());
            }
        }
    }

    private static void selectOutputFolder(ActionEvent e) {
        outputFolderChooser = new JFileChooser();
        outputFolderChooser.setDialogTitle("Select Output Folder");
        outputFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = outputFolderChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            outputFolder = outputFolderChooser.getSelectedFile();
        }
    }

    private static void toggleDarkMode(ActionEvent e) {
        boolean darkModeEnabled = darkMode.isSelected();
        Color backgroundColor = darkModeEnabled ? Color.DARK_GRAY : Color.WHITE;
        Color textColor = darkModeEnabled ? Color.WHITE : Color.BLACK;
        frame.getContentPane().setBackground(backgroundColor);
        cobolInput.setBackground(backgroundColor);
        cobolInput.setForeground(textColor);
        javaOutput.setBackground(backgroundColor);
        javaOutput.setForeground(textColor);
    }

    private static void compileCobol(ActionEvent e) {
        try {
            if (selectedFile == null) {
                showError("No valid COBOL file selected.");
                return;
            }
            if (debugMode.isSelected()) Debugger.enableDebugging();
            String sourceCode = cobolInput.getText();

            List<Token> tokens = CobolLexer.tokenize(sourceCode);
            CobolParser parser = new CobolParser(tokens);
            ASTNode ast = parser.parseProgram();

            JavaCodeGenerator generator = new JavaCodeGenerator(ast);
            String javaCode = generator.generateCode();

            javaOutput.setText(javaCode);
            javaOutput.setForeground(Color.GREEN); // Show success in green text

            if (outputFolder != null) {
                Files.write(Paths.get(outputFolder.getAbsolutePath(), "ConvertedProgram.java"), javaCode.getBytes());
            }
        } catch (Exception ex) {
            javaOutput.setText("Compilation Error: " + ex.getMessage());
            javaOutput.setForeground(Color.RED); // Show errors in red text
        }
    }

    private static void showError(String message) {
        javaOutput.setText(message);
        javaOutput.setForeground(Color.RED);
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
