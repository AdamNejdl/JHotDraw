package org.jhotdraw.action.edit;

import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import static org.junit.Assert.*;

public class CutActionTest {

    private CutAction cutAction;
    private JTextArea textArea;

    @Before
    public void setUp() {
        Clipboard clipboard = new Clipboard("test");
        clipboard.setContents(new StringSelection("Test"), null);
        ClipboardUtil.setClipboard(clipboard);

        textArea = new JTextArea();
        textArea.setText("Hello World");
        cutAction = new CutAction(textArea);
    }

    @Test
    public void testCutSelectedText() throws Exception {
        textArea.select(0, 5);

        cutAction.actionPerformed(null);

        Clipboard clipboard = ClipboardUtil.getClipboard();
        Transferable contents = clipboard.getContents(null);

        assertNotNull("Clipboard should not be null", contents);
        assertTrue("Clipboard should support string flavor", contents.isDataFlavorSupported(DataFlavor.stringFlavor));

        String clipboardText = (String) contents.getTransferData(DataFlavor.stringFlavor);
        assertEquals("Hello", clipboardText);
    }

    @Test
    public void testCutWithoutSelectionDoesntChangeClipboard() throws Exception {
        textArea.select(0, 0);

        cutAction.actionPerformed(null);

        Clipboard clipboard = ClipboardUtil.getClipboard();
        Transferable contents = clipboard.getContents(null);

        assertNotNull("Clipboard should not be null", contents);
        assertTrue("Clipboard should support string flavor", contents.isDataFlavorSupported(DataFlavor.stringFlavor));

        String clipboardText = (String) contents.getTransferData(DataFlavor.stringFlavor);
        assertEquals("Test", clipboardText);
    }
}