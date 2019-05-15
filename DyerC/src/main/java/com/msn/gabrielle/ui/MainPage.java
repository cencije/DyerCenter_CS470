/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.msn.gabrielle.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.msn.gabrielle.ui.views.Student.SQLProjectStud;
import com.msn.gabrielle.ui.SQLTablesManager;


/**
 * this is the page that initially loads to select if the user wants to go to the student,
 * employee, or alumni page. This is where the login functionality should be. 
 */
@Route(value = "", layout = MainLayout.class)
@HtmlImport("frontend://styles/main-page-styles.html")
public class MainPage extends VerticalLayout
        implements RouterLayout{
	
	/**
	 * Constructor to initialize the page
	 */
    public MainPage() { 
    	setSizeFull();
    	
    	//Dialog with the student, alumni, employee button 
    	Dialog viewDialog = new Dialog();
    	viewDialog.setCloseOnEsc(false);
    	viewDialog.setCloseOnOutsideClick(false);
    	VerticalLayout mainLay = new VerticalLayout();
    	Label iAm = new Label("I am a(n)...");
    	mainLay.add(iAm);
    	
    	Button studentButton = new Button("Student");
    	studentButton.addClickListener( e-> {
    		this.getUI().ifPresent(ui -> ui.navigate("studentPage"));
    		viewDialog.close();

    	});
    	studentButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    	
    	Button alumniButton = new Button("Alumni");
    	alumniButton.addClickListener( e-> {
    		this.getUI().ifPresent(ui -> ui.navigate("alumniPage"));
    		viewDialog.close();
    	});
    	alumniButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    	
    	Button employeeButton = new Button("Employee");
    	employeeButton.addClickListener( e-> {
    		this.getUI().ifPresent(ui -> ui.navigate("employeePage"));
    		viewDialog.close();
    	});
    	
    	employeeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    	HorizontalLayout buttonLay = new HorizontalLayout();
    	buttonLay.add(studentButton, alumniButton, employeeButton);
    	mainLay.add(buttonLay);

    	viewDialog.add(mainLay);
    	viewDialog.open();
    	add(viewDialog);
    	
    	//The sql for loading the student 
    	SQLTablesManager sqlTM = new SQLTablesManager();
    	sqlTM.determineDBStates();
    	SQLProjectStud sqlPS = new SQLProjectStud();
    	//sqlPS.loadMatchingProjects("goodwayj@lafayette.edu");
    	
    	
    }
}
