package org.jhotdraw.action.edit;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class DrawingEditingTest extends ScenarioTest<DrawingEditingScenario, DrawingEditingScenario, DrawingEditingScenario> {

    static { System.setProperty("jgiven.disableByteBuddy", "true"); }

    @Test
    public void selected_text_can_be_copied_to_clipboard() {
        given().a_text_component_with_text_and_selection("Hello World", 0, 5)
                .and().clipboard_has_content("Initial");

        when().the_user_performs_copy();

        then().the_clipboard_should_contain("Hello");
    }

    @Test
    public void selected_text_can_be_cut_from_text_component() {
        given().a_text_component_with_text_and_selection("Hello World", 0, 5)
                .and().clipboard_has_content("Initial");

        when().the_user_performs_cut();

        then().the_clipboard_should_contain("Hello")
                .and().the_text_component_should_have_text(" World");
    }

    @Test
    public void clipboard_content_can_be_pasted_into_text_component() {
        given().a_text_component_with_text_and_caret("World", 0)
                .and().clipboard_has_content("Hello ");

        when().the_user_performs_paste();

        then().the_text_component_should_have_text("Hello World");
    }

    @Test
    public void selected_text_can_be_deleted() {
        given().a_text_component_with_text_and_caret("Hello World", 10);

        when().the_user_performs_delete();

        then().the_text_component_should_have_text("Hello Worl");
    }
}