package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.Project;
import fiuba.mda.ui.dialogs.SimpleDialogController;
import fiuba.mda.ui.utilities.ImageLoader;

@Singleton
public class NewProjectAction extends Action {
	private final DocumentModel model;
	private final SimpleDialogController dialog;

	@Inject
	public NewProjectAction(final DocumentModel model,
			final SimpleDialogController dialog, final ImageLoader imageLoader) {
		this.model = model;
		this.dialog = dialog;

		setText("&Nuevo Proyecto@Ctrl+Shift+N");
		setToolTipText("Cerrar el proyecto actual y crear un nuevo proyecto");

		Image image = imageLoader.loadImage("project-new");
		ImageDescriptor imageDescriptor = ImageDescriptor
				.createFromImage(image);
		setImageDescriptor(imageDescriptor);
	}

	@Override
	public void run() {
		String name = dialog.showInput("Nuevo proyecto",
				"Nombre del nuevo proyecto", null, projectNameValidator);
		if (name != null) {
			Project newProject = new Project(name.trim());
			model.openProject(newProject);
		}
	}

	private final IInputValidator projectNameValidator = new IInputValidator() {
		@Override
		public String isValid(final String value) {
			if (value == null || value.trim().isEmpty()) {
				return "El nombre del nuevo proyecto no puede ser vacio";
			}

			if (value.contains(" ")) {
				return "El nombre del nuevo proyecto no puede contener espacios";
			}
			return null;
		}
	};

}
