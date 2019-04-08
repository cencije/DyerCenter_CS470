package com.msn.gabrielle.ui.views.Employee;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;

@Route(value = "eventaddemp", layout = EmployeePage.class)
@PageTitle("Add Event Page")
public class AddEventEmp extends VerticalLayout {
	
	String eventName, eventLocation, eventDescription;
	String[] arrayLocations = { "Colton Chapel", "Marlo Room"};
	
	public AddEventEmp() {
		Label addTitleLbl = new Label("Title: ");
		Label addLocLbl = new Label("Location: ");
		ComboBox<String> locations = new ComboBox<>("Locations");
		//locations.setItems(Arrays.asList(arrayLocations));;
		
		Label addDescLbl = new Label("Description: ");
	}
	
	VerticalLayout vLabelLayout = new VerticalLayout();
	//vLabelLayout.add(addTitleLbl);

	
}
