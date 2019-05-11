package com.msn.gabrielle.ui.views.Alumni;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "projectpropalum", layout = AlumniPage.class)
@PageTitle("Project Proposal")
public class ProjectProposalAlum extends VerticalLayout {
	private TextField pTField;
	private DatePicker datePickerFirst;
	private DatePicker datePickerSecond;
	private TextField locationTF;
	private TextArea area;
	private ComboBox<String> comboBox;
	private String pay;
	private TextField name;
	private ComboBox<String> cB;
	private Grid firstGrid;
    private List<Projects> projectList = new ArrayList<Projects>();
	private DatePicker dialog;
	private String nameStr;
	
	public ProjectProposalAlum() {
		setWidthFull();
		setHeightFull();
		addClassName("main-layout-emp");
		HorizontalLayout hL = new HorizontalLayout();
		hL.add(projectTitle(), duration());
		add(hL);
		add(location());
		add(projectDescription());
		HorizontalLayout hL1 = new HorizontalLayout();
		hL1.add(salary(), proposerName());
		add(hL1);
		add(skills());
		HorizontalLayout hL2 = new HorizontalLayout();
    	Button saveButton = new Button("Save", event -> {
    		Set<String> skills = firstGrid.getSelectedItems();
    		Projects newProj = new Projects(pTField.getValue());
    		newProj.setStartDate(datePickerFirst.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    		newProj.setEndDate(datePickerSecond.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    		newProj.setLocation(locationTF.getValue());
    		newProj.setDescription(area.getValue());
    		newProj.setPay(pay);
    		newProj.setProposedBy(name.getValue());
    		newProj.setDatePosted(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    		newProj.setSkillsSet(skills);
    		projectList.add(newProj);
    		clearAll();
    	});
    	saveButton.addClassName("view-toolbar__event-click");
    	hL2.add(saveButton);
    	add(hL2);
	}
    /**
     * Layout for project title & text field for project dialog
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
     * @return
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
    	List<SkillStud> personList = new ArrayList<SkillStud>(); //personService.fetchAll();
		personList.add(new SkillStud("CompSci", "Coding"));
		personList.add(new SkillStud("Anthropology & Sociology", "Social Constructs"));
		cB = new ComboBox<String>("Skills required: ");
		cB.setPlaceholder("Category");
		firstGrid = new Grid<>();
		firstGrid.setItems(personList);
		firstGrid.setSelectionMode(SelectionMode.MULTI);
		vL.add(cB, firstGrid);
		vL.setWidthFull();
		return vL;
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
    	cB.clear();
    	firstGrid.deselectAll();
    }
}