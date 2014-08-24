package fiuba.mda.ui.figures;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseListener;
import fiuba.mda.model.IPositionable;

import java.util.ArrayList;
import java.util.List;

public class SelectionManager implements SelectableElementFigure.ISelectEvent {
	private List<SelectableElementFigure> selectables;
	public SelectionManager() {
		selectables = new ArrayList<>();
	}
	public void add(SelectableElementFigure selectable) {
		selectables.add(selectable);

	}
	
	public IFigure wrap(final IPositionable state, final IFigure figure) {
		return wrap(state, figure, null);
	}

	public IFigure wrap(final IPositionable state, final IFigure figure, final MouseListener mouseListener) {
      SelectableElementFigure selectable = new SelectableElementFigure(state, figure, this);
      if (mouseListener != null) selectable.setMouseListenerReceptor(mouseListener);
      this.add(selectable);

      return selectable;
	}
	
	@Override
	public void select(SelectableElementFigure selectedSelectable) {
		for (SelectableElementFigure selectable : selectables) {
			if (selectable != selectedSelectable) {
				selectable.setSelected(false);
			} else {
				selectable.setSelected(true);
			}
		}
	}

	@Override
	public void multiSelect(SelectableElementFigure selectedSelectable) {
		selectedSelectable.setSelected(true);
	}

	@Override
	public void drag(SelectableElementFigure selectedSelectable, Dimension offset) {
		for (SelectableElementFigure selectable : selectables) {
			selectable.drag(offset);
		}
	}

	public void removeSelections() {
		for (SelectableElementFigure selectable : selectables) {
			selectable.setSelected(false);
		}
	}
}