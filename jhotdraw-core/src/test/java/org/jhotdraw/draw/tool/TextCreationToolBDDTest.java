package org.jhotdraw.draw.tool;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;
import java.awt.Point;

public class TextCreationToolBDDTest
        extends ScenarioTest<GivenDrawingStage, WhenUserStage, ThenDrawingStage> {

    @Test
    public void user_can_create_text_and_edit_it() {
        given().a_drawing_view_is_open()
                .the_user_has_selected_the_TextCreationTool();

        when().clicks_on_location(new Point(100, 100))
                .types_text_and_presses_enter("aaa");

        then().floating_text_field_should_appear()
                .text_holder_figure_should_have_text("aaa")
                .floating_text_field_should_disappear();
    }
}
