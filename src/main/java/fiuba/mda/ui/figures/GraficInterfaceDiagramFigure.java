package fiuba.mda.ui.figures;

import fiuba.mda.model.*;
import fiuba.mda.utilities.SimpleEvent.Observer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Figure which displays a behavior diagram
 */
public class GraficInterfaceDiagramFigure extends FreeformLayer {
    public interface Dialogs {
        /**
        * Crea los dialogos para editar los behavior
        */
        public void showTextDialog(BehaviorText behaviorText);
        public void showButtonDialog(BehaviorButton bevaviorButton);
        public void showFieldDialog(BehaviorField behaviorField);
        public void showFormDialog(BehaviorForm behaviorForm);
    }

    private Dialogs dialogs;


	private Observer<GraficInterfaceDiagram> onTextChanged = new Observer<GraficInterfaceDiagram>() {
		@Override
		public void notify(GraficInterfaceDiagram observable) {
			rebindChildFigures();
		}
	};

    private Observer<GraficInterfaceDiagram> onFieldChanged = new Observer<GraficInterfaceDiagram>() {
        @Override
        public void notify(GraficInterfaceDiagram observable) {
            rebindChildFigures();
        }
    };

    private Observer<GraficInterfaceDiagram> onButtonChanged = new Observer<GraficInterfaceDiagram>() {
        @Override
        public void notify(GraficInterfaceDiagram observable) {
            rebindChildFigures();
        }
    };

    private Observer<GraficInterfaceDiagram> onFormChanged = new Observer<GraficInterfaceDiagram>() {
        @Override
        public void notify(GraficInterfaceDiagram observable) {
            rebindChildFigures();
        }
    };

	private final GraficInterfaceDiagram component;


    private List<BehaviorTextFigure> behaviorTextFigures;

    private List<BehaviorButtonFigure> behaviorButtonFigures;

    private List<BehaviorFieldFigure> behaviorFieldFigures;
    private List<BehaviorFormFigure> behaviorFormFigures;

    private SelectionManager selectionManager;

	/**
	 * Creates a new @{link BehaviorDiagramFigure} instance
	 *
	 * @param component
	 *            the {@link fiuba.mda.model.BehaviorDiagram} instance to display
	 */
	public GraficInterfaceDiagramFigure(final GraficInterfaceDiagram component, final Dialogs dialogs) {
		this.component = component;
        this.dialogs = dialogs;
        component.textsChangedEvent().observe(onTextChanged);
        component.fieldsChangedEvent().observe(onFieldChanged);
        component.buttonsChangedEvent().observe(onButtonChanged);
        component.formsChangedEvent().observe(onFormChanged);
		setLayoutManager(new FreeformLayout());
        behaviorTextFigures = new ArrayList<>();
        behaviorButtonFigures = new ArrayList<>();
        behaviorFieldFigures = new ArrayList<>();
        behaviorFormFigures = new ArrayList<>();
		rebindChildFigures();
		component.setDiagramFigure(this);
    }

	private void rebindChildFigures() {
		removeAll();

        selectionManager = new SelectionManager();


		for (final Representation<BehaviorText> text : component.getTexts()) {
            BehaviorTextFigure figure = new BehaviorTextFigure(text, this.dialogs);
            add(selectionManager.wrap(text, figure, figure, new SelectableElementFigure.Removable() {
                public void remove() {
                    component.removeText(text);
                }
            }));
            getBehaviorTextFigures().add(figure);
		}
		for (final Representation<BehaviorButton> button : component.getButtons()) {
			BehaviorButtonFigure figure = new BehaviorButtonFigure(button, this.dialogs);
            add(selectionManager.wrap(button, figure, figure, new SelectableElementFigure.Removable() {
                public void remove() {
                    component.removeButton(button);
                }
            }));
            getBehaviorButtonFigures().add(figure);
		}
		for (final Representation<BehaviorField> field : component.getFields()) {
			BehaviorFieldFigure figure = new BehaviorFieldFigure(field, this.dialogs);
            add(selectionManager.wrap(field, figure, figure, new SelectableElementFigure.Removable() {
                public void remove() {
                    component.removeField(field);
                }
            }));
            getBehaviorFieldFigures().add(figure);
		}
        for (final Representation<BehaviorForm> form : component.getForms()) {
            BehaviorFormFigure figure = new BehaviorFormFigure(form, this.dialogs);
            add(selectionManager.wrap(form, figure, figure, new SelectableElementFigure.Removable() {
                public void remove() {
                    component.removeForm(form);
                }
            }));
            getBehaviorFormFigures().add(figure);
        }
	}

	public List<BehaviorTextFigure> getBehaviorTextFigures() {
		if (behaviorTextFigures == null){
            behaviorTextFigures = new ArrayList<>();
        }
        return behaviorTextFigures;
    } 
	
	public List<BehaviorButtonFigure> getBehaviorButtonFigures() {
		if (behaviorButtonFigures == null){
            behaviorButtonFigures = new ArrayList<>();
        }
        return behaviorButtonFigures;
    } 
	
	public List<BehaviorFieldFigure> getBehaviorFieldFigures() {
		if (behaviorFieldFigures == null){
			behaviorFieldFigures = new ArrayList<>();
        }
        return behaviorFieldFigures;
    }

    public List<BehaviorFormFigure> getBehaviorFormFigures() {
        if (behaviorFormFigures == null){
            behaviorFormFigures = new ArrayList<>();
        }
        return behaviorFormFigures;
    }

    public void removeSelectedObjects() {
        selectionManager.removeSelectedObjects();
    }

    public void removeSelections() {
        selectionManager.removeSelections();
    }    
}
