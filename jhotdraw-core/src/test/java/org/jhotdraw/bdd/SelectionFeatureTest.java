package org.jhotdraw.bdd;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.bdd.stages.GivenFigures;
import org.jhotdraw.bdd.stages.ThenSelectionOutcome;
import org.jhotdraw.bdd.stages.WhenSelectionAction;
import org.junit.Test;

public class SelectionFeatureTest extends ScenarioTest<GivenFigures, WhenSelectionAction, ThenSelectionOutcome> {
    @Test
    public void selecting_figures_of_the_same_type() {
        given().a_drawing_with_two_rectangles_and_an_ellipse()
                .and().a_rectangle_is_selected();

        when().the_user_selects_Select_Same();

        then().two_rectangles_are_selected()
                .and().the_ellipse_is_NOT_selected();
    }
}
