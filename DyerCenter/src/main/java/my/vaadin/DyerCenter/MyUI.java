package my.vaadin.DyerCenter;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	StudentUI sp = new StudentUI();
    	AlumniUI ap = new AlumniUI();
    	EmployeeUI ep = new EmployeeUI();

    	final VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        
        final VerticalLayout buttonLabelLayout = new VerticalLayout();
        mainLayout.addComponent(buttonLabelLayout);
        mainLayout.setComponentAlignment(buttonLabelLayout, Alignment.MIDDLE_CENTER);

        Label headingLabel = new Label("I am a...");
        buttonLabelLayout.addComponent(headingLabel);
        buttonLabelLayout.setComponentAlignment(headingLabel, Alignment.MIDDLE_CENTER);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        
        Button studentButton = new Button("Student");
        
        studentButton.addClickListener(e -> {
        	VerticalLayout studentPage = sp.getStudentPage();
        	setContent(studentPage);
        });
        
        Button alumniButton = new Button("Alumni");
        alumniButton.addClickListener(e -> {
        	VerticalLayout alumniPage = ap.getAlumniPage();
        	setContent(alumniPage);
        });
        
        Button employeeButton = new Button("Dyer Center Employee");
        employeeButton.addClickListener(e -> {
        	VerticalLayout employeePage = ep.getEmployeePage();
        	setContent(employeePage);
        });
        
        buttonLayout.addComponents(studentButton, alumniButton, employeeButton);
        buttonLabelLayout.addComponent(buttonLayout);

        buttonLabelLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
        
        setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
