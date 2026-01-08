package org.jhotdraw.action.edit;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.datatransfer.ClipboardUtil;

import javax.swing.text.JTextComponent;
import javax.swing.JTextArea;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;

import static org.assertj.core.api.Assertions.assertThat;

public class DrawingEditingScenario extends Stage<DrawingEditingScenario> {

    @ProvidedScenarioState
    JTextComponent textComponent;

    @ProvidedScenarioState
    Clipboard clipboard = ClipboardUtil.getClipboard();

    // GIVEN

    public DrawingEditingScenario a_text_component_with_text_and_selection(String text, int start, int end) {
        this.textComponent = new JTextArea(text);
        this.textComponent.select(start, end);
        return self();
    }

    public DrawingEditingScenario a_text_component_with_text_and_caret(String text, int caret) {
        this.textComponent = new JTextArea(text);
        this.textComponent.setCaretPosition(caret);
        this.textComponent.setEnabled(true);
        textComponent.requestFocusInWindow();
        return self();
    }

    public DrawingEditingScenario clipboard_has_content(String content) {
        clipboard.setContents(new StringSelection(content), null);
        return self();
    }

    // WHEN

    public DrawingEditingScenario the_user_performs_copy() {
        new CopyAction(textComponent).actionPerformed(null);
        return self();
    }

    public DrawingEditingScenario the_user_performs_cut() {
        new CutAction(textComponent).actionPerformed(null);
        return self();
    }

    public DrawingEditingScenario the_user_performs_paste() {
        new PasteAction(textComponent).actionPerformed(null);
        return self();
    }

    public DrawingEditingScenario the_user_performs_delete() {
        new DeleteAction(textComponent).actionPerformed(new ActionEvent(textComponent, 0, ""));
        return self();
    }

    // THEN

    public DrawingEditingScenario the_clipboard_should_contain(String expected) {
        try {
            String content = (String) clipboard.getData(DataFlavor.stringFlavor);
            assertThat(content).isEqualTo(expected);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return self();
    }

    public DrawingEditingScenario the_text_component_should_have_text(String expected) {
        assertThat(textComponent.getText()).isEqualTo(expected);
        return self();
    }
}