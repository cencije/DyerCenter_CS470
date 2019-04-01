package com.msn.gabrielle.ui.views.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "profilestud", layout = StudentPage.class)
@PageTitle("Profile")
public class ProfileStud extends VerticalLayout{
	String profileName, profileEmail, profileMajor1, profileMajor2, profileMinor1, profileMinor2;
	String[] arrayMajor = { "N/A", "AB International Studies/BS Engineering Major", "Africana Studies", 
			"American Studies", "Anthropology and Sociology", "Art", "Asian Studies",
			"Biochemistry", "Biology",
			"Chemical Engineering", "Chemistry", "Civil Engineering", "Computer Science",
			"Economics", "English", "Electrical & Computer Engineering", "Engineering Studies",
			"English", "Environmental Science", "Environmental Studies",
			"Film and Media Studies", "Fo", "French",
			"Geology", "German", "Government and Law", "Government and Law: French",
			"Government and Law: German", "Government and Law: Spanish",
			"History",
			"International Affairs",
			"Mathematics", "Mathematics and Economics, A.B. Joint Major","Mechanical Engineering",
			"Military Science", "Music",
			"Neuroscience", 
			"Philosophy", "Physics", "Policy Studies", "Psychology", 
			"Religion and Politics", "Religious Studies", "Russian and East European Studies",
			"Spanish", 
			"Theater",
			"Women’s and Gender Studies",
			"Undecided" };
	String[] arrayMinor = { "N/A", "Africana Studies", "Aging Studies", 
			"Anthropology and Sociology", "Architectural Studies", "Art", "Asian Studies",
			"Biotechnology/Bioengineering",
			"Chemistry", "Chinese", "Computational Methods",
			"Classic Civilization", "Computer Science",
			"Economics", "English", "Environmental Science", "Environmental Studies",
			"Film and Media Studies", "French",
			"Geology", "German", "Global History", "Government and Law",  
			"Health, Life Sciences and Society", "History",
			"Italian Studies",
			"Jewish Studies",
			"Latin American and Caribbean Studies", "Literature in Translation",
			"Mathematics", "Mechanical Engineering", 
			"Medieval, Renaissance, and Early Modern Studies", "Music",
			"Philosophy", "Physics", "Psychology", 
			"Religious Studies", "Russian", "Russian and East European Studies",
			"Spanish", 
			"Theater",
			"Women’s and Gender Studies", "Writing"};
	public ProfileStud() {
		loadProfileValues();
		
		Label lblName = new Label("Name:");
		Label lblProfileName = new Label(profileName);
		Label lblEmail = new Label("Email:");
		Label lblProfileEmail = new Label(profileEmail);
		
		Label lblMajor1 = new Label("Major 1: " + profileMajor1);
		Label lblMajor2 = new Label("Major 2: " + profileMajor2);
		Label lblMinor1 = new Label("Minor 1: " + profileMinor1);
		Label lblMinor2 = new Label("Minor 2: " + profileMinor2);
		ComboBox<String> cbMajor1 = new ComboBox<>("Major 1");
		cbMajor1.setItems(Arrays.asList(arrayMajor));
		ComboBox<String> cbMajor2 = new ComboBox<>("Major 2 (If Applicable)");
		cbMajor2.setItems(Arrays.asList(arrayMajor));
		ComboBox<String> cbMinor1 = new ComboBox<>("Minor 1 (If Applicable)");
		cbMinor1.setItems(Arrays.asList(arrayMinor));
		ComboBox<String> cbMinor2 = new ComboBox<>("Minor 2 (If Applicable)");
		cbMinor2.setItems(Arrays.asList(arrayMinor));
		
		Button btnUpdateFields = new Button("Update Fields", event -> {
		    try {
		    	lblMajor1.setText("Major 1: " + cbMajor1.getValue());
		    	lblMajor2.setText("Major 2: " + cbMajor2.getValue());
		    	lblMinor1.setText("Minor 1: " + cbMinor1.getValue());
		    	lblMinor2.setText("Minor 2: " + cbMinor2.getValue());
		        // Update in the database
		    } catch (Exception e) { e.printStackTrace(); }
		});
		btnUpdateFields.setDisableOnClick(true);
		btnUpdateFields.setEnabled(false);
		cbMajor1.addValueChangeListener(event -> {
		    if (!event.getSource().isEmpty()) { btnUpdateFields.setEnabled(true); }
		});
		cbMajor2.addValueChangeListener(event -> {
		    if (!event.getSource().isEmpty()) { btnUpdateFields.setEnabled(true); }
		});
		cbMinor1.addValueChangeListener(event -> {
		    if (!event.getSource().isEmpty()) { btnUpdateFields.setEnabled(true); }
		});
		cbMinor2.addValueChangeListener(event -> {
		    if (!event.getSource().isEmpty()) { btnUpdateFields.setEnabled(true);  }
		});	

		HorizontalLayout hlName = new HorizontalLayout();
		hlName.add(lblName); hlName.add(lblProfileName);
		HorizontalLayout hlEmail = new HorizontalLayout();
		hlEmail.add(lblEmail); hlEmail.add(lblProfileEmail);
		HorizontalLayout hlMajors = new HorizontalLayout();
		hlMajors.add(lblMajor1); hlMajors.add(lblMajor2);
		HorizontalLayout hlMinors = new HorizontalLayout();
		hlMinors.add(lblMinor1); hlMinors.add(lblMinor2);
		HorizontalLayout hlCBMajors = new HorizontalLayout();
		hlCBMajors.add(cbMajor1); hlCBMajors.add(cbMajor2);
		HorizontalLayout hlCBMinors = new HorizontalLayout();
		hlCBMinors.add(cbMinor1); hlCBMinors.add(cbMinor2);
		
		VerticalLayout vlNonGridSide = new VerticalLayout();
		vlNonGridSide.add(hlName); vlNonGridSide.add(hlEmail);
		vlNonGridSide.add(hlMajors); vlNonGridSide.add(hlMinors);
		vlNonGridSide.add(hlCBMajors); vlNonGridSide.add(hlCBMinors);
		vlNonGridSide.add(btnUpdateFields);
		
		
		List<SkillStud> personList = new ArrayList<SkillStud>(); //personService.fetchAll();

		personList.add(new SkillStud("CompSci", "Coding"));
		personList.add(new SkillStud("Anthropology & Sociology", "Social Constructs"));
		Label hGrid = new Label("Skills List");
		Grid<SkillStud> firstGrid = new Grid<>();
		firstGrid.setItems(personList);
		firstGrid.setSelectionMode(SelectionMode.MULTI);

		TextField filterField = new TextField();
		filterField.setValueChangeMode(ValueChangeMode.EAGER);
		filterField.addValueChangeListener(event -> {
		    Optional<SkillStud> foundPerson = personList.stream()
		            .filter(person -> person.getMajor().toLowerCase()
		                    .startsWith(event.getValue().toLowerCase()))
		            .findFirst();
		    
		    firstGrid.getSelectionModel().deselectAll();
		    Set<SkillStud> foundpersons = personList.stream()
		            .filter(person -> person.getMajor().toLowerCase()
		                    .startsWith(event.getValue().toLowerCase()))
		            .collect(Collectors.toSet());
		    firstGrid.asMultiSelect().setValue(foundpersons);
		});

		firstGrid.addColumn(SkillStud::getMajor).setHeader("Major");
		firstGrid.addColumn(SkillStud::getName).setHeader("Skill Name");

		Button deselectBtn = new Button("Deselect all");
		deselectBtn.addClickListener(
		        event -> firstGrid.asMultiSelect().deselectAll());
		Button selectAllBtn = new Button("Select all");
		selectAllBtn.addClickListener(
		        event -> ((GridMultiSelectionModel<SkillStud>) firstGrid
		                .getSelectionModel()).selectAll());
		HorizontalLayout hlGridBtns = new HorizontalLayout();
		hlGridBtns.add(deselectBtn); hlGridBtns.add(selectAllBtn);
		VerticalLayout vlGrid = new VerticalLayout();
		vlGrid.add(hGrid); vlGrid.add(firstGrid);
		vlGrid.add(hlGridBtns);
		HorizontalLayout hlFinalLayout = new HorizontalLayout();
		hlFinalLayout.add(vlNonGridSide); hlFinalLayout.add(vlGrid);
		hlFinalLayout.setWidth("100%");
		add(hlFinalLayout);
		
	}
	public void loadProfileValues() {
		// Select * WHERE EMAILADDRESS = ' ';
		profileName = "N/A";
		profileEmail = "N/A";
		profileName = "John Goodway";
		profileEmail = "goodwayj@lafayette.edu";
		profileMajor1 = "N/A";
		profileMajor2 = "N/A";
		profileMinor1 = "N/A";
		profileMinor2 = "N/A";
		
	}
}