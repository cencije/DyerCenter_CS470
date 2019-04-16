package com.msn.gabrielle.ui.views.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msn.gabrielle.backend.Category;
import com.msn.gabrielle.backend.CategoryService;
import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.backend.Review;
import com.msn.gabrielle.backend.ReviewService;
import com.msn.gabrielle.ui.*;
import com.msn.gabrielle.ui.common.AbstractEditorDialog;
import com.msn.gabrielle.ui.encoders.LocalDateToStringEncoder;
import com.msn.gabrielle.ui.encoders.LongToStringEncoder;
import com.msn.gabrielle.ui.views.categorieslist.CategoryEditorDialog;
import com.msn.gabrielle.ui.views.reviewslist.ReviewEditorDialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.Encode;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.msn.gabrielle.ui.views.Student.ProjectListStud.ProjectsModel;

@Route(value = "projectsstud", layout = StudentPage.class)
@PageTitle("Projects")
@HtmlImport("frontend://styles/shared-project-style.html")
@Tag("reviews-list")
public class ProjectListStud extends PolymerTemplate<ProjectsModel>{
	private TextField searchField = new TextField("",
            "Search projects");
    //private H2 header = new H2("Project Proposals");
    private List<Projects> projectList = new ArrayList<Projects>();
    private String nameStr;
    private String descriptionStr;
    private String nameProposerStr;
   
    
    public interface ProjectsModel extends TemplateModel{
    	@Encode(value = LongToStringEncoder.class, path = "id")
        @Encode(value = LocalDateToStringEncoder.class, path = "date")
        void setReviews(List<Projects> projects);
    }
    
    @Id("search")
    private TextField search;
    @Id("newReview")
    private Button addReview;
    @Id("header")
    private H2 header;
    
    public ProjectListStud() {
      search.setPlaceholder("Search reviews");
      search.addValueChangeListener(e -> updateList());
      search.setValueChangeMode(ValueChangeMode.EAGER);
      search.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        //initView();
      getElement().setProperty("reviewButtonText", "New project");
      getElement().setProperty("editButtonText", "View");
      addReview.addClickListener(e -> 
      getUI().ifPresent(ui -> ui.add(dialog())));
      updateList();
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
    		updateList();
    	});
    	Button cancelButton = new Button("Cancel", event -> {
    	    dialog.close();
    	});
    	buttons.add(saveButton, cancelButton);
    	projectForum.add(nameLayout, description, proposerLayout, buttons);
    	dialog.add(projectForum);
    	//add(dialog);
    	dialog.open();
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
    
    public void saveUpdate(Review review,
            AbstractEditorDialog.Operation operation) {
        ReviewService.getInstance().saveReview(review);
        updateList();
        Notification.show(
                "Beverage successfully " + operation.getNameInText() + "ed.",
                3000, Position.BOTTOM_START);
    }

    public void deleteUpdate(Review review) {
        ReviewService.getInstance().deleteReview(review);
        updateList();
        Notification.show("Beverage successfully deleted.", 3000,
                Position.BOTTOM_START);
    }

    private Button createEditButton(Projects project) {
        Button edit = new Button("View", event -> viewDialog(project).open());
        edit.setIcon(new Icon("lumo", "view"));
        edit.addClassName("review__edit");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return edit;
    }

    
    private void updateList() {
    	List<Projects> projects = getSearchValues(searchField.getValue());
    	
    	if (searchField.getValue().length() > 0) {
    		header.setText("Search for “" + searchField.getValue() + "”");
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
    	
    	List<Projects> listToDisplay = new ArrayList();
    	for (int i = 0; i < projectList.size(); i++) {
    		if (projectList.get(i).getName().toLowerCase().contains(value.toLowerCase())) {
    			listToDisplay.add(projectList.get(i));
    		}
    	}
		return listToDisplay;
    }
}
