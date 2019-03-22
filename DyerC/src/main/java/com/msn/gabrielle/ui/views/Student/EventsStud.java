/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.msn.gabrielle.ui.views.Student;

import java.time.LocalDate;

import org.vaadin.stefan.fullcalendar.CalendarView;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "eventsstud", layout = StudentPage.class)
@PageTitle("Events")
public class EventsStud extends VerticalLayout{

	public EventsStud() {
        initView();
        displayCalendar();
    }
	
	private void initView() {
        addClassName("events");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }
	
	private void displayCalendar() {
//		// Create a new calendar instance and attach it to our layout
//		FullCalendar calendar = FullCalendarBuilder.create().build();
//		add(calendar);
//
//		// Create a initial sample entry
//		Entry entry = new Entry();
//		entry.setTitle("Some event");
//		entry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 0));
//		entry.setEnd(entry.getStart().plusHours(2));
//		entry.setColor("#ff3333");
//
//		calendar.addEntry(entry);
		HasText intervalLabel = new Span();
	    // combo box to select a view for the calendar, like "monthly", "weekly", ...
	    FullCalendar calendar = FullCalendarBuilder.create().build();
		setFlexGrow(1, calendar);
	    ComboBox<CalendarView> viewBox = new ComboBox<>("", CalendarViewImpl.values());
	    viewBox.addValueChangeListener(e -> {
	        CalendarView value = e.getValue();
	        calendar.changeView(value == null ? CalendarViewImpl.MONTH : value);
	    });
	    viewBox.setValue(CalendarViewImpl.MONTH);
	    Label month = new Label("MARCH 2019");
	    add(month);
	    add(viewBox);
		add(calendar);

	}
}