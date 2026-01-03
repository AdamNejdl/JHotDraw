package org.jhotdraw.bdd.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.EllipseFigure;
import org.jhotdraw.draw.figure.RectangleFigure;

import static org.assertj.core.api.Assertions.*;

public class ThenSelectionOutcome extends Stage<ThenSelectionOutcome> {
    @ExpectedScenarioState
    private DrawingView view;

    @ExpectedScenarioState
    private Drawing drawing;

    public ThenSelectionOutcome two_rectangles_are_selected() {
        assertThat(view.getSelectedFigures())
                .filteredOn(f -> f instanceof RectangleFigure)
                .hasSize(2);
        return self();
    }

    public ThenSelectionOutcome the_ellipse_is_NOT_selected() {
        assertThat(view.getSelectedFigures())
                .filteredOn(f -> f instanceof EllipseFigure)
                .isEmpty();
        return self();
    }

    public ThenSelectionOutcome all_figures_are_selected() {
        assertThat(view.getSelectedFigures())
                .hasSize(drawing.getChildCount())
                .containsAll(drawing.getChildren());
        return self();
    }

    public ThenSelectionOutcome no_figures_are_selected() {
        assertThat(view.getSelectedFigures())
                .isEmpty();
        return self();
    }
}
