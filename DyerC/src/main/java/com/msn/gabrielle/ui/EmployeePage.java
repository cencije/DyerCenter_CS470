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
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.msn.gabrielle.ui.views.Employee.EventsEmp;
import com.msn.gabrielle.ui.views.Employee.ProjectProposalEmp;
import com.msn.gabrielle.ui.views.Employee.ProjectsListEmp;
import com.msn.gabrielle.ui.views.Employee.SkillsEmp;

/**
 * The Employee layout contains the header with the navigation buttons for the project list for employee,
 * the project proposal, skillset editing page, and the events calendar for employee. This class uses 
 * routerlinks and was routed from the MainPage.java class
 * 
 * @author Dyer Center Senior Design
 */
@Route(value = "employeePage")
@HtmlImport("frontend://styles/shared-styles-ALUMNI.html")

public class EmployeePage extends Div
        implements RouterLayout{
	
	/**
	 * Constructor that sets up the header and navigation layout
	 */
    public EmployeePage() {
    	//header title
		H2 title = new H2("DYER CENTER");
		title.addClassName("main-lay__title");
		
		//app layout button for project list
		RouterLink projects = new RouterLink(null, ProjectsListEmp.class);
		projects.add(new Icon(VaadinIcon.FORM), new Text("Projects"));
		projects.addClassName("main-lay__nav-item");
		projects.setHighlightCondition(HighlightConditions.sameLocation());
		
		//app layout button for events calendar
		RouterLink eventsemp = new RouterLink(null, EventsEmp.class);
		eventsemp.add(new Icon(VaadinIcon.CALENDAR), new Text("Events"));
		eventsemp.addClassName("main-lay__nav-item");
		eventsemp.setHighlightCondition(HighlightConditions.sameLocation());
		
		//app layout button for skill set editing page
		RouterLink skillsEmp = new RouterLink(null, SkillsEmp.class);
		skillsEmp.add(new Icon(VaadinIcon.TASKS), new Text("SkillSet"));
		skillsEmp.addClassName("main-lay__nav-item");
		skillsEmp.setHighlightCondition(HighlightConditions.sameLocation());
		
		//app layout button for project proposals
		RouterLink projectpropemp = new RouterLink(null, ProjectProposalEmp.class);
		projectpropemp.add(new Icon(VaadinIcon.EDIT), new Text("Project Proposal"));
		projectpropemp.addClassName("main-lay__nav-item");
		projectpropemp.setHighlightCondition(HighlightConditions.sameLocation());
		
		//sets up the navigation bar with those app layout buttons
		Div navigation = new Div(projects, eventsemp, skillsEmp, projectpropemp); //, skillsEmp, );
		navigation.addClassName("main-lay__nav");
		
		//Adds the title and navigation to the full header
		Div header = new Div(title, navigation);
		header.addClassName("main-lay__header");
		
		add(header);

		addClassName("main-lay");
    }
   
}