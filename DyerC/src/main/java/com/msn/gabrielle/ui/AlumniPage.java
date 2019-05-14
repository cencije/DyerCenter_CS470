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
import com.msn.gabrielle.ui.views.Alumni.ProfileAlum;
import com.msn.gabrielle.ui.views.Alumni.ProjectProposalAlum;
import com.msn.gabrielle.ui.views.Alumni.ProjectListAlum;
import com.msn.gabrielle.ui.views.categorieslist.CategoriesList;
import com.msn.gabrielle.ui.views.reviewslist.ReviewsList;

/**
 * The main layout contains the header with the navigation buttons, and the
 * child views below that.
 */
@Route(value = "alumniPage")
@HtmlImport("frontend://styles/shared-styles-ALUMNI.html")

public class AlumniPage extends Div
        implements RouterLayout{
		
    public AlumniPage() {
		H2 title = new H2("DYER CENTER");
		title.addClassName("main-lay__title");

		RouterLink projects = new RouterLink(null, ProjectListAlum.class);
		projects.add(new Icon(VaadinIcon.FORM), new Text("Projects"));
		projects.addClassName("main-lay__nav-item");
		// Only show as active for the exact URL, but not for sub paths
		projects.setHighlightCondition(HighlightConditions.sameLocation());
		
		RouterLink projectproposal = new RouterLink(null, ProjectProposalAlum.class);
		projectproposal.add(new Icon(VaadinIcon.EDIT), new Text("Project Proposal"));
		projectproposal.addClassName("main-lay__nav-item");
		// Only show as active for the exact URL, but not for sub paths
		projectproposal.setHighlightCondition(HighlightConditions.sameLocation());

		RouterLink profile = new RouterLink(null, ProfileAlum.class);
		profile.add(new Icon(VaadinIcon.USER), new Text("Profile"));
		profile.addClassName("main-lay__nav-item");
		profile.setHighlightCondition(HighlightConditions.sameLocation());

		Div navigation = new Div(projects, projectproposal, profile);
		navigation.addClassName("main-lay__nav");

		Div header = new Div(title, navigation);
		header.addClassName("main-lay__header");
		add(header);

		addClassName("main-lay");
    }
   
}

