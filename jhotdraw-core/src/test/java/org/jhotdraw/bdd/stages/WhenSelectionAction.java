package org.jhotdraw.bdd.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.SelectSameAction;

import java.awt.event.ActionEvent;

public class WhenSelectionAction extends Stage<WhenSelectionAction> {
    @ExpectedScenarioState
    private DrawingEditor editor;

    public WhenSelectionAction the_user_selects_Select_Same() {
        SelectSameAction action = new SelectSameAction(editor);

        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Select Same"));

        return self();
    }
}
