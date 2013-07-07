package fiuba.mda.ui.main.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.google.common.base.Optional;

/**
 * Custom {@link Composite} which contains a number of tab folders, each with a
 * name and a displayed {@link Control}. This class wraps the {@link CTabFolder}
 * custom SWT control.
 */
public class TabsFolder extends Composite {
	private final CTabFolder tabs;

	/**
	 * Creates a new @{link TabsFolder} instance
	 * 
	 * @param parent
	 *            the parent {@link Composite} to build the control into
	 * @param style
	 *            the control style
	 */
	public TabsFolder(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		tabs = new CTabFolder(this, SWT.NONE);
		tabs.setSimple(false);
		tabs.setBorderVisible(true);
	}

	/**
	 * Adds a new tab folder for a given control
	 * 
	 * @param text
	 *            the name of the tab folder
	 * @param image
	 *            the image to display for the tab
	 * @param builder
	 *            the builder in charge of building the control of the tab
	 *            folder
	 */
	public void ensureCloseableTab(final String text, final Image image,
			final ControlBuilder builder) {
		ensureTab(text, image, builder, SWT.CLOSE);
	}

	public void ensurePermanentTab(final String text, final Image image,
			final ControlBuilder builder) {
		ensureTab(text, image, builder, SWT.NONE);
	}

	private void ensureTab(final String text, final Image image,
			final ControlBuilder builder, final int style) {
		Optional<CTabItem> tab = findTab(text);
		if (tab.isPresent()) {
			tabs.setSelection(tab.get());
		} else {
			CTabItem item = new CTabItem(tabs, style);
			item.setText(text);
			item.setImage(image);
			item.setControl(builder.buildInto(tabs));
			tabs.setSelection(item);
			tabs.layout();
		}
	}

	public void renameTab(final String oldText, final String newText) {
		Optional<CTabItem> tab = findTab(oldText);
		if (tab.isPresent()) {
			tab.get().setText(newText);
		}
	}

	public void deleteTab(final String oldText) {
		Optional<CTabItem> tab = findTab(oldText);
		if (tab.isPresent()) {
			tab.get().dispose();
		}
	}

	private Optional<CTabItem> findTab(String text) {
		for (CTabItem item : tabs.getItems()) {
			if (item.getText().equals(text)) {
				return Optional.of(item);
			}
		}
		return Optional.absent();
	}
}
