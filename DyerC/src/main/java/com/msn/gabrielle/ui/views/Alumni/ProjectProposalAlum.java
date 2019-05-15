package com.msn.gabrielle.ui.views.Alumni;

import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.msn.gabrielle.backend.EmailSender;
import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.ui.*;
import com.msn.gabrielle.ui.views.Student.SkillStud;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "projectpropalum", layout = AlumniPage.class)
@HtmlImport("frontend://styles/shared-styles-ALUMNI.html")
@PageTitle("Project Proposal Alum")
public class ProjectProposalAlum extends VerticalLayout {
	private TextField pTField; //the text field for the project title
	private DatePicker datePickerFirst; // the date field for start date
	private DatePicker datePickerSecond; // the date field for end date
	private TextField locationTF; //the location text field
	private List<SkillStud> skillList; //the list of skills 
	private TextArea area; //the area for writing the project description
	private ComboBox<String> comboBox; // the combo box for the pay for a project 
	private String pay; //the string from the comboBox
	private TextField name; //the name text field
	private TextField searchBar; //the search bar text field
	private Grid<SkillStud> firstGrid; //the grid to display the skill set
	private List<Projects> projectList; //list of project objects
	SQLProjectAlum sqlPA = new SQLProjectAlum(); //the sql object to access the DB data
	
	/**
	 * constructor that sets up the layout to provide the project proposal fields
	 */
	public ProjectProposalAlum() {
		addClassName("main-lay");
		setWidthFull();
		setHeightFull();
		addClassName("main-layout-emp");
		projectList = sqlPA.loadProjects();
		HorizontalLayout hL = new HorizontalLayout();
		hL.add(projectTitle(), duration());
		add(hL);
		add(location());
		add(projectDescription());
		HorizontalLayout hL1 = new HorizontalLayout();
		hL1.add(salary(), proposerName());
		add(hL1);
		add(skills());
		updateSkillList();
		HorizontalLayout hL2 = new HorizontalLayout();
    	Button saveButton = new Button("Submit", event -> {
    		if(projectError() == true && projectStringError() == true) {
	    		Set<SkillStud> setSkills = firstGrid.getSelectedItems();
	    		List<SkillStud> listSkills = new ArrayList<SkillStud>();
	    		for (SkillStud skill : setSkills) { listSkills.add(skill); }
	    		Projects newProj = new Projects(pTField.getValue());
	    		newProj.setStartDate(datePickerFirst.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
	    		newProj.setEndDate(datePickerSecond.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
	    		newProj.setLocation(locationTF.getValue());
	    		newProj.setDescription(area.getValue());
	    		newProj.setPay(pay);
	    		newProj.setProposedBy(name.getValue());
	    		newProj.setDatePosted(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
	    		newProj.setSkillsList(listSkills);
	    		//projectList.add(newProj);
	    		
	    		EmailSender es = new EmailSender();
	    		String nameToSend = name.getValue();
	    		String titleToSend = pTField.getValue();
	    		String descToSend = area.getValue();
	    		String timeStart = datePickerFirst.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    		String timeEnd = datePickerSecond.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    		String timeframe = timeStart + " to " + timeEnd;
	    		String locToSend = locationTF.getValue();
	    		String paidToSend = comboBox.getValue();
	    		
	    		Set<SkillStud> skillSetGrid = firstGrid.getSelectedItems();
        		ArrayList<String> skillsForEmail = new ArrayList<String>();
        		for(Iterator<SkillStud> it = skillSetGrid.iterator(); it.hasNext();) {
        			String s = it.next().printSkill();
        			skillsForEmail.add(s);
        		}
        		String formattedSkills = " " + skillsForEmail.toString().replace("[", "").replace("]", "").replace(",", "").trim();
        				
	    		es.sendSpecificEmail("New Project Proposal!", titleToSend, descToSend, 
	    				timeframe, locToSend, paidToSend, 2, nameToSend, formattedSkills);
	    		
	    		Dialog good = new Dialog();
				Label goodLabel = new Label("Successfully submitted! A Dyer Center Employee will review it shortly");
				good.add(goodLabel);
				good.setCloseOnOutsideClick(true);
				good.open();
				
				clearAll();
	    		return;
	    	}
    		if (projectError() == false) {
	    		Dialog error = new Dialog();
				Label errorBlank = new Label("Error: please enter all the required fields");
				error.add(errorBlank);
				error.setCloseOnOutsideClick(true);
				error.open();
    		}
    		if (projectStringError() == false) {
	    		Dialog errorString = new Dialog();
				Label errorBlank = new Label("Error: " + "' " + "\" "+ "; are not allowed in the text fields");
				errorString.add(errorBlank);
				errorString.setCloseOnOutsideClick(true);
				errorString.open();
    		}
    	});
    	saveButton.addClassName("main-layout-emp__event");
    	hL2.add(saveButton);
    	add(hL2);
	}
	
	/**
	 * Checks if any of the fields are null in the fields 
	 * @return true if the fields are all filled, or false if fields are empty
	 */
	public boolean projectError() {
	    if(pTField.isEmpty()) {   return false; }
	    if(datePickerFirst.isEmpty()) {   return false; }
	    if(datePickerSecond.isEmpty()) {  return false; }
	    if(locationTF.isEmpty()) {  return false; }
	    if(area.isEmpty()) {  return false; }
	    if(name.isEmpty()) {  return false; }
	    if(comboBox.isEmpty()) {  return false; }
	    if(firstGrid.getSelectedItems().size() == 0) {  return false; }
	    return true;
	}
	
	/**
	 * Checks if the fields contain ' " or ; since the DB does not allow those characters
	 * @return true if the fields do not contain those, or false if they do
	 */
	public boolean projectStringError() {
	    if(checkContainsInvalidSymbols(pTField.getValue())) {   return false; }
	    if(checkContainsInvalidSymbols(locationTF.getValue())) {  return false; }
	    if(checkContainsInvalidSymbols(area.getValue())) {  return false; }
	    if(checkContainsInvalidSymbols(name.getValue())) {  return false; }
	    return true;
	}
	
	/**
	 * Checks that a passed String does not contain any of the special characters that can't be
	 * stored in the database. It's called from openAddEvent().
	 * @param s A variable of type String.
	 * @return true If the String contains any of the invalid symbols.
	 */
	private boolean checkContainsInvalidSymbols(String s) {
		if(s.contains(";") || s.contains("'") || s.contains("\"")) {
			return true;
		}
		return false;
	}
	 
    /**
     * Layout for project title and text field for project dialog
     * @return vertical layout
     */
    public VerticalLayout projectTitle() {
    	VerticalLayout pT = new VerticalLayout();
    	pTField = new TextField("Title: ");
    	pT.add(pTField);
    	pTField.setWidthFull();
    	return pT;
    }
    
    /**
     * Layout for duration of project dialog
     * @return vertical layout
     */
    public VerticalLayout duration() {
    	VerticalLayout dur = new VerticalLayout();
    	HorizontalLayout datePicker = new HorizontalLayout();
    	datePickerFirst = new DatePicker("Start Date ");
    	datePickerSecond = new DatePicker("End Date ");
    	datePicker.add(datePickerFirst, datePickerSecond);
    	dur.add(datePicker);
    	return dur;
    }
    
    /**
     * Layout for location of project dialog
     * @return vertical layout
     */
    public VerticalLayout location() {
    	VerticalLayout loc = new VerticalLayout();
    	locationTF = new TextField("Location: ");
    	locationTF.setWidthFull();
    	loc.add(locationTF);
    	return loc;
    }
    
    /**
     * Layout for project description for project dialog
     * @return vertical layout
     */
    public VerticalLayout projectDescription() {
    	VerticalLayout descrip = new VerticalLayout();
    	area = new TextArea("Description: ");
    	area.setWidthFull();
    	descrip.add(area);
    	return descrip;
    }
    
    /**
     * Layout for salary combo box for project dialog
     * @return vertical layout
     */
    public VerticalLayout salary() {
    	VerticalLayout sL = new VerticalLayout();
    	comboBox = new ComboBox<String>("Pay: ");
    	comboBox.setItems("Paid", "Unpaid", "Unknown");
    	comboBox.addValueChangeListener(event -> {
    	    pay = comboBox.getValue();
    	});
    	sL.add(comboBox);
    	return sL;
    }
    
    /**
     * Layout for proposer of the projects name for project dialog
     * @return vertical layout
     */
    public VerticalLayout proposerName() {
    	VerticalLayout vL = new VerticalLayout();
    	name = new TextField("Name: ");
    	name.setWidthFull();
    	vL.add(name);
    	return vL;
    }
    
    /**
     * Layout for skills for project layout dialog
     * @return vertical layout
     */
    public VerticalLayout skills() {
    	VerticalLayout vL = new VerticalLayout();
    	skillList = new ArrayList<SkillStud>(); 
    	skillList = sqlPA.loadAllSkills();
		searchBar = new TextField();
		searchBar.setWidthFull();
		searchBar.setPlaceholder("Search by skill name");
		searchBar.addValueChangeListener(e -> updateSkillList());
		firstGrid = new Grid<SkillStud>();
		firstGrid.setItems(skillList);
		firstGrid.setSelectionMode(SelectionMode.MULTI);
		firstGrid.addColumn(SkillStud::getCategory).setHeader("Category");
		firstGrid.addColumn(SkillStud::getName).setHeader("Skill Name");
		vL.add(searchBar, firstGrid);
		vL.setWidthFull();
		return vL;
    }
    
    /**
     * Gets the list of skills that match the search value that was entered int the search bar for the
     * skill set. The value is the skill set name
     * @param skills is the whole list of skills
     * @param value is the string entered into the search bar
     * @return the list of skills that match the search results
     */
    private List<SkillStud> getSearchSkills (List<SkillStud> skills, String value){
    	List<SkillStud> skillsEdit = skills;

    	if (value.isEmpty()) {
    		return skillsEdit;
    	}
    	
    	List<SkillStud> listToDisplay = new ArrayList<SkillStud>();
    	for (int i = 0; i < skillsEdit.size(); i++) {
    		if (skillsEdit.get(i).getName().toLowerCase().contains(value.toLowerCase())) {
    			listToDisplay.add(skillsEdit.get(i));
    		}
    	}
		return listToDisplay;
    }
    
    /**
     * Updates the skills list displayed based on the search bar results
     */
    public void updateSkillList() {
    	List<SkillStud> listToDisplay = getSearchSkills(skillList, searchBar.getValue());
        firstGrid.setItems(listToDisplay);
    }
    
    /**
     * clear all the text fields and all fields in the layout
     */
    public void clearAll() {
    	pTField.clear();
    	datePickerFirst.clear();
    	datePickerSecond.clear();
    	locationTF.clear();
    	area.clear();
    	name.clear();
    	comboBox.clear();
    	searchBar.clear();
    	firstGrid.deselectAll();
    }
}