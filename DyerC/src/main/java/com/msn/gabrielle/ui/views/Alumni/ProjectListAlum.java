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
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "projectsalum", layout = AlumniPage.class)
@PageTitle("Projects Alumni")
public class ProjectListAlum extends VerticalLayout{
		private final TextField searchField = new TextField("",
	            "Search projects");
	    private final H2 header = new H2("Project Proposals");
	    private final Grid<Projects> grid = new Grid<>();
	    private List<Projects> projectList = new ArrayList();
	    private String nameStr;
	    private String descriptionStr;
	    private String nameProposerStr;

	    SQLProjectAlum sqlPA = new SQLProjectAlum();
	    
	    public ProjectListAlum() {
	    	initView();
	        addSearchBar();
	        addContent();
	        projectList = sqlPA.loadProjects();
	        updateView();
	    }

	    private void initView() {
	        addClassName("categories-list");
	        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
	    }
	    
	    private void addStudentToggle() {
	        Div radio = new Div();
	    	RadioButtonGroup<String> group = new RadioButtonGroup<>();
	    	group.setItems("Student", "Employee");
	    	NativeButton button = new NativeButton("Switch validity state",
	    	        event -> group.setInvalid(!group.isInvalid()));
	    	radio.add(group);
	        add(radio);
	    }
	    
	    private String textSwitch(Button toggle) {
	    	if(toggle.getText().contentEquals("Employee")) {
	    		return "Student";
	    	}
	    	return "Employee";
	    }
	    
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
	    	Button closeButton = new Button("Cancel", event -> {
	    		viewDialog.close();
	    	});
	    	List<SkillStud> skillList = new ArrayList<SkillStud>();
	    	skillList = currentProj.getSkillList();
	    	Grid<SkillStud> grid = new Grid<>();
	    	grid.setItems(skillList);
	    	grid.addColumn(SkillStud::getCategory).setHeader("Category");
	    	grid.addColumn(SkillStud::getName).setHeader("Skill Name");
	    	grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
	    	        GridVariant.LUMO_NO_ROW_BORDERS);
	    	viewDialog.add(grid);
	    	viewDialog.add(closeButton);
	    	return viewDialog;
	    }

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

	    private Button createEditButton(Projects project) {
	        Button edit = new Button("View", event -> viewDialog(project).open());
	        edit.setIcon(new Icon("lumo", "view"));
	        edit.addClassName("review__edit");
	        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
	        return edit;
	    }

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
