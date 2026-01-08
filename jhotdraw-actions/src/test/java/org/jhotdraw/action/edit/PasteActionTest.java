package org.jhotdraw.action.edit;

import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static org.junit.Assert.*;

public class PasteActionTest {

    private PasteAction pasteAction;
    private JTextArea textArea;

    @Before
    public void setUp() {
        Clipboard clipboard = new Clipboard("test");
        clipboard.setContents(new StringSelection("World"), null);
        ClipboardUtil.setClipboard(clipboard);

        textArea = new JTextArea();
        textArea.setText("Hello ");
        pasteAction = new PasteAction(textArea);
    }

    @Test
    public void testPasteWithContent() {
        textArea.setCaretPosition(textArea.getText().length());
        pasteAction.actionPerformed(null);

        String result = textArea.getText();
        assertEquals("Hello World", result);
    }

    @Test
    public void testPasteWithEmptyClipboard() {
        ClipboardUtil.getClipboard()
                .setContents(new StringSelection(""), null);

        textArea.setText("Hello ");
        textArea.setCaretPosition(textArea.getText().length());

        pasteAction.actionPerformed(null);

        String result = textArea.getText();
        assertEquals("Hello ", result);
    }
}
