package org.jhotdraw.bdd.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.action.edit.ClearSelectionAction;
import org.jhotdraw.action.edit.SelectAllAction;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.action.SelectSameAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class WhenSelectionAction extends Stage<WhenSelectionAction> {
    @ExpectedScenarioState
    private DrawingEditor editor;

    @ExpectedScenarioState
    private DrawingView view;

    public WhenSelectionAction the_user_selects_Select_Same() {
        SelectSameAction action = new SelectSameAction(editor);

        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Select Same"));

        return self();
    }

    public WhenSelectionAction the_user_selects_Select_All() {
        SelectAllAction action = new SelectAllAction((JComponent) view);

        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Select All"));
        return self();
    }

    public WhenSelectionAction the_user_selects_Clear_Selection() {
        ClearSelectionAction action = new ClearSelectionAction((JComponent) view);

        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Clear Selection"));
        return self();
    }
}
