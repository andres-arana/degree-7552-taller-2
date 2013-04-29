package fiuba.mda.ui.main;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.ProjectComponent;

@Singleton
public class ProjectTreeBuilder {
	private final DocumentModel model;
	private final ProjectTreeLabelProvider labelProvider;
	private final ProjectTreeContentProvider contentProvider;

	@Inject
	public ProjectTreeBuilder(final DocumentModel model,
			final ProjectTreeLabelProvider labelProvider,
			final ProjectTreeContentProvider contentProvider) {
		this.model = model;
		this.labelProvider = labelProvider;
		this.contentProvider = contentProvider;
	}

	public void buildInto(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		result.setLayout(new FillLayout(SWT.VERTICAL));

		CTabFolder tabs = new CTabFolder(result, SWT.NONE);
		tabs.setSimple(false);
		tabs.setBorderVisible(true);

		CTabItem item = new CTabItem(tabs, SWT.NONE);
		item.setText("Explorador de proyecto");

		TreeViewer treeViewer = new TreeViewer(tabs);
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					model.clearActivePackage();
				} else {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					ProjectComponent selectedComponent = (ProjectComponent) selection.getFirstElement();
					model.activatePackage(selectedComponent.closestOwningPackage());
				}
			}
		});
		treeViewer.setInput(model);
		treeViewer.expandAll();

		item.setControl(treeViewer.getControl());
		
		tabs.setSelection(item);
	}
}
