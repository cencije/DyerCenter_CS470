package com.msn.gabrielle.ui.views.Employee;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.teemu.switchui.Switch;

import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.ui.*;
import com.msn.gabrielle.ui.views.Student.SkillStud;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "projectemp", layout = EmployeePage.class)
@PageTitle("Projects Employee")
public class ProjectsListEmp extends VerticalLayout {
	private final TextField searchField = new TextField("",
            "Search by title");
    private final H2 header = new H2("Project Proposals");
    private final Grid<Projects> grid = new Grid<>();
    private List<Projects> projectList = new ArrayList();
    private String nameStr;
    private String descriptionStr;
    private String nameProposerStr;

    SQLProjectEmp sqlPE = new SQLProjectEmp();
    public ProjectsListEmp() {
        initView();
        addSearchBar();
        addContent();
        projectList = sqlPE.loadProjects();
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
    
    /*
    private Dialog viewDialog(Projects currentProj) {
    	System.out.println("Title " + currentProj.getProjectTitle());
    	Dialog viewDialog = new Dialog();

    	viewDialog.setCloseOnEsc(false);
    	viewDialog.setCloseOnOutsideClick(false);
    	
    	VerticalLayout mainLay = new VerticalLayout();
    	VerticalLayout nameLay = new VerticalLayout();
    	Label nameProject = new Label("Project title: ");
    	Label nameCurr = new Label(currentProj.getProjectTitle());
    	nameLay.add(nameProject,nameCurr );
    	
    	VerticalLayout descriptionLay = new VerticalLayout();
    	Label descriptionProject = new Label("Project description: ");
    	Label descriptionCurr = new Label(currentProj.getDescription());
    	descriptionLay.add(descriptionProject, descriptionCurr);
    	
    	VerticalLayout proposerLay = new VerticalLayout();
    	Label proposerProject = new Label("Project proposer: ");
    	Label proposerCurr = new Label(currentProj.getProposedBy());
    	proposerLay.add(proposerProject, proposerCurr);
    	mainLay.add(nameLay, descriptionLay, proposerLay);
    	
    	NativeButton closeButton = new NativeButton("Close", event -> {
    		viewDialog.close();
    	});
    	
    	mainLay.add(closeButton);
    	viewDialog.add(mainLay);
    	return viewDialog;
    }
    */

    private void addContent() {
        VerticalLayout container = new VerticalLayout();
        container.setClassName("view-container");
        container.setAlignItems(Alignment.STRETCH);

        grid.addColumn(Projects::getProjectTitle).setHeader("Name").setWidth("8em")
                .setResizable(true);
        grid.addColumn(new ComponentRenderer<>(this::createEditButton))
                .setFlexGrow(0);
        grid.addThemeName("rounded-rows");
        grid.setSelectionMode(SelectionMode.NONE);

        container.add(header, grid);
        add(container);
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
     	Button closeButton = new Button("Close", event -> {
    		viewDialog.close();
    	});
     	closeButton.addClassName("main-layout-emp__event");
    	viewDialog.setCloseOnOutsideClick(true);
    	List<SkillStud> skillList = currentProj.getSkillList();
    	Grid<SkillStud> gridSkill = new Grid<>();
    	gridSkill.setItems(skillList);
    	gridSkill.addColumn(SkillStud::getCategory).setHeader("Category");
    	gridSkill.addColumn(SkillStud::getName).setHeader("Skill Name");
    	gridSkill.addThemeVariants(GridVariant.LUMO_NO_BORDER,
    	        GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
    	viewDialog.add(gridSkill);
    	Button deleteProject = new Button("Delete",
    	        new Icon(VaadinIcon.TRASH));
    	deleteProject.addClickListener(e -> {
    		sqlPE.deleteProject(currentProj);
    		updateView();
    		viewDialog.close();
    	});
    	deleteProject.addClassName("main-layout-emp__event");
    	viewDialog.add(closeButton, deleteProject);
    	return viewDialog;
    }

    private Button createEditButton(Projects project) {
    	
        Button edit = new Button("View", event -> viewDialog(project).open());
        edit.setIcon(new Icon("lumo", "view"));
        edit.addClassName("review__edit");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        edit.addClickListener(e -> viewDialog(project));
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
    	projectList = sqlPE.loadProjects();
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
