package com.msn.gabrielle.ui.views.Alumni;

import com.msn.gabrielle.ui.AlumniPage;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "projectsalum", layout = AlumniPage.class)
@PageTitle("Projects")
public class ProjectListAlum extends VerticalLayout{
	public ProjectListAlum() {
		
	}
}