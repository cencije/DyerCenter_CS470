package com.msn.gabrielle.ui.views.Student;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.msn.gabrielle.backend.Category;
import com.msn.gabrielle.backend.CategoryService;
import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.backend.Review;
import com.msn.gabrielle.ui.common.AbstractEditorDialog;

/**
 * A dialog for editing {@link Review} objects.
 */
public class ProjectEditorDialog extends AbstractEditorDialog<Projects> {

    private DatePicker lastTasted = new DatePicker();
    private TextField beverageName = new TextField();
    private TextField timesTasted = new TextField();

    public ProjectEditorDialog(BiConsumer<Projects, Operation> saveHandler,
            Consumer<Projects> deleteHandler) {
        super("project", saveHandler, deleteHandler);

        createNameField();
        createDatePicker();
        createTimesField();
    }

    private void createDatePicker() {
        lastTasted.setLabel("Last tasted");
        lastTasted.setRequired(true);
        lastTasted.setMax(LocalDate.now());
        lastTasted.setMin(LocalDate.of(1, 1, 1));
        lastTasted.setValue(LocalDate.now());
        getFormLayout().add(lastTasted);

    }
    
    private void createTimesField() {
        timesTasted.setLabel("Times tasted");
        timesTasted.setRequired(true);
        timesTasted.setPattern("[0-9]*");
        timesTasted.setPreventInvalidInput(true);
        getFormLayout().add(timesTasted);

//        getBinder().forField(timesTasted)
//                .withConverter(
//                        new StringToIntegerConverter(0, "Must enter a number."))
//                .withValidator(new IntegerRangeValidator(
//                        "The tasting count must be between 1 and 99.", 1, 99))
//                .bind(Review::getCount, Review::setCount);
    }

    private void createNameField() {
        beverageName.setLabel("Beverage");
        beverageName.setRequired(true);
        getFormLayout().add(beverageName);

//        getBinder().forField(beverageName)
//                .withConverter(String::trim, String::trim)
//                .withValidator(new StringLengthValidator(
//                        "Beverage name must contain at least 3 printable characters",
//                        3, null))
//                .bind(Review::getName, Review::setName);
    }

    @Override
    protected void confirmDelete() {
        openConfirmationDialog("Delete review",
                "Are you sure you want to delete the review for “" + getCurrentItem().getName() + "”?", "");
    }

}
