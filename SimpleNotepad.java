import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SimpleNotepad extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile = null;
    private boolean changed = false;

    public SimpleNotepad() {
        super("Notepad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll, BorderLayout.CENTER);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));

        createMenuBar();

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { changed = true; }
            public void removeUpdate(DocumentEvent e) { changed = true; }
            public void changedUpdate(DocumentEvent e) { changed = true; }
        });
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newIt = new JMenuItem("New");
        JMenuItem openIt = new JMenuItem("Open...");
        JMenuItem saveIt = new JMenuItem("Save");
        JMenuItem saveAsIt = new JMenuItem("Save As...");
        JMenuItem exitIt = new JMenuItem("Exit");

        newIt.addActionListener(e -> newFile());
        openIt.addActionListener(e -> openFile());
        saveIt.addActionListener(e -> saveFile());
        saveAsIt.addActionListener(e -> saveFileAs());
        exitIt.addActionListener(e -> exitApp());

        fileMenu.add(newIt);
        fileMenu.add(openIt);
        fileMenu.add(saveIt);
        fileMenu.add(saveAsIt);
        fileMenu.addSeparator();
        fileMenu.add(exitIt);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutIt = new JMenuItem("Cut");
        JMenuItem copyIt = new JMenuItem("Copy");
        JMenuItem pasteIt = new JMenuItem("Paste");
        JMenuItem selectAllIt = new JMenuItem("Select All");

        cutIt.addActionListener(e -> textArea.cut());
        copyIt.addActionListener(e -> textArea.copy());
        pasteIt.addActionListener(e -> textArea.paste());
        selectAllIt.addActionListener(e -> textArea.selectAll());

        editMenu.add(cutIt);
        editMenu.add(copyIt);
        editMenu.add(pasteIt);
        editMenu.addSeparator();
        editMenu.add(selectAllIt);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutIt = new JMenuItem("About");
        aboutIt.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Simple Notepad\nChamod Thejan\ns16215\n2021s18843", "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutIt);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void newFile() {
        if (confirmSaveIfNeeded()) {
            textArea.setText("");
            currentFile = null;
            changed = false;
            setTitle("Notepad");
        }
    }

    private void openFile() {
        if (!confirmSaveIfNeeded()) return;
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                textArea.read(br, null);
                currentFile = f;
                changed = false;
                setTitle("Notepad - " + f.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not open file: " + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile))) {
            textArea.write(bw);
            changed = false;
            setTitle("Notepad - " + currentFile.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
        }
    }

    private void saveFileAs() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            currentFile = f;
            saveFile();
        }
    }

    private boolean confirmSaveIfNeeded() {
        if (!changed) return true;
        int result = JOptionPane.showConfirmDialog(this, "Save changes?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION) return false;
        if (result == JOptionPane.YES_OPTION) {
            saveFile();
            return !changed; 
        }
        return true;
    }

    private void exitApp() {
        if (confirmSaveIfNeeded()) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleNotepad n = new SimpleNotepad();
            n.setVisible(true);
        });
    }
}
