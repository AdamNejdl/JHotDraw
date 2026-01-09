package org.jhotdraw.draw.tool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.figure.TextHolderFigure;

import static org.junit.Assert.*;

public class ThenDrawingStage extends Stage<ThenDrawingStage> {

    @ExpectedScenarioState
    TextCreationTool tool;

    public ThenDrawingStage floating_text_field_should_appear() {
        assertNotNull(tool.getTextField());
        return self();
    }

    public ThenDrawingStage text_holder_figure_should_have_text(String expected) {
        TextHolderFigure f =
                (TextHolderFigure) tool.getCreatedFigureForTest();

        assertEquals(expected, f.getText());
        return self();
    }

    public void floating_text_field_should_disappear() {
        assertFalse(tool.isEditing());
        self();
    }
}
