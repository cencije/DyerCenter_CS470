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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.msn.gabrielle.ui.views.Employee.EventsEmp;
import com.msn.gabrielle.ui.views.Employee.ProfileEmp;
import com.msn.gabrielle.ui.views.Employee.ProjectsListEmp;
import com.msn.gabrielle.ui.views.Employee.SkillsEmp;
import com.msn.gabrielle.ui.views.categorieslist.CategoriesList;
import com.msn.gabrielle.ui.views.reviewslist.ReviewsList;

/**
 * The main layout contains the header with the navigation buttons, and the
 * child views below that.
 */
@Route(value = "employeePage")
@HtmlImport("frontend://styles/shared-styles.html")

public class EmployeePage extends Div
        implements RouterLayout{
		
    public EmployeePage() {
		H2 title = new H2("DYER CENTER");
		title.addClassName("main-layout__title");

		RouterLink projects = new RouterLink(null, ProjectsListEmp.class);
		projects.add(new Icon(VaadinIcon.FORM), new Text("Projects"));
		projects.addClassName("main-layout__nav-item");
		// Only show as active for the exact URL, but not for sub paths
		projects.setHighlightCondition(HighlightConditions.sameLocation());

		RouterLink eventsemp = new RouterLink(null, EventsEmp.class);
		eventsemp.add(new Icon(VaadinIcon.CALENDAR), new Text("Events"));
		eventsemp.addClassName("main-layout__nav-item");
		eventsemp.setHighlightCondition(HighlightConditions.sameLocation());
		
		RouterLink skillsEmp = new RouterLink(null, SkillsEmp.class);
		skillsEmp.add(new Icon(VaadinIcon.TASKS), new Text("SkillSet"));
		skillsEmp.addClassName("main-layout__nav-item");
		skillsEmp.setHighlightCondition(HighlightConditions.sameLocation());
		
		RouterLink profileemp = new RouterLink(null, ProfileEmp.class);
		profileemp.add(new Icon(VaadinIcon.USER), new Text("Profile"));
		profileemp.addClassName("main-layout__nav-item");
		profileemp.setHighlightCondition(HighlightConditions.sameLocation());

		Div navigation = new Div(projects, eventsemp, skillsEmp, profileemp); //, skillsEmp, );
		navigation.addClassName("main-layout__nav");
		
		Div header = new Div(title, navigation);
		header.addClassName("main-layout__header");
		
		add(header);

		addClassName("main-layout");
    }
   
}