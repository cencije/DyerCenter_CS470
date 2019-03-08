package my.vaadin.DyerCenter;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class StudentUI {
	VerticalLayout getStudentPage() { 
    	final VerticalLayout studentLayout = new VerticalLayout();
    	studentLayout.setVisible(true);
        studentLayout.setSizeFull();
        Label headingLabel = new Label("I am a student");
        studentLayout.addComponent(headingLabel);
        studentLayout.setComponentAlignment(headingLabel, Alignment.MIDDLE_CENTER);
		return studentLayout;
    }
}
