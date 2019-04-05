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
import com.msn.gabrielle.ui.views.categorieslist.CategoryEditorDialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

@Route(value = "projectsstud", layout = StudentPage.class)
@PageTitle("Projects")
public class ProjectListStud extends VerticalLayout{
	private final TextField searchField = new TextField("",
            "Search projects");
    private final H2 header = new H2("Project Proposals");
    private final Grid<Projects> grid = new Grid<>();
    private List<Projects> projectList = new ArrayList();
    private String nameStr;
    private String descriptionStr;
    private String nameProposerStr;

//    private final ProjectEditorStud form = new ProjectEditorStud(
//            this::saveProject, this::deleteProject);

    public ProjectListStud() {
        initView();

        addSearchBar();
        addContent();

        updateView();
    }

    private void initView() {
        addClassName("categories-list");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }

    private void addSearchBar() {
        Div viewToolbar = new Div();
        viewToolbar.addClassName("view-toolbar");

        searchField.setPrefixComponent(new Icon("lumo", "search"));
        searchField.addClassName("view-toolbar__search-field");
        searchField.addValueChangeListener(e -> updateView());
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        
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
    	Label proposerProject = new Label("Project description: ");
    	TextField proposerField = new TextField();
    	proposerField.addValueChangeListener(event ->
    		nameProposerStr = event.getValue());
    	proposerLayout.add(proposerProject, proposerField);
    	
    	VerticalLayout buttons = new VerticalLayout();
    	Button saveButton = new Button("Save", event -> {
    		Projects newProj = new Projects(nameStr, descriptionStr, nameProposerStr);
    		projectList.add(newProj);
    	    dialog.close();
    	    updateView();
    	});
    	Button cancelButton = new Button("Cancel", event -> {
    	    dialog.close();
    	});
    	buttons.add(saveButton, cancelButton);
    	projectForum.add(nameLayout, description, proposerLayout, buttons);
    	dialog.add(projectForum);
    	add(dialog);
        	
        Button newButton = new Button("New project proposal", new Icon("lumo", "plus"));
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newButton.addClassName("view-toolbar__button");
        newButton.addClickListener(event-> dialog.open());
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

    	Label messageLabel = new Label();

    	NativeButton confirmButton = new NativeButton("Confirm", event -> {
    	    messageLabel.setText("Confirmed!");
    	    dialog.close();
    	});
    	NativeButton cancelButton = new NativeButton("Cancel", event -> {
    	    messageLabel.setText("Cancelled...");
    	    dialog.close();
    	});
    	dialog.add(confirmButton, cancelButton);
    	return dialog;
    }

    private void addContent() {
        VerticalLayout container = new VerticalLayout();
        container.setClassName("view-container");
        container.setAlignItems(Alignment.STRETCH);

        grid.addColumn(Projects::getName).setHeader("Name").setWidth("8em")
                .setResizable(true);
//        grid.addColumn(new ComponentRenderer<>(this::createEditButton))
//                .setFlexGrow(0);
        grid.setSelectionMode(SelectionMode.NONE);

        container.add(header, grid);
        add(container);
    }
//
//    private Button createEditButton(Projects project) {
//        Button edit = new Button("Edit", event -> form.open(project,
//                AbstractEditorDialog.Operation.EDIT));
//        edit.setIcon(new Icon("lumo", "edit"));
//        edit.addClassName("review__edit");
//        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
////        if (CategoryService.getInstance().getUndefinedCategory().getId()
////                .equals(category.getId())) {
////            edit.setEnabled(false);
////        }
//        return edit;
//    }

    private String getReviewCount(Category category) {
        List<Review> reviewsInCategory = ReviewService.getInstance()
                .findReviews(category.getName());
        int sum = reviewsInCategory.stream().mapToInt(Review::getCount).sum();
        return Integer.toString(sum);
    }

    private void updateView() {
//        List<Projects> projects = CategoryService.getInstance()
//                .findCategories(searchField.getValue());
//        grid.setItems(projects);
    	grid.setItems(projectList);
    	
        if (searchField.getValue().length() > 0) {
            header.setText("Search for “" + searchField.getValue() + "”");
        } else {
            header.setText("Project Proposals");
        }
    }

    private void saveProject(Projects project,
            AbstractEditorDialog.Operation operation) {
        //CategoryService.getInstance().saveCategory(category);
    	//SAVE PROJECT HERE
    	
        Notification.show(
                "Project proposal successfully " + operation.getNameInText() + "ed.",
                3000, Position.BOTTOM_START);
        updateView();
    }

    private void deleteProject(Projects project) {
        List<Review> reviewsInCategory = ReviewService.getInstance()
                .findReviews(project.getName());

        reviewsInCategory.forEach(review -> {
            review.setCategory(
                    CategoryService.getInstance().getUndefinedCategory());
            ReviewService.getInstance().saveReview(review);
        });
        //CategoryService.getInstance().deleteCategory(project);

        Notification.show("Project proposal successfully deleted.", 3000,
                Position.BOTTOM_START);
        updateView();
    }
}
