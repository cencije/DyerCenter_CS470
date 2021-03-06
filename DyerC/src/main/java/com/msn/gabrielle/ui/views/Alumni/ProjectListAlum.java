package com.msn.gabrielle.ui.views.Alumni;

import java.util.ArrayList;
import java.util.List;

import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.ui.AlumniPage;
import com.msn.gabrielle.ui.views.Student.SkillStud;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Class for the project list for Alumni. Routes from AlumniPage.class and uses that layout 
 * @author Dyer Center Senior Design
 */
@Route(value = "projectsalum", layout = AlumniPage.class)
@PageTitle("Projects Alumni")
@HtmlImport("frontend://styles/shared-styles-ALUMNI.html")
public class ProjectListAlum extends VerticalLayout{
		private final TextField searchField = new TextField("", "Search by title"); //search bar for projects
	    private final H2 header = new H2("Project Proposals"); //header that displays what is being searched
	    private final Grid<Projects> grid = new Grid<>(); //grid for project display
	    private List<Projects> projectList = new ArrayList(); //list of project objects

	    SQLProjectAlum sqlPA = new SQLProjectAlum(); //sql for retrieving database info
	    
	    /**
	     * Contructor that sets up layout for the page
	     */
	    public ProjectListAlum() {
			addClassName("main-lay");
	    	initView();
			addClassName("main-layout-emp");
	        addSearchBar();
	        addContent();
	        projectList = sqlPA.loadProjects();
	        updateView();
	    }
	    
	    /**
	     * Adds the view to full stretch the screen
	     */
	    private void initView() {
	        addClassName("categories-list");
	        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
	    }
	    
	    /**
	     * Creates the search bar for the project list
	     */
	    private void addSearchBar() {
	        Div viewToolbar = new Div();
	        viewToolbar.addClassName("view-toolbar");

	        searchField.setPrefixComponent(new Icon("lumo", "search"));
	        searchField.addClassName("view-toolbar__search-field");
	        searchField.addValueChangeListener(e -> updateView());
	        searchField.setValueChangeMode(ValueChangeMode.EAGER);
	        searchField.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
	        searchField.setWidthFull();
	        
	        viewToolbar.add(searchField);
	        add(viewToolbar);
	    }
	    
	    /**
	     * Pop up window that displays the project details. Uses the vaadin component
	     * Dialog to display the pop up window 
	     * @param currentProj is the project that is being displayed in the window
	     * @return the pop up window for project view 
	     */
	    private Dialog viewDialog(Projects currentProj) {
	    	Dialog viewDialog = new Dialog();
	    	viewDialog.setCloseOnEsc(false);
	    	viewDialog.setCloseOnOutsideClick(false);
	    	VerticalLayout projectForum = new VerticalLayout();
	    	
	    	HorizontalLayout titleDuration = new HorizontalLayout();
	    	Label projectTitleLabel = new Label("Project title: " + currentProj.getProjectTitle());
	    	Label durationLabel = new Label("Duration: " + currentProj.getStartDate() + " to " + currentProj.getEndDate());
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
	    	Button closeButton = new Button("Close", event -> {
	    		viewDialog.close();
	    	});
	    	closeButton.addClassName("main-layout-emp__event");
	    	List<SkillStud> skillList = new ArrayList<SkillStud>();
	    	skillList = currentProj.getSkillList();
	    	Grid<SkillStud> gridSkill = new Grid<>();
	    	gridSkill.setItems(skillList);
	    	gridSkill.addColumn(SkillStud::getCategory).setHeader("Category");
	    	gridSkill.addColumn(SkillStud::getName).setHeader("Skill Name");
	    	gridSkill.addThemeVariants(GridVariant.LUMO_NO_BORDER,
	    	        GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
	    	viewDialog.add(gridSkill);
	    	viewDialog.setCloseOnOutsideClick(true);
	    	viewDialog.add(closeButton);
	    	return viewDialog;
	    }
	    
	    /**
	     * Adds the project list to the page
	     */
	    private void addContent() {
	        VerticalLayout container = new VerticalLayout();
	        container.setClassName("view-container");
	        container.setAlignItems(Alignment.STRETCH);

	        grid.addColumn(Projects::getProjectTitle).setHeader("Name").setWidth("8em")
	                .setResizable(true);
	        grid.addColumn(new ComponentRenderer<>(this::createEditButton))
	                .setFlexGrow(0);
	        grid.setSelectionMode(SelectionMode.NONE);

	        container.add(header, grid);
	        add(container);
	    }
	    
	    /**
	     * Creates the view button in the grid that when clicked displays the popup window with the'
	     * project details
	     * 
	     * @param project is the current project that the view button links
	     * @return the button created 
	     */
	    private Button createEditButton(Projects project) {
	        Button edit = new Button("View", event -> viewDialog(project).open());
	        edit.setIcon(new Icon("lumo", "view"));
	        edit.addClassName("review__edit");
	        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
	        return edit;
	    }
	    
	    /**
	     * Updates the project list if something is searched 
	     */
	    private void updateView() {
	    	grid.setItems(getSearchValues(searchField.getValue()));
	    	
	        if (searchField.getValue().length() > 0) {
	            header.setText("Search for “" + searchField.getValue() + "”");
	            header.addClassName("main-layout-project-title");

	        } else {
	            header.setText("Project Proposals");
	            header.addClassName("main-layout-project-title");
	        }
	    }
	    
	    /**
	     * Gets the projects that match the search results
	     * @param value is the string entered in the search bar 
	     * @return the list of projects that contain that string value
	     */
	    private List<Projects> getSearchValues(String value){
	    	if (value.isEmpty()) {
	    		return projectList;
	    	}
	    	
	    	List<Projects> listToDisplay = new ArrayList();
	    	for (int i = 0; i < projectList.size(); i++) {
	    		if (projectList.get(i).getProjectTitle().toLowerCase().contains(value.toLowerCase())) {
	    			listToDisplay.add(projectList.get(i));
	    		}
	    	}
			return listToDisplay;
	    }
	}
