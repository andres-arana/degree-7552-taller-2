package fiuba.mda.ui.main.workspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class SecondWizardPage extends WizardPage {

	private Composite container;
	
	private Spinner existingPropertiesQty;
	
	private List<Composite> existingPropertiesAddedUI; // list that contains every pair Label+Property UI added to the form
	private List<String> existingPropertiesAdded;
	
	public SecondWizardPage(String pageName) {
		super(pageName);
		this.setTitle("Campos existentes");
		existingPropertiesAddedUI = new ArrayList<Composite>();
		existingPropertiesAdded = new ArrayList<String>();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
    
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    layout.numColumns = 2;
	    Label fieldsQtyLabel = new Label(container, SWT.NONE);
	    fieldsQtyLabel.setText("Cantidad de campos a agregar:");
	    existingPropertiesQty = new Spinner(container, SWT.BORDER);
	    existingPropertiesQty.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {		
				int currentValue = ((Spinner)e.getSource()).getSelection();
				
				// "Up" spinner button
				if (currentValue > existingPropertiesAddedUI.size()){ 
					// Add label + properties combobox
					existingPropertiesAddedUI.add(getLabelAndPropertiesCombo(container));
				} else{ // "Down" spinner button
					// Remove the component from the main composite
					if (existingPropertiesAddedUI.size() > 0){
						Composite miniCompositeToRemove = existingPropertiesAddedUI.get(existingPropertiesAddedUI.size()-1);

						// Get the combo that will be removed from the UI an delete its selection from existingPropertiesAddedUI list
						Combo comboToRemove = (Combo)miniCompositeToRemove.getChildren()[1]; // [0] = label, [1] = combo
						
						// Remove the selected item as the combo has been removed from the UI
						existingPropertiesAdded.remove(comboToRemove.getText()); 
						
						// Remove combo from the UI
						existingPropertiesAddedUI.get(existingPropertiesAddedUI.size()-1).dispose();
						existingPropertiesAddedUI.remove(existingPropertiesAddedUI.size()-1);

					}
				}
				
				container.layout(); // refreshs the container
			}
	    	
	    });
	   	    
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(true);
	}

	//TODO: Populate with available properties from existing diagrams!!!!
    private Combo getPropertiesToRelateCombobox(Composite parent){
        String[] items = {"Propiedad1", "Propiedad2", "Propiedad3"}; //TODO fill with properties from existing diagrams!!

        Combo propertyNameCombo = new Combo(parent, SWT.READ_ONLY);
        propertyNameCombo.setItems(items);
        propertyNameCombo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedProperty = ((Combo)e.getSource()).getText();
				existingPropertiesAdded.add(selectedProperty); // TODO: Validate repeated elements? 
			}
        	
        });
        
        return propertyNameCombo;
    }
    
    /**
     * 
     * @return a pair label-combobox to select an existing property to add to the form
     */
    private Composite getLabelAndPropertiesCombo(Composite parent){
    	Composite miniComposite = new Composite(parent, SWT.NONE);
    	GridLayout layout = new GridLayout();
    	miniComposite.setLayout(layout);
    	layout.numColumns = 2;
    	
		Label addFieldLabel = new Label(miniComposite, SWT.NONE);
		addFieldLabel.setText("Campo:");

		getPropertiesToRelateCombobox(miniComposite);

    	return miniComposite;
    }

    /**
     * Returns the properties that have been added to the form
     * @return a list with existing properties added to the form
     */
	public List<String> getExistingPropertiesAdded() {
		return existingPropertiesAdded;
	}

	public void setExistingPropertiesAdded(List<String> list) {
		this.existingPropertiesAdded = list;
	}

	
}
