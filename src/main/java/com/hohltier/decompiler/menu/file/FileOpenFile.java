package com.hohltier.decompiler.menu.file;

import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.loader.FileLoader;
import com.hohltier.decompiler.utils.ResourceUtil;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class FileOpenFile extends JMenuItem implements ActionListener {

    private final String title;

    public FileOpenFile() {
        title = ResourceUtil.getTranslation("file.openFile");
        setText(String.format("%s...", title));
        setMnemonic(KeyEvent.VK_O);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Java (*.jar, *.war, *.zip)", "jar", "war", "zip"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.showOpenDialog(WindowController.getINSTANCE().getComponent());
        FileLoader.load(List.of(fileChooser.getSelectedFiles()));
    }

}