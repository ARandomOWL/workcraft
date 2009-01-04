package org.workcraft.plugins.graph;

import org.w3c.dom.Element;
import org.workcraft.dom.Connection;
import org.workcraft.dom.DisplayName;
import org.workcraft.dom.MathModel;
import org.workcraft.dom.VisualClass;
import org.workcraft.framework.Framework;
import org.workcraft.framework.exceptions.DuplicateIDException;
import org.workcraft.framework.exceptions.InvalidComponentException;
import org.workcraft.framework.exceptions.InvalidConnectionException;
import org.workcraft.framework.exceptions.ModelLoadFailedException;
import org.workcraft.framework.exceptions.ModelValidationException;

@DisplayName ("Directed Graph")
@VisualClass("org.workcraft.plugins.graph.VisualGraph")
public class Graph extends MathModel {
	protected static final Class<?>[] supportedComponents = new Class<?>[] { Vertex.class };

	public Graph(Framework framework) {
		super(framework);
	}

	public Graph(Framework framework, Element xmlElement, String sourcePath) throws ModelLoadFailedException {
		super(framework, xmlElement, sourcePath);
	}


	@Override
	public Class<?>[] getSupportedComponents() {
		return supportedComponents;
	}


	@Override
	public void validate() throws ModelValidationException {
	}


	@Override
	protected void validateConnection(Connection connection)
	throws InvalidConnectionException {
	}

	public Vertex createVertex(String label) {
		Vertex v = new Vertex();
		v.setLabel(label);
		try {
			addComponent(v);
		} catch (InvalidComponentException e) {

		} catch (DuplicateIDException e) {
			e.printStackTrace();
		}
		return v;
	}

	public Class<?>[] getComponentClasses() {
		return new Class<?>[] {
				Vertex.class
		};
	}
}