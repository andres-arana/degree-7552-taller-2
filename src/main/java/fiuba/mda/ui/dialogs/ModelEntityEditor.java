package fiuba.mda.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.inject.Inject;

import fiuba.mda.model.ModelEntity;
import fiuba.mda.ui.actions.validators.NameValidatorFactory;

public class ModelEntityEditor extends
		org.eclipse.jface.dialogs.TitleAreaDialog {
	private ModelEntity component;
	private Label nameLabel;
	private Text nameText;
	private NameValidatorFactory validatorFactory;
	private IInputValidator validator;
	private AtributosTabla attributesTable;
	private Label kindLabel;
	private Combo kindCombo;

	@Inject
	public ModelEntityEditor(final Shell shell,
			final NameValidatorFactory validatorFactory) {
		super(shell);
		this.validatorFactory = validatorFactory;
	}

	public void edit(final ModelEntity component) {
		this.component = component;
		this.validator = validatorFactory.validatorForRenameInParent(
				component.getParent(), component);
		this.open();
	}

	private void acceptData() {
		component.setName(nameText.getText());
		component.setKind(ModelEntity.Kind.valueOf(this.kindCombo.getText()));

		component.getAttributes().addAll(attributesTable.getElementos());
		component.getAttributes().removeAll(
				attributesTable.getElementosEliminados());
	}

	@Override
	protected void okPressed() {
		acceptData();
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(800, 600);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);

		this.getButton(IDialogConstants.OK_ID).setText("Aceptar");
		this.getButton(IDialogConstants.CANCEL_ID).setText("Cancelar");

		return control;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		this.setMessage("Editando entidad " + component.getQualifiedName(),
				IMessageProvider.INFORMATION);

		Composite header = new Composite(dialogArea, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		header.setLayout(new GridLayout(2, false));

		this.nameLabel = new Label(header, SWT.LEFT);
		this.nameLabel.setText("Nombre");
		this.nameText = new Text(header, SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		this.nameText.setText(component.getName());
		this.nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				String result = validator.isValid(nameText.getText());
				if (result == null) {
					getButton(IDialogConstants.OK_ID).setEnabled(true);
					setErrorMessage(null);
				} else {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
					setErrorMessage(result);
				}
			}
		});

		this.kindLabel = new Label(header, SWT.LEFT);
		this.kindLabel.setText("Tipo");
		this.kindCombo = new Combo(header, SWT.READ_ONLY | SWT.BORDER);
		this.kindCombo.setItems(ModelEntity.KINDS);
		this.kindCombo.setText(component.getKind().name());

		Group grupoAtributos = new Group(dialogArea, SWT.NONE);
		grupoAtributos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		grupoAtributos.setText("Atributos");
		grupoAtributos.setLayout(new GridLayout(1, true));

		Composite botones = new Composite(grupoAtributos, SWT.NONE);
		botones.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1,
				1));
		botones.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNuevoAtributo = new Button(botones, SWT.PUSH);
		btnNuevoAtributo.setText("Nuevo");

		Button btnEliminarAtributo = new Button(botones, SWT.PUSH);
		btnEliminarAtributo.setText("Eliminar");

		this.attributesTable = new AtributosTabla(grupoAtributos, component);
		this.attributesTable.setElementos(component.getAttributes());

		btnNuevoAtributo.addSelectionListener(this.attributesTable.nuevo);
		btnEliminarAtributo.addSelectionListener(this.attributesTable.eliminar);

		return dialogArea;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edición de Entidad");
	}

}
