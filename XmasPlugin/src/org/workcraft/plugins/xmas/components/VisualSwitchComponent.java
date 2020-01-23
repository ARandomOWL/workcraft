package org.workcraft.plugins.xmas.components;

import org.workcraft.annotations.DisplayName;
import org.workcraft.annotations.SVGIcon;
import org.workcraft.dom.visual.DrawRequest;
import org.workcraft.dom.visual.Positioning;
import org.workcraft.gui.properties.PropertyDeclaration;
import org.workcraft.gui.tools.Decoration;
import org.workcraft.plugins.xmas.XmasSettings;
import org.workcraft.plugins.xmas.components.SwitchComponent.Type;
import org.workcraft.plugins.xmas.components.SwitchComponent.Val;
import org.workcraft.utils.Coloriser;

import java.awt.*;
import java.awt.geom.Path2D;

@DisplayName("Switch")
@SVGIcon("images/xmas-node-switch.svg")
public class VisualSwitchComponent extends VisualXmasComponent {

    private static final double POINTER_SIZE = 0.20 * SIZE;

    public VisualSwitchComponent(SwitchComponent component) {
        super(component);
        if (component.getChildren().isEmpty()) {
            this.createInput(Positioning.LEFT);
            this.createOutput(Positioning.TOP_RIGHT);
            this.createOutput(Positioning.BOTTOM_RIGHT);
        }
        addPropertyDeclarations();
    }

    private void addPropertyDeclarations() {
        addPropertyDeclaration(new PropertyDeclaration<>(Type.class,
                SwitchComponent.PROPERTY_TYPE,
                value -> getReferencedComponent().setType(value),
                () -> getReferencedComponent().getType())
                .setCombinable().setTemplatable());

        addPropertyDeclaration(new PropertyDeclaration<>(Val.class,
                SwitchComponent.PROPERTY_VAL,
                value -> getReferencedComponent().setVal(value),
                () -> getReferencedComponent().getVal())
                .setCombinable().setTemplatable());
    }

    @Override
    public SwitchComponent getReferencedComponent() {
        return (SwitchComponent) super.getReferencedComponent();
    }

    public Shape getUpPointerShape() {
        Path2D shape = new Path2D.Double();
        shape.moveTo(+0.50 * SIZE, -0.28 * SIZE);
        shape.lineTo(+0.50 * SIZE + 0.7 * POINTER_SIZE, -0.28 * SIZE + POINTER_SIZE);
        shape.lineTo(+0.50 * SIZE - 0.7 * POINTER_SIZE, -0.28 * SIZE + POINTER_SIZE);
        shape.closePath();
        return shape;
    }

    public Shape getDownPointerShape() {
        Path2D shape = new Path2D.Double();
        shape.moveTo(+0.50 * SIZE, +0.28 * SIZE);
        shape.lineTo(+0.50 * SIZE + 0.7 * POINTER_SIZE, +0.28 * SIZE - POINTER_SIZE);
        shape.lineTo(+0.50 * SIZE - 0.7 * POINTER_SIZE, +0.28 * SIZE - POINTER_SIZE);
        shape.closePath();
        return shape;
    }

    @Override
    public void draw(DrawRequest r) {
        super.draw(r);
        Graphics2D g = r.getGraphics();
        Decoration d = r.getDecoration();
        if (d instanceof StateDecoration) {
            g.setStroke(new BasicStroke((float) XmasSettings.getBorderWidth()));
            g.setColor(Coloriser.colorise(getForegroundColor(), d.getColorisation()));
            if (((StateDecoration) d).getState()) {
                Shape shape = transformShape(getUpPointerShape());
                g.fill(shape);
                g.draw(shape);
            } else {
                Shape shape = transformShape(getDownPointerShape());
                g.fill(shape);
                g.draw(shape);
            }
        }
    }

    @Override
    public Shape getShape() {
        Path2D shape = new Path2D.Double();

        shape.moveTo(-0.50 * SIZE, +0.00);
        shape.lineTo(-0.08, +0.00);

        shape.moveTo(0.00, -0.60 * SIZE);
        shape.lineTo(0.00, +0.60 * SIZE);

        shape.moveTo(0.00, -0.50 * SIZE);
        shape.lineTo(+0.50 * SIZE, -0.50 * SIZE);

        shape.moveTo(0.00, +0.50 * SIZE);
        shape.lineTo(+0.50 * SIZE, +0.50 * SIZE);

        // Arrows
        shape.moveTo(-0.15 * SIZE, -0.05 * SIZE);
        shape.lineTo(-0.05 * SIZE, +0.00);
        shape.lineTo(-0.15 * SIZE, +0.05 * SIZE);
        shape.closePath();

        return shape;
    }

}
