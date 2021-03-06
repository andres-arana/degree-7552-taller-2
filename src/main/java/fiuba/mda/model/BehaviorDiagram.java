package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;

import fiuba.mda.ui.actions.ExportableToImage;
import fiuba.mda.utilities.SimpleEvent;

/**
 * Represents a behavior diagram which presents details of a particular user
 * interaction flow on the modeled software
 */
public class BehaviorDiagram extends AbstractLeafProjectComponent implements
		ExportableToImage {
	private transient SimpleEvent<BehaviorDiagram> statesChangedEvent;
	private transient SimpleEvent<BehaviorDiagram> relationChangedEvent;

	private List<Representation<BehaviorState>> states;

	private Representation<BehaviorState> initialState;

	private List<Representation<BehaviorRelation>> relations;

	private transient FreeformLayer diagram;

	/*
	 * private final List<Representation<BehaviorText>> texts = new
	 * ArrayList<>();
	 * 
	 * private final List<Representation<BehaviorButton>> buttons = new
	 * ArrayList<>();
	 * 
	 * private final List<Representation<BehaviorField>> fields = new
	 * ArrayList<>();
	 */

	/**
	 * Creates a new {@link BehaviorDiagram} instance
	 * 
	 * @param name
	 *            the name which represents this diagram
	 */
	public BehaviorDiagram(final String name) {
		super(name);
	}

	@Override
	public void init() {
		super.init();
		statesChangedEvent = new SimpleEvent<>(this);
		relationChangedEvent = new SimpleEvent<>(this);

		if (states == null) {
			states = new ArrayList<>();
			initialState = null;
			relations = new ArrayList<>();
		}
	}

	@Override
	public void accept(ProjectComponentVisitor visitor) {
		visitor.visit(this);
	}

	public List<Representation<BehaviorState>> getStates() {
		return Collections.unmodifiableList(states);
	}

	public List<Representation<BehaviorRelation>> getRelations() {
		return Collections.unmodifiableList(relations);
	}

	/*
	 * public List<Representation<BehaviorText>> getTexts() { return
	 * Collections.unmodifiableList(texts); }
	 * 
	 * public List<Representation<BehaviorButton>> getButtons() { return
	 * Collections.unmodifiableList(buttons); }
	 * 
	 * public List<Representation<BehaviorField>> getFields() { return
	 * Collections.unmodifiableList(fields); }
	 */

	public void addState(Representation<BehaviorState> state) {
		if (state.getEntity().getType().equals(BehaviorState.FORM_ENTRADA))
			initialState = state;
		states.add(state);
		statesChangedEvent.raise();
	}

	public void addRelation(Representation<BehaviorRelation> relation) {
		updateBilateralRelationIfExists(relation);
		relations.add(relation);
		relationChangedEvent.raise();
	}

	public void removeState(Representation<BehaviorState> state) {
		states.remove(state);
		statesChangedEvent.raise();

		ArrayList<Representation<BehaviorRelation>> relationsCopy = new ArrayList<Representation<BehaviorRelation>>(
				relations);
		// eliminar las relations que estan colgadas
		for (Representation<BehaviorRelation> relation : relationsCopy) {
			if (relation.getEntity().getInitialState() == state.getEntity()
					|| relation.getEntity().getFinalState() == state
							.getEntity()) {
				removeRelation(relation);
			}
		}

	}

	public void removeRelation(Representation<BehaviorRelation> relation) {
		relations.remove(relation);
		relationChangedEvent.raise();
	}

	private void updateBilateralRelationIfExists(
			Representation<BehaviorRelation> relation) {
		Representation<BehaviorRelation> existingRelation = findRelationByStates(
				relation.getEntity().getFinalState(), relation.getEntity()
						.getInitialState());
		if (existingRelation != null) {
			existingRelation.getEntity().setBilateralRelation(
					relation.getEntity());
			relation.getEntity().setBilateralRelation(
					existingRelation.getEntity());
		}
	}

	private Representation<BehaviorRelation> findRelationByStates(
			BehaviorState initialState, BehaviorState finalState) {
		for (Representation<BehaviorRelation> behaviorRelation : relations) {
			if (behaviorRelation.getEntity().getInitialState().getName()
					.equals(initialState.getName())
					&& behaviorRelation.getEntity().getFinalState().getName()
							.equals(finalState.getName())) {
				return behaviorRelation;
			}
		}
		return null;
	}

	/**
	 * An event raised when some states have been added or removed
	 * 
	 * @return the event
	 */
	public SimpleEvent<BehaviorDiagram> statesChangedEvent() {
		return statesChangedEvent;
	}

	public SimpleEvent<BehaviorDiagram> relationChangedEvent() {
		return relationChangedEvent;
	}

	public BehaviorState getStateByName(String name) {
		for (Representation<BehaviorState> state : getStates()) {
			if (state.getEntity().getName().equals(name))
				return state.getEntity();
		}
		return null; // To change body of created methods use File | Settings |
						// File Templates.
	}

	public boolean hasInitialState() {
		if (initialState == null)
			return false;
		return true;
	}

	public void setDiagramFigure(FreeformLayer diagram) {
		this.diagram = diagram;
	}

	public FreeformLayer getDiagramFigure() {
		return this.diagram;
	}
}
