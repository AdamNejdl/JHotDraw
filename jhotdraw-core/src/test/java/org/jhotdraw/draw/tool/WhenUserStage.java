package org.jhotdraw.draw.tool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DrawingView;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class WhenUserStage extends Stage<WhenUserStage> {

    @ExpectedScenarioState
    TextCreationTool tool;

    @ExpectedScenarioState
    DrawingView view;

    public WhenUserStage clicks_on_location(Point p) {
        MouseEvent evt = new MouseEvent(
                view.getComponent(),
                MouseEvent.MOUSE_PRESSED,
                System.currentTimeMillis(),
                0,
                p.x,
                p.y,
                1,
                false
        );

        tool.mousePressed(evt);
        return self();
    }

    public WhenUserStage types_text_and_presses_enter(String text) {
        tool.getTextField()
                .setText(text);

        tool.actionPerformed(null); // ENTER
        return self();
    }
}
