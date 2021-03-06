package legacy.mer.ui.dialogs;

import java.util.Set;

import legacy.mer.control.RelacionControl;
import legacy.mer.modelo.CurrentOpenProject;
import legacy.mer.modelo.Relacion;
import legacy.mer.ui.editores.Editor;
import legacy.mer.ui.editores.EditorFactory;

import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;


/**
 * Dialog to add a relation to a diagram
 */
public class AddRelationDialog extends AddComponentDialog<Relacion> {

	/**
	 * Creates a new {@link AddRelationDialog} instance
	 */
	@Inject
	public AddRelationDialog(final Shell shell,
			final SelectComponentController selectComponentController,
			final CurrentOpenProject currentProject) {
		super(shell, selectComponentController, currentProject);
	}

	@Override
	protected Set<Relacion> loadComponents() {
		// Obtener las entidades de los ancestros
		Set<Relacion> relaciones = queryCurrentProject()
				.getRelacionesDisponibles();
		Set<Relacion> entidadesDiagrama = queryCurrentProject()
				.getRelacionesDiagrama();
		// Quitar las que ya tiene
		relaciones.removeAll(entidadesDiagrama);

		return relaciones;
	}

	@Override
	protected Editor<?> getEditor() {
		return EditorFactory.getEditor(new RelacionControl());
	}

	@Override
	protected String getComponentName() {
		return "Relacion";
	}
}
