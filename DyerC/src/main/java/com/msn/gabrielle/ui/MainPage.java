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

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.msn.gabrielle.ui.views.categorieslist.CategoriesList;
import com.msn.gabrielle.ui.views.reviewslist.ReviewsList;

/**
 * The main layout contains the header with the navigation buttons, and the
 * child views below that.
 */
@Route(value = "", layout = MainLayout.class)
@HtmlImport("frontend://styles/main-page-styles.html")
public class MainPage extends VerticalLayout
        implements RouterLayout{
	
    public MainPage() { 
    	setSizeFull();
    	
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
    	System.out.println("CALLING THE DB!");
    	//connectDB();
    	//mainLayout.setHorizontalComponentAlignment(Alignment.CENTER, buttonLayout);
    	SQLTablesManager sqlTM = new SQLTablesManager();
    	sqlTM.determineDBStates();
    	
    }
}
