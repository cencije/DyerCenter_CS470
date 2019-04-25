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
import com.vaadin.flow.component.textfield.TextArea;
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
import com.vaadin.flow.component.datepicker.DatePicker;
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
    private List<Projects> projectList = new ArrayList<Projects>();
    private String nameStr;
    private String descriptionStr;
    private String nameProposerStr;
    private DatePicker datePickerFirst;
    private DatePicker datePickerSecond;
   
    
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
    	
    	VerticalLayout projectForum = new VerticalLayout();
    	projectForum.add(projectTitle());
    	projectForum.add(duration());
    	TextArea area = new TextArea("Big Area");

    	// Put some content in it
    	area.setValue("A row\n"+
    	              "Another row\n"+
    	              "Yet another row");
    	
    	HorizontalLayout buttons = new HorizontalLayout();
    	Button saveButton = new Button("Save", event -> {
    		Projects newProj = new Projects(nameStr, descriptionStr, nameProposerStr);
    		projectList.add(newProj);
    		dialog.close();
    		clearAll();
    		updateList();
    	});
    	Button cancelButton = new Button("Cancel", event -> {
    	    dialog.close();
    	});
    	buttons.add(saveButton, cancelButton);
    	dialog.add(projectForum, area, buttons);
    	dialog.open();
    	return dialog;
    }
    
    TextField pTField;
    /**
     * Layout for project title & text field
     * @return vertical layout
     */
    public VerticalLayout projectTitle() {
    	VerticalLayout pT = new VerticalLayout();
    	pT.add(new Label("Title: "));
    	pTField = new TextField();
    	pT.add(pTField);
    	return pT;
    }
    
    /**
     * Layout for duration of project
     * @return vertical layout
     */
    public VerticalLayout duration() {
    	VerticalLayout dur = new VerticalLayout();
    	HorizontalLayout datePicker = new HorizontalLayout();
    	datePickerFirst = new DatePicker();
    	Label to = new Label(" to ");
    	datePickerSecond = new DatePicker();
    	datePicker.add(datePickerFirst,to, datePickerSecond);
    	Label duration = new Label("Duration: ");
    	dur.add(duration, datePicker);
    	return dur;
    }
    
    /**
     * clear all the fields in project dialog
     */
    public void clearAll() {
    	pTField.clear();
    	datePickerFirst.clear();
    	datePickerSecond.clear();
    }
    
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
    		if (projectList.get(i).getName().toLowerCase().contains(value.toLowerCase())) {
    			listToDisplay.add(projectList.get(i));
    		}
    	}
		return listToDisplay;
    }
}
