package org.workcraft.plugins.builtin.settings;

import org.workcraft.Config;
import org.workcraft.gui.properties.PropertyDeclaration;
import org.workcraft.gui.properties.PropertyDescriptor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class SelectionDecorationSettings extends AbstractDecorationSettings {
    private static final LinkedList<PropertyDescriptor> properties = new LinkedList<>();
    private static final String prefix = "SelectionDecorationSettings";

    private static final String keyHighlightingColor = prefix + ".highlightingColor";
    private static final String keySelectionColor = prefix + ".selectionColor";
    private static final String keyShadingColor = prefix + ".shadingColor";

    private static final Color defaultHighlightingColor = new Color(1.0f, 0.5f, 0.0f).brighter();
    private static final Color defaultSelectionColor = new Color(99, 130, 191).brighter();
    private static final Color defaultShadingColor = Color.LIGHT_GRAY;

    private static Color highlightingColor = defaultHighlightingColor;
    private static Color selectionColor = defaultSelectionColor;
    private static Color shadingColor = defaultShadingColor;

    public SelectionDecorationSettings() {
        properties.add(new PropertyDeclaration<SelectionDecorationSettings, Color>(
                this, "Highlighting color", Color.class) {
            @Override
            public void setter(SelectionDecorationSettings object, Color value) {
                setHighlightingColor(value);
            }
            @Override
            public Color getter(SelectionDecorationSettings object) {
                return getHighlightingColor();
            }
        });

        properties.add(new PropertyDeclaration<SelectionDecorationSettings, Color>(
                this, "Selection color", Color.class) {
            @Override
            public void setter(SelectionDecorationSettings object, Color value) {
                setSelectionColor(value);
            }
            @Override
            public Color getter(SelectionDecorationSettings object) {
                return getSelectionColor();
            }
        });

        properties.add(new PropertyDeclaration<SelectionDecorationSettings, Color>(
                this, "Shading color", Color.class) {
            @Override
            public void setter(SelectionDecorationSettings object, Color value) {
                setShadingColor(value);
            }
            @Override
            public Color getter(SelectionDecorationSettings object) {
                return getShadingColor();
            }
        });
    }

    @Override
    public List<PropertyDescriptor> getDescriptors() {
        return properties;
    }

    @Override
    public void load(Config config) {
        setHighlightingColor(config.getColor(keyHighlightingColor, defaultHighlightingColor));
        setSelectionColor(config.getColor(keySelectionColor, defaultSelectionColor));
        setShadingColor(config.getColor(keyShadingColor, defaultShadingColor));
    }

    @Override
    public void save(Config config) {
        config.setColor(keyHighlightingColor, getHighlightingColor());
        config.setColor(keySelectionColor, getSelectionColor());
        config.setColor(keyShadingColor, getShadingColor());
    }

    @Override
    public String getName() {
        return "Selection";
    }

    public static void setHighlightingColor(Color value) {
        highlightingColor = value;
    }

    public static Color getHighlightingColor() {
        return highlightingColor;
    }

    public static void setSelectionColor(Color value) {
        selectionColor = value;
    }

    public static Color getSelectionColor() {
        return selectionColor;
    }

    public static void setShadingColor(Color value) {
        shadingColor = value;
    }

    public static Color getShadingColor() {
        return shadingColor;
    }

}
