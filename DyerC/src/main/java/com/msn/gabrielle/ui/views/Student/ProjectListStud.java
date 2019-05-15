package com.msn.gabrielle.ui.views.Student;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.ui.*;
import com.msn.gabrielle.ui.encoders.LocalDateToStringEncoder;
import com.msn.gabrielle.ui.encoders.LongToStringEncoder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.ModelItem;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.Encode;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.msn.gabrielle.ui.views.Student.ProjectListStud.ProjectsModel;
import com.msn.gabrielle.ui.views.Student.SkillStud;
import com.msn.gabrielle.backend.EmailSender;

@Route(value = "projectsstud", layout = StudentPage.class)
@PageTitle("Projects")
@HtmlImport("frontend://styles/shared-project-style.html")
@Tag("reviews-list")
public class ProjectListStud extends PolymerTemplate<ProjectsModel>{
	
	SQLProjectStud sqlPStud = new SQLProjectStud();
	private TextField searchField = new TextField("",
            "Search projects");
    private List<Projects> projectList;
    private List<SkillStud> skillsList;
    private String nameStr;
    private TextField pTField;
    private DatePicker datePickerFirst;
    private DatePicker datePickerSecond;
    private TextField locationTF;
    private TextArea area;
    private TextField name;   
    private ComboBox<String> comboBox;
    private TextField searchBar;
    private Grid<SkillStud> firstGrid;
    
    public interface ProjectsModel extends TemplateModel{
    	@Encode(value = LongToStringEncoder.class, path = "id")
        @Encode(value = LocalDateToStringEncoder.class, path = "date")
        void setReviews(List<Projects> projectlis);
    }
    
    @Id("search")
    private TextField search;
    @Id("newReview")
    private Button addReview;
    @Id("header")
    private H2 header;
    @Id("radio")
    private RadioButtonGroup<String> combo;
    
    public ProjectListStud() {
      search.setPlaceholder("Search by title");
      search.addValueChangeListener(e -> updateList());
      search.setValueChangeMode(ValueChangeMode.EAGER);
      search.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        //initView();
      nameStr = "";
      projectList = sqlPStud.loadProjects();
      getModel().setReviews(projectList);
      getElement().setProperty("reviewButtonText", "New project");
      getElement().setProperty("editButtonText", "View");
      addReview.addClickListener(e -> {
    	  getUI().ifPresent(ui -> ui.add(dialog()));
    	  updateSkillList();
      });
      updateList();
    }
    
    private Dialog viewDialog(Projects currentProj) {
    	Dialog viewDialog = new Dialog();
    	viewDialog.setCloseOnEsc(false);
    	viewDialog.setCloseOnOutsideClick(false);
    	VerticalLayout projectForum = new VerticalLayout();
    	
    	HorizontalLayout titleDuration = new HorizontalLayout();
    	Label projectTitleLabel = new Label("Project title: " + currentProj.getProjectTitle());
    	Label durationLabel = new Label("Duration: " + currentProj.getStartDate() + " to " +
    												   currentProj.getEndDate());
    	titleDuration.add(projectTitleLabel, durationLabel);
    	
    	projectForum.add(titleDuration, new Label("Location: " + currentProj.getLocation()),
    									new Label ("Description: " + currentProj.getDescription()));
    	
    	HorizontalLayout nameUnPaid = new HorizontalLayout();
    	Label payLabel = new Label("Pay: " + currentProj.getPay());
    	nameUnPaid.add(payLabel);
    	Label nameLabel = new Label("Proposer name: " + currentProj.getProposedBy());
    	nameUnPaid.add(nameLabel);
    	
    	projectForum.add(nameUnPaid);
    	viewDialog.add(projectForum);
    	Button closeButton = new Button("Cancel", event -> {
    		viewDialog.close();
    	});
    	List<SkillStud> personList = currentProj.getSkillList();
    	Grid<SkillStud> grid = new Grid<>();
    	grid.setItems(personList);
    	grid.addColumn(SkillStud::getCategory).setHeader("Category");
    	grid.addColumn(SkillStud::getName).setHeader("Skill Name");
    	grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
    	        GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
    	viewDialog.add(grid);
    	viewDialog.setCloseOnOutsideClick(true);
    	viewDialog.add(closeButton);
    	return viewDialog;
    }
    
    @EventHandler
    private void edit(@ModelItem Projects project) {
    	viewDialog(project).open();
    }
    
    @EventHandler
    private void view_click() {
    	projectList = sqlPStud.loadProjects();
    	getModel().setReviews(projectList);
    	updateList();
    }
    
    @EventHandler
    private void match_click() {
    	projectList = sqlPStud.loadMatchingProjectsCatSkill("goodwayj@lafayette.edu");
    	getModel().setReviews(projectList);
    	updateList();
    }
    
    private Dialog dialog() {
        Dialog dialog = new Dialog();

    	dialog.setCloseOnEsc(false);
    	dialog.setCloseOnOutsideClick(false);
    	
    	VerticalLayout projectForum = new VerticalLayout();
    	HorizontalLayout titleDuration = new HorizontalLayout();
    	titleDuration.add(projectTitle(), duration());
    	HorizontalLayout nameUnPaid = new HorizontalLayout();
    	nameUnPaid.add(salary(), proposerName());
    	projectForum.add(titleDuration, location(), projectDescription(), nameUnPaid);
    	projectForum.add(skills());
    	
    	//********************************************************
    	HorizontalLayout buttons = new HorizontalLayout();
    	Button saveButton = new Button("Submit", event -> {
    		if( projectError() == true && projectStringError() == true) {
    			Projects newProj = new Projects(pTField.getValue());
    			// SQL ADD HERE to PROPOSED TABLE
    			// Reload Projects List
    			projectList.add(newProj);
    			
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
        				timeframe, locToSend, paidToSend, 1, nameToSend, formattedSkills);
        		
    			dialog.close();
    			clearAll();
    			updateList();
    		}
    		if (projectError() == false) {
        		HorizontalLayout error = new HorizontalLayout();
        		Label errorBlank = new Label("Error: please enter all the required fields");
        		error.add(errorBlank);
        		dialog.add(error);
    		}
    		if (projectStringError() == false) {
    			HorizontalLayout errorStr = new HorizontalLayout();
				Label errorBlank = new Label("Error: " + "' " + "\" "+ "; are not allowed in the text fields");
				errorStr.add(errorBlank);
        		dialog.add(errorStr);
    		}
    	});
    	Button cancelButton = new Button("Cancel", event -> {
    		clearAll();
    	    dialog.close();
    	});
    	buttons.add(saveButton, cancelButton);
    	saveButton.addClassName("view-toolbar__event-click");
    	cancelButton.addClassName("view-toolbar__event-click");
    	dialog.add(projectForum, buttons);
    	dialog.open();
    	return dialog;
    }
    
    public boolean projectError() {
    	//if(projectList.isEmpty()) { return false; }
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
    	comboBox = new ComboBox<>("Pay: ");
    	comboBox.setItems("Paid", "Unpaid", "Unknown");
    	sL.add(comboBox);
    	return sL;
    }
    
    /**
     * Layout for proposer of the projects name for project dialog
     * @return vertical Remove Projects?layout
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
    	skillsList = new ArrayList<SkillStud>(); 
		skillsList = sqlPStud.loadAllSkills();
		searchBar = new TextField();
		searchBar.setWidthFull();
		searchBar.setPlaceholder("Search by skill name");
		searchBar.addValueChangeListener(e -> updateSkillList());
		firstGrid = new Grid<>();
		firstGrid.setItems(skillsList);
		firstGrid.addColumn(SkillStud::getCategory).setHeader("Category");
		firstGrid.addColumn(SkillStud::getName).setHeader("Skill Name");
		firstGrid.setSelectionMode(SelectionMode.MULTI);
		vL.add(searchBar, firstGrid);
		vL.setWidthFull();
		return vL;
    }
    
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
    
    public void updateSkillList() {
    	List<SkillStud> listToDisplay = getSearchSkills(skillsList, searchBar.getValue());
        firstGrid.setItems(listToDisplay);
    }
    
    /**
     * clear all the fields in project dialog
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
    
    /**
     * updates the project list on main page, changes list if search bar
     * has a value entered
     */
    private void updateList() {
    	List<Projects> projects = getSearchValues(search.getValue());
    	
    	if (search.getValue().length() > 0) {
    		header.setText("Search for “" + search.getValue() + "”");
            header.addClassName("main-layout-project-title");
        } else {
        	header.setText("Project Proposals");
            header.addClassName("main-layout-project-title");
        }
        getModel().setReviews(projects);
    }
    
    private List<Projects> getSearchValues(String value){
    	if (value.isEmpty()) {
    		return projectList;
    	}
    	
    	List<Projects> listToDisplay = new ArrayList<Projects>();
    	for (int i = 0; i < projectList.size(); i++) {
    		if (projectList.get(i).getProjectTitle().toLowerCase().contains(value.toLowerCase())) {
    			listToDisplay.add(projectList.get(i));
    		}
    	}
		return listToDisplay;
    }
}
