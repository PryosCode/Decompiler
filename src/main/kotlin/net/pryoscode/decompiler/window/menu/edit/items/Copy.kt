package net.pryoscode.decompiler.window.menu.edit.items

import net.pryoscode.decompiler.window.container.Code
import net.pryoscode.decompiler.window.container.Container
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class Copy : JMenuItem("Copy", KeyEvent.VK_C), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)

        Container.selectionModel.selectedItemProperty().addListener { _, _, item ->
            isEnabled = false
            if (item != null) {
                (item as Code).codeArea.selectedTextProperty().addListener { _, _, text ->
                    isEnabled = text.isNotEmpty()
                }
            }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        val tab = Container.selectionModel.selectedItem as Code
        val text = tab.codeArea.selectedText
        val content = StringSelection(text)
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(content, content)
    }

}