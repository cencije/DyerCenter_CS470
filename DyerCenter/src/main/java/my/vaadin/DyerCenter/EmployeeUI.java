package my.vaadin.DyerCenter;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class EmployeeUI {
	VerticalLayout getEmployeePage() {
    	final VerticalLayout employeeLayout = new VerticalLayout();
    	employeeLayout.setVisible(true);
    	employeeLayout.setSizeFull();
        Label headingLabel = new Label("I am an employee");
        employeeLayout.addComponent(headingLabel);
        employeeLayout.setComponentAlignment(headingLabel, Alignment.MIDDLE_CENTER);
		return employeeLayout;
	}
}
