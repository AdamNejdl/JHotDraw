package org.jhotdraw.draw.dbb;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.draw.figure.TriangleFigure;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.assertj.core.api.Assertions.assertThat;

public class TriangleFigureBDDTest extends ScenarioTest<TriangleFigureBDDTest.GivenTriangle, TriangleFigureBDDTest.WhenTriangle, TriangleFigureBDDTest.ThenTriangle> {

    @Test
    public void a_triangle_can_be_moved() {
        given().a_triangle_at_coordinates(0, 0, 50, 50);
        when().i_move_the_triangle_by(10, 20);
        then().the_triangle_should_be_at(10, 20);
    }

    @Test
    public void a_triangle_can_be_resized() {
        given().a_triangle_at_coordinates(0, 0, 50, 50);
        when().i_resize_the_triangle_to(0, 0, 100, 100);
        then().the_width_should_be(100)
                .and().the_height_should_be(100);
    }

    @Test
    public void a_triangle_detects_points_inside_it() {
        given().a_triangle_at_coordinates(0, 0, 50, 50);
        then().the_point_$_$_should_be_inside(25, 25)
                .and().the_point_$_$_should_be_outside(60, 60);
    }

    static class GivenTriangle extends Stage<GivenTriangle> {
        @ProvidedScenarioState
        TriangleFigure figure;

        public GivenTriangle a_triangle_at_coordinates(double x, double y, double width, double height) {
            figure = new TriangleFigure();
            figure.setBounds(new Point2D.Double(x, y), new Point2D.Double(x + width, y + height));
            return self();
        }
    }

    static class WhenTriangle extends Stage<WhenTriangle> {
        @ExpectedScenarioState
        TriangleFigure figure;

        public WhenTriangle i_move_the_triangle_by(double dx, double dy) {
            figure.transform(java.awt.geom.AffineTransform.getTranslateInstance(dx, dy));
            return self();
        }

        public WhenTriangle i_resize_the_triangle_to(double x, double y, double width, double height) {
            Point2D.Double anchor = new Point2D.Double(x, y);
            Point2D.Double lead = new Point2D.Double(x + width, y + height);
            figure.setBounds(anchor, lead);
            return self();
        }
    }

    static class ThenTriangle extends Stage<ThenTriangle> {
        @ExpectedScenarioState
        TriangleFigure figure;

        public ThenTriangle the_triangle_should_be_at(double x, double y) {
            assertThat(figure.getBounds().x).isEqualTo(x);
            assertThat(figure.getBounds().y).isEqualTo(y);
            return self();
        }

        public ThenTriangle the_width_should_be(double width) {
            assertThat(figure.getBounds().width).isEqualTo(width);
            return self();
        }

        public ThenTriangle the_height_should_be(double height) {
            assertThat(figure.getBounds().height).isEqualTo(height);
            return self();
        }

        public ThenTriangle the_point_$_$_should_be_inside(double x, double y) {
            boolean isInside = figure.contains(new Point2D.Double(x, y));
            assertThat(isInside)
                    .as("Point (" + x + "," + y + ") should be inside")
                    .isTrue();
            return self();
        }

        public ThenTriangle the_point_$_$_should_be_outside(double x, double y) {
            boolean isInside = figure.contains(new Point2D.Double(x, y));
            assertThat(isInside)
                    .as("Point (" + x + "," + y + ") should be outside")
                    .isFalse();
            return self();
        }
    }
}