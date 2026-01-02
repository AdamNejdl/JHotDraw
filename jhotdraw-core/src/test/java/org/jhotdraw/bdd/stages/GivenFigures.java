package org.jhotdraw.bdd.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.*;

public class GivenFigures extends Stage<GivenFigures> {
    @ProvidedScenarioState
    private DrawingView view;

    @ProvidedScenarioState
    private Drawing drawing;

    @ProvidedScenarioState
    private DrawingEditor editor;

    public GivenFigures a_drawing_with_two_rectangles_and_an_ellipse() {

        drawing = new DefaultDrawing();
        drawing.add(new RectangleFigure());
        drawing.add(new RectangleFigure());
        drawing.add(new EllipseFigure());

        view = new DefaultDrawingView();
        view.setDrawing(drawing);

        editor = new DefaultDrawingEditor();
        editor.setActiveView(view);

        return self();
    }

    public GivenFigures a_rectangle_is_selected() {
        Figure rect = drawing.getChildren().stream()
                .filter(f -> f instanceof RectangleFigure)
                .findFirst().orElseThrow(()-> new RuntimeException("No RectangleFigure found to select!"));

        view.addToSelection(rect);
        return self();
    }
}
