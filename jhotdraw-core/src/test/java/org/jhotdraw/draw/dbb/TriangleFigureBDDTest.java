package org.jhotdraw.draw.dbb;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.draw.figure.TriangleFigure;
import org.junit.Test;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

import static org.assertj.core.api.Assertions.assertThat; // Lab指示: AssertJを使用

/**
 * BDD Test for TriangleFigure using JGiven.
 * Maps directly to the User Story and Scenario defined in the Portfolio.
 */
public class TriangleFigureBDDTest extends ScenarioTest<TriangleFigureBDDTest.GivenTriangle, TriangleFigureBDDTest.WhenTriangle, TriangleFigureBDDTest.ThenTriangle> {

    @Test
    public void a_triangle_can_be_moved_correctly() {
        given().a_triangle_at_coordinates(0, 0, 50, 50);
        when().i_move_the_triangle_by(10, 20);
        then().the_triangle_should_be_at(10, 20);
    }

    // --- Stage Classes (Given, When, Then) ---

    // 1. Given Stage: 状態のセットアップ
    public static class GivenTriangle extends Stage<GivenTriangle> {
        @ProvidedScenarioState
        TriangleFigure triangle;

        public GivenTriangle a_triangle_at_coordinates(double x, double y, double w, double h) {
            triangle = new TriangleFigure();
            triangle.setBounds(new Point2D.Double(x, y), new Point2D.Double(x + w, y + h));
            return self();
        }
    }

    // 2. When Stage: アクションの実行
    public static class WhenTriangle extends Stage<WhenTriangle> {
        @ExpectedScenarioState
        TriangleFigure triangle;

        public WhenTriangle i_move_the_triangle_by(double dx, double dy) {
            AffineTransform tx = new AffineTransform();
            tx.translate(dx, dy);
            triangle.transform(tx);
            return self();
        }
    }

    // 3. Then Stage: 検証 (AssertJを使用)
    public static class ThenTriangle extends Stage<ThenTriangle> {
        @ExpectedScenarioState
        TriangleFigure triangle;

        public ThenTriangle the_triangle_should_be_at(double expectedX, double expectedY) {
            Rectangle2D.Double bounds = triangle.getBounds();

            // Lab指示: AssertJ (assertThat) を使用した記述
            assertThat(bounds.x).as("X coordinate").isEqualTo(expectedX);
            assertThat(bounds.y).as("Y coordinate").isEqualTo(expectedY);
            return self();
        }
    }
}