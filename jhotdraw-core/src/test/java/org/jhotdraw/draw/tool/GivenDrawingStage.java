package org.jhotdraw.draw.tool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.TextFigure;

import javax.swing.*;
import java.awt.*;

public class GivenDrawingStage extends Stage<GivenDrawingStage> {

    @ProvidedScenarioState
    DrawingView view;

    @ProvidedScenarioState
    DrawingEditor editor;

    @ProvidedScenarioState
    TextCreationTool tool;

    @ProvidedScenarioState
    JFrame frame;

    public GivenDrawingStage a_drawing_view_is_open() {
        editor = new DefaultDrawingEditor();

        view = new DefaultDrawingView();
        view.setDrawing(new DefaultDrawing());

        frame = new JFrame("test");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.add((Component) view, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.doLayout();

        editor.add(view);

        return self();
    }

    public GivenDrawingStage the_user_has_selected_the_TextCreationTool() {
        tool = new TextCreationTool(new TextFigure());
        editor.setTool(tool);
        return self();
    }
}
