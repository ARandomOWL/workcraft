package org.workcraft.workspace;

import java.sql.Timestamp;
import java.util.UUID;

import org.workcraft.Version;
import org.workcraft.dom.Model;
import org.workcraft.dom.ModelDescriptor;
import org.workcraft.dom.math.MathModel;
import org.workcraft.dom.visual.VisualModel;

public class ModelEntry {
    private final ModelDescriptor descriptor;
    private final Model model;
    private Stamp stamp;
    private Version version;

    public ModelEntry(ModelDescriptor descriptor, Model model) {
        this.descriptor = descriptor;
        this.model = model;
    }

    public ModelDescriptor getDescriptor() {
        return descriptor;
    }

    public Model getModel() {
        return model;
    }

    public VisualModel getVisualModel() {
        if (isVisual()) {
            return (VisualModel) getModel();
        } else {
            return null;
        }
    }

    public MathModel getMathModel() {
        if (isVisual()) {
            return getVisualModel().getMathModel();
        } else {
            return (MathModel) getModel();
        }
    }

    public boolean isVisual() {
        return getModel() instanceof VisualModel;
    }

    public boolean isApplicable(Class<?> cls) {
        return getAs(cls) != null;
    }

    public boolean isApplicableExact(Class<?> cls) {
        boolean result = false;
        final Model model = getModel();
        if (model.getClass() == cls) {
            result = true;
        } else if (isVisual()) {
            final MathModel mathModel = getMathModel();
            result = mathModel.getClass() == cls;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAs(Class<T> cls) {
        if (cls.isInstance(getModel())) {
            return (T) getModel();
        }
        if (isVisual()) {
            final MathModel mathModel = getMathModel();
            if (cls.isInstance(mathModel)) {
                return (T) mathModel;
            }
        }
        return null;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public Stamp getStamp() {
        if (stamp == null) {
            long currentTime = System.currentTimeMillis();
            String time = new Timestamp(currentTime).toString();
            String uuid = UUID.randomUUID().toString();
            stamp = new Stamp(time, uuid);
        }
        return stamp;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

}
