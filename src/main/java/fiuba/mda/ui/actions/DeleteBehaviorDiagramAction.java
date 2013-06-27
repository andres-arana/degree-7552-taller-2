package fiuba.mda.ui.actions;

import com.google.inject.Inject;
import fiuba.mda.model.Application;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelPackage;
import org.eclipse.jface.action.Action;

/**
 * Created with IntelliJ IDEA.
 * User: Juan-Asus
 * Date: 26/06/13
 * Time: 00:43
 * To change this template use File | Settings | File Templates.
 */
public class DeleteBehaviorDiagramAction extends Action {

    private final Application model;

    private final BehaviorDiagram modelBehaviorDiagramToDelete;

    @Inject
    public DeleteBehaviorDiagramAction(Application model, BehaviorDiagram modelBehaviorDiagramToDelete) {
        this.model = model;
        this.modelBehaviorDiagramToDelete = modelBehaviorDiagramToDelete;
        setupPresentation();
    }


    private void setupPresentation() {
        setText("Borrar");
    }

    @Override
    public void run() {
        ModelAspect parentAspect = (ModelAspect)modelBehaviorDiagramToDelete.getParent();
        parentAspect.removeChildren(modelBehaviorDiagramToDelete);
        if (parentAspect.getChildren().isEmpty()){
            ModelPackage parentPackage = (ModelPackage) parentAspect.getParent();
            parentPackage.removeChildren(parentAspect);
            model.clearActivePackage();
        }
    }
}
