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

import com.vaadin.flow.component.Text;  
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.msn.gabrielle.ui.views.Student.EventsStud;
import com.msn.gabrielle.ui.views.Student.ProfileStud;
import com.msn.gabrielle.ui.views.Student.ProjectListStud;
import com.msn.gabrielle.ui.views.categorieslist.CategoriesList;
import com.msn.gabrielle.ui.views.reviewslist.ReviewsList;
import com.vaadin.flow.component.dialog.Dialog;

/**
 * The Student layout contains the header with the navigation buttons for the project list for student,
 * the events calendar for students, and the profile of the student. This class uses routerlinks and was routed from the 
 * MainPage.java class
 * 
 * @author Dyer Center Senior Design
 */
@Route(value = "studentPage")
@HtmlImport("frontend://styles/shared-styles-ALUMNI.html")
public class StudentPage extends Div
        implements RouterLayout{
		
	/**
	 * Constructor that sets up the header and navigation layout
	 */
    public StudentPage() {
		addClassName("main-lay");
		Dialog initial = new Dialog();
		Label welcome = new Label("Welcome, John! Click one of the items from the control panel above to navigate to a new page.");
		initial.add(welcome);
		initial.open();
		
		//title of the header bar
		H2 title = new H2("DYER CENTER");
		title.addClassName("main-lay__title");
		
		//app layout button for event calendar
		RouterLink events = new RouterLink(null, EventsStud.class);
		events.add(new Icon(VaadinIcon.CALENDAR), new Text("Events"));
		events.addClassName("main-lay__nav-item");
		events.setHighlightCondition(HighlightConditions.sameLocation());
		
		//app layout button for project list
		RouterLink projects = new RouterLink(null, ProjectListStud.class);
		projects.add(new Icon(VaadinIcon.FORM), new Text("Projects"));
		projects.addClassName("main-lay__nav-item");
		projects.setHighlightCondition(HighlightConditions.sameLocation());
		
		//app layout button for profile
		RouterLink profile = new RouterLink(null, ProfileStud.class);
		profile.add(new Icon(VaadinIcon.USER), new Text("Profile"));
		profile.addClassName("main-lay__nav-item");
		profile.setHighlightCondition(HighlightConditions.sameLocation());
		
		//creates the navigation bar
		Div navigation = new Div(projects, events, profile);
		navigation.addClassName("main-lay__nav");
		
		//creates the whole header bar with title and navigation
		Div header = new Div(title, navigation);
		header.addClassName("main-lay__header");
		add(header);
    }
   
}

