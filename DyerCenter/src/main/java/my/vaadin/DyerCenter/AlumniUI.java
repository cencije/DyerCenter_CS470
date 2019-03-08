package my.vaadin.DyerCenter;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AlumniUI {
	 VerticalLayout getAlumniPage() {
	    	final VerticalLayout alumniLayout = new VerticalLayout();
	    	alumniLayout.setVisible(true);
	    	alumniLayout.setSizeFull();
	        Label headingLabel = new Label("I am an alumi");
	        alumniLayout.addComponent(headingLabel);
	        alumniLayout.setComponentAlignment(headingLabel, Alignment.MIDDLE_CENTER);
			return alumniLayout;
	 }
}
