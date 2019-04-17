package com.msn.gabrielle.ui.views.Employee;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.teemu.switchui.Switch;

import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.ui.*;
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
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "projectemp", layout = EmployeePage.class)
@PageTitle("Projects Employee")
public class ProjectsListEmp extends VerticalLayout {
	private final TextField searchField = new TextField("",
            "Search projects");
    private final H2 header = new H2("Project Proposals");
    private final Grid<Projects> grid = new Grid<>();
    private List<Projects> projectList = new ArrayList();
    private String nameStr;
    private String descriptionStr;
    private String nameProposerStr;

    public ProjectsListEmp() {
        initView();
        
        addStudentToggle();
        addSearchBar();
        addContent();

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
        	
        Button newButton = new Button("New project proposal", new Icon("lumo", "plus"));
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newButton.addClassName("view-toolbar__button");
        newButton.addClickListener(event-> dialog().open());
        /*
            This is a falFl-back method:
            '+' is not a event.code (DOM events), so as a fall-back shortcuts
            will perform a character-based comparison. Since Key.ADD changes
            locations frequently based on the keyboard language, we opted to use
            a character instead.
         */
        newButton.addClickShortcut(Key.of("+"));
        
        viewToolbar.add(searchField, newButton);
        add(viewToolbar);
    }
    
    private Dialog dialog() {
        Dialog dialog = new Dialog();

    	dialog.setCloseOnEsc(false);
    	dialog.setCloseOnOutsideClick(false);
    	
    	HorizontalLayout projectForum = new HorizontalLayout();
    	VerticalLayout nameLayout = new VerticalLayout();
    	Label nameProject = new Label("Project title: ");
    	TextField nameField = new TextField();
    	nameField.addValueChangeListener(event ->
    		nameStr = event.getValue());
    	nameField.setMaxLength(45);
    	nameLayout.add(nameProject, nameField);
    	
    	VerticalLayout description = new VerticalLayout();
    	Label descriptionProject = new Label("Project description: ");
    	TextField descriptionField = new TextField();
    	descriptionField.addValueChangeListener(event ->
    		descriptionStr = event.getValue());
    	nameLayout.add(descriptionProject, descriptionField);
    	
    	VerticalLayout proposerLayout = new VerticalLayout();
    	Label proposerProject = new Label("Name: ");
    	TextField proposerField = new TextField();
    	proposerField.addValueChangeListener(event ->
    		nameProposerStr = event.getValue());
    	proposerLayout.add(proposerProject, proposerField);
    	
    	VerticalLayout buttons = new VerticalLayout();
    	Button saveButton = new Button("Save", event -> {
    		Projects newProj = new Projects(nameStr, descriptionStr, nameProposerStr);
    		projectList.add(newProj);
    		dialog.close();
    		proposerField.clear();
    		descriptionField.clear();
    		nameField.clear();
    	    updateView();
    	});
    	Button cancelButton = new Button("Cancel", event -> {
    	    dialog.close();
    	});
    	buttons.add(saveButton, cancelButton);
    	projectForum.add(nameLayout, description, proposerLayout, buttons);
    	dialog.add(projectForum);
    	add(dialog);
    	return dialog;
    }
    
    private Dialog viewDialog(Projects currentProj) {
    	Dialog viewDialog = new Dialog();

    	viewDialog.setCloseOnEsc(false);
    	viewDialog.setCloseOnOutsideClick(false);
    	
    	VerticalLayout mainLay = new VerticalLayout();
    	VerticalLayout nameLay = new VerticalLayout();
    	Label nameProject = new Label("Project title: ");
    	Label nameCurr = new Label(currentProj.getName());
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

    private void addContent() {
        VerticalLayout container = new VerticalLayout();
        container.setClassName("view-container");
        container.setAlignItems(Alignment.STRETCH);

        grid.addColumn(Projects::getName).setHeader("Name").setWidth("8em")
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
    		if (projectList.get(i).getName().toLowerCase().contains(value.toLowerCase())) {
    			listToDisplay.add(projectList.get(i));
    		}
    	}
		return listToDisplay;
    }
}