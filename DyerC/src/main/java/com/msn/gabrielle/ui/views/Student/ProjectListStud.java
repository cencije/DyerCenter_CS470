package com.msn.gabrielle.ui.views.Student;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "projectsstud", layout = StudentPage.class)
@PageTitle("Projects")
public class ProjectListStud extends VerticalLayout{
	public ProjectListStud() {
		
	}
}
