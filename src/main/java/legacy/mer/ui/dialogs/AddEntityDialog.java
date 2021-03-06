package legacy.mer.ui.dialogs;

import java.util.Set;

import legacy.mer.control.EntidadControl;
import legacy.mer.modelo.CurrentOpenProject;
import legacy.mer.modelo.Entidad;
import legacy.mer.ui.editores.Editor;
import legacy.mer.ui.editores.EditorFactory;

import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;


/**
 * Dialog to add an entity to a diagram
 */
public class AddEntityDialog extends AddComponentDialog<Entidad> {

	/**
	 * Creates a new {@link AddEntityDialog} instance
	 */
	@Inject
	public AddEntityDialog(final Shell shell,
			final SelectComponentController selectComponentController,
			final CurrentOpenProject currentProject) {
		super(shell, selectComponentController, currentProject);
	}

	@Override
	protected Set<Entidad> loadComponents() {
		// Obtener las entidades de los ancestros
		Set<Entidad> entidades = queryCurrentProject()
				.getEntidadesDisponibles();
		Set<Entidad> entidadesDiagrama = queryCurrentProject()
				.getEntidadesDiagrama();
		// Quitar las que ya tiene
		entidades.removeAll(entidadesDiagrama);

		return entidades;
	}

	@Override
	protected Editor<?> getEditor() {
		return EditorFactory.getEditor(new EntidadControl());
	}

	@Override
	protected String getComponentName() {
		return "Entidad";
	}

}
