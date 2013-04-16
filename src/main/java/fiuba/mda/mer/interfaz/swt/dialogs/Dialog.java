package fiuba.mda.mer.interfaz.swt.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import fiuba.mda.mer.interfaz.swt.Principal;

public abstract class Dialog extends org.eclipse.jface.dialogs.Dialog {
	protected Principal principal = Principal.getInstance();
	protected String titulo = "";

	public Dialog() {
		super(Principal.getInstance().getShell());
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);

		Button btnOK = this.getButton(IDialogConstants.OK_ID);
		Button btnCancel = this.getButton(IDialogConstants.CANCEL_ID);
		btnOK.setText("Aceptar");
		btnCancel.setText("Cancelar");

		return control;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(this.titulo);
	}
}
