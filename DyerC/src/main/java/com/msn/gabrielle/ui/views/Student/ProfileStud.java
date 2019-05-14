package com.msn.gabrielle.ui.views.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "profilestud", layout = StudentPage.class)
@PageTitle("Profile")
public class ProfileStud extends VerticalLayout{
	String profileName, profileEmail, profileMajor1, profileMajor2, profileMinor1, profileMinor2;
	
	SQLProfileStud sqlPS = new SQLProfileStud();
	
	Notification nDuplicateValues;
	
	Grid<SkillStud> gridChosenSkills;
	String[] arrayMajor = { "AB International Studies/BS Engineering Major", "Africana Studies", 
			"American Studies", "Anthropology and Sociology", "Art", "Asian Studies",
			"Biochemistry", "Biology",
			"Chemical Engineering", "Chemistry", "Civil Engineering", "Computer Science",
			"Economics", "English", "Electrical & Computer Engineering", "Engineering Studies",
			"English", "Environmental Science", "Environmental Studies",
			"Film and Media Studies", "French",
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
	String[] arrayMajor2 = { "N/A", "AB International Studies/BS Engineering Major", "Africana Studies", 
			"American Studies", "Anthropology and Sociology", "Art", "Asian Studies",
			"Biochemistry", "Biology",
			"Chemical Engineering", "Chemistry", "Civil Engineering", "Computer Science",
			"Economics", "English", "Electrical & Computer Engineering", "Engineering Studies",
			"English", "Environmental Science", "Environmental Studies",
			"Film and Media Studies", "French",
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
	
	/**
	 * Constructor for ProfileStud, loads the UI and skills values from the database.
	 * Also loads in profile values for the student logged in.
	 */
	public ProfileStud() {
		gridChosenSkills = new Grid<>();
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
		ComboBox<String> cbMajor2 = new ComboBox<>("Major 2");
		cbMajor2.setItems(arrayMajor2);
		ComboBox<String> cbMinor1 = new ComboBox<>("Minor 1");
		cbMinor1.setItems(Arrays.asList(arrayMinor));
		ComboBox<String> cbMinor2 = new ComboBox<>("Minor 2");
		cbMinor2.setItems(Arrays.asList(arrayMinor));
		
		Button btnUpdateFields = new Button("Update Fields", event -> {
		    try {
		    	String Maj1 = cbMajor1.getValue();
		    	String Maj2 = cbMajor2.getValue();
		    	String Min1 = cbMinor1.getValue();
		    	String Min2 = cbMinor2.getValue();
		    	if (Maj1 == null || Maj2 == null || Min1 == null || Min2 == null) {
		    		Label lblNotif = new Label("Please enter a major & minor or select 'Undecided' & 'N/A' respectively!");
		    		nDuplicateValues = new Notification(lblNotif);
					nDuplicateValues.setDuration(3000);
					nDuplicateValues.setPosition(Position.MIDDLE);
					nDuplicateValues.open();
		    	}
		    	else if ((Maj1.equals("Undecided") && !Maj2.equals("N/A")) ||
		    			 (Min1.equals("N/A") && !Min2.equals("N/A"))) {
		    		Label lblNotif = new Label("Major/Minor 2 must be 'Undecided' or 'N/A' if Major/Minor 1 is 'Undecided' or 'N/A'");
		    		nDuplicateValues = new Notification(lblNotif);
					nDuplicateValues.setDuration(3000);
					nDuplicateValues.setPosition(Position.MIDDLE);
					nDuplicateValues.open();
		    	}
		    		
		    	else if (!(Maj1.equals(Maj2) && !Maj1.equals("Undecided")) && !Maj1.equals(Min1) && !Maj1.equals(Min2) &&
		    			 !(Maj2.equals(Min1) && !Maj2.equals("N/A")) && !(Maj2.equals(Min2) && 
		    			   !Maj2.equals("N/A")) && !(Min1.equals(Min2) && !Min1.equals("N/A") )) {
		    		sqlPS.updateMajorsMinors(profileEmail, Maj1, Maj2, Min1, Min2);
			    	lblMajor1.setText("Major 1: " + Maj1);
			    	lblMajor2.setText("Major 2: " + Maj2);
			    	lblMinor1.setText("Minor 1: " + Min1);
			    	lblMinor2.setText("Minor 2: " + Min2);
			    	
		    	}
		    	else {
		    		Label lblNotif = new Label("Majors / Minors cannot be duplicates!");
		    		nDuplicateValues = new Notification(lblNotif);
					nDuplicateValues.setDuration(3000);
					nDuplicateValues.setPosition(Position.MIDDLE);
					nDuplicateValues.open();
		    	}
		        // Update in the database
		    } catch (Exception e) { e.printStackTrace(); }
		});
		btnUpdateFields.setDisableOnClick(true);
		btnUpdateFields.setEnabled(false);
		btnUpdateFields.addClassName("view-toolbar__profile-click");
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

		
		Label lblChosenSkills = new Label("Chosen Skills List");
		gridChosenSkills.addColumn(SkillStud::getCategory).setHeader("Category");
		gridChosenSkills.addColumn(SkillStud::getName).setHeader("Skill Name");
		
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
		vlNonGridSide.add(lblChosenSkills);
		vlNonGridSide.add(gridChosenSkills);
	
		List<SkillStud> listSkills = new ArrayList<SkillStud>(); 
		listSkills = sqlPS.loadAllSkills();
		
		Label lblSkillsGrid = new Label("Available Skills List");
		Grid<SkillStud> firstGrid = new Grid<>();
		firstGrid.setItems(listSkills);
		firstGrid.setSelectionMode(SelectionMode.MULTI);

		firstGrid.addColumn(SkillStud::getCategory).setHeader("Category");
		firstGrid.addColumn(SkillStud::getName).setHeader("Skill Name");

		Button deselectBtn = new Button("Deselect all");
		deselectBtn.addClickListener(
		        event -> firstGrid.asMultiSelect().deselectAll());
		Button selectAllBtn = new Button("Select all");
		selectAllBtn.addClickListener(
		        event -> ((GridMultiSelectionModel<SkillStud>) firstGrid
		                .getSelectionModel()).selectAll());
		Button btnUpdateSkills = new Button("Update Skills");
		btnUpdateSkills.addClickListener(
				event -> {
					ArrayList<SkillStud> listSelectedSkills = new ArrayList<SkillStud>();
					Set<SkillStud> skillSet = new HashSet<SkillStud>();
					skillSet = firstGrid.getSelectedItems();
					for (SkillStud ss : skillSet) { 
						listSelectedSkills.add(ss); 
						System.out.println(ss.skillCategory + " " + ss.skillName);
					}
					sqlPS.addSkillsToProfile(profileEmail, listSelectedSkills);
					updateProfileSkills();
				}
		);
		HorizontalLayout hlGridBtns = new HorizontalLayout();
		hlGridBtns.add(deselectBtn); hlGridBtns.add(selectAllBtn); hlGridBtns.add(btnUpdateSkills); 
		VerticalLayout vlGrid = new VerticalLayout();
		vlGrid.add(lblSkillsGrid); vlGrid.add(firstGrid); vlGrid.add(hlGridBtns);
		HorizontalLayout hlFinalLayout = new HorizontalLayout();
		hlFinalLayout.add(vlNonGridSide); hlFinalLayout.add(vlGrid);
		hlFinalLayout.setWidth("100%");
		add(hlFinalLayout);
		
	}
	
	/**
	 * Loads values pertaining to the user profile within the database.
	 */
	public void loadProfileValues() {
		// Dummy Value used for email right now
		// Should be loaded from the Databases upon login
		profileEmail = "goodwayj@lafayette.edu";
		updateProfileSkills();
		ArrayList<String> listInfo = sqlPS.getProfileInformation();
		profileName = listInfo.get(0);
		profileEmail = listInfo.get(1);
		profileMajor1 = listInfo.get(4);
		profileMajor2 = listInfo.get(5);
		profileMinor1 = listInfo.get(6);
		profileMinor2 = listInfo.get(7);
		
	}
	
	/**
	 * Updates the skills the user has based upon what was selected and updated in the grid on the right.
	 */
	public void updateProfileSkills() {
		ArrayList<SkillStud> profileSkillsList = sqlPS.getProfileValues(profileEmail);
		gridChosenSkills.setItems(profileSkillsList);
	}
}