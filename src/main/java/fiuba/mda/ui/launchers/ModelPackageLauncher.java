package fiuba.mda.ui.launchers;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameAndExistenceValidator;

/**
 * {@link EditorLauncher} implementation which allows editing a package
 */
@Singleton
public class ModelPackageLauncher extends BaseEditorLauncher<ModelPackage> {
	private final SimpleDialogLauncher dialogs;
	private final NameAndExistenceValidator packageNameValidator;

	/**
	 * Creates a new @{link {@link ModelPackageLauncher} instance
	 * 
	 * @param dialogs
	 *            the dialog controller used to create the associated dialogs
	 * @param packageNameAndExistenceValidator
	 *            the validator used to validate the package name on the input
	 *            dialogs
	 */
	@Inject
	public ModelPackageLauncher(SimpleDialogLauncher dialogs,
			final NameAndExistenceValidator packageNameAndExistenceValidator) {
		this.dialogs = dialogs;
		this.packageNameValidator = packageNameAndExistenceValidator;
	}

	@Override
	protected void doLaunch(ModelPackage component) {
		final String title = "Paquete " + component.getQualifiedName();
		if(!component.isRoot()) packageNameValidator.setParent(component.getParent());
        Optional<String> name = dialogs.showInput(title, "Nombre",
				component.getName(), packageNameValidator);
        if (name.isPresent()) {
			component.setName(name.get());
		}
	}
}
