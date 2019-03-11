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
import com.msn.gabrielle.ui.views.categorieslist.CategoriesList;
import com.msn.gabrielle.ui.views.reviewslist.ReviewsList;

/**
 * The main layout contains the header with the navigation buttons, and the
 * child views below that.
 */
@Route(value = "studentPage", layout = MainLayout.class)
@HtmlImport("frontend://styles/shared-styles.html")

public class StudentPage extends Div
        implements RouterLayout{
		
    public StudentPage() {
		H2 title = new H2("Dyer Center Student");
		title.addClassName("main-layout__title");

		RouterLink reviews = new RouterLink(null, ReviewsList.class);
		reviews.add(new Icon(VaadinIcon.LIST), new Text("Reviews"));
		reviews.addClassName("main-layout__nav-item");
		// Only show as active for the exact URL, but not for sub paths
		reviews.setHighlightCondition(HighlightConditions.sameLocation());

		RouterLink categories = new RouterLink(null, CategoriesList.class);
		categories.add(new Icon(VaadinIcon.ARCHIVES), new Text("Categories"));
		categories.addClassName("main-layout__nav-item");
		categories.setHighlightCondition(HighlightConditions.sameLocation());

		Div navigation = new Div(reviews, categories);
		navigation.addClassName("main-layout__nav");

		Div header = new Div(title, navigation);
		header.addClassName("main-layout__header");
		add(header);

		addClassName("main-layout");
    }
   
}

