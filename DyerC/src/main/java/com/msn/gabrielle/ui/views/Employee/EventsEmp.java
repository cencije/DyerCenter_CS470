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
package com.msn.gabrielle.ui.views.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.vaadin.stefan.fullcalendar.CalendarView;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "eventsemp", layout = EmployeePage.class)
@PageTitle("Events")
public class EventsEmp extends VerticalLayout{
	
	private Button addEventButton;
	VerticalLayout lay;
	HorizontalLayout hlay;
	FullCalendar calendar = FullCalendarBuilder.create().build();

	public EventsEmp() {
        initView();
        
        lay = new VerticalLayout();
        hlay = new HorizontalLayout();
        addEventButton = new Button("Add an Event", event ->  {
        	try {
        		Label enterhere = new Label("Now you'll enter a new event");
        		enterhere.setVisible(true);
        	} catch(Exception e) { e.printStackTrace(); }  }       
        );
        
        displayCalendar();
    }
	
	private void initView() {
        addClassName("events");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }
	
	private void displayCalendar() {
		
		LocalDate x = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		String formattedDate = x.format(formatter);
		Label currentMonth = new Label("");
		String currentYear = new String(formattedDate.substring(0, 4));
		
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '1') {
			currentMonth.setText("January " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '2') {
			currentMonth.setText("February " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '3') {
			currentMonth.setText("March " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '4') {
			currentMonth.setText("April " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '5') {
			currentMonth.setText("May " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '6') {
			currentMonth.setText("June " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '7') {
			currentMonth.setText("July " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '8') {
			currentMonth.setText("August " + currentYear);
		}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '9') {
			currentMonth.setText("September " + currentYear);
		}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '0') {
			currentMonth.setText("October " + currentYear);
		}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '1') {
			currentMonth.setText("November" + currentYear);
		}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '2') {
			currentMonth.setText("December " + currentYear);
		}
	    	
		//calendar.addEntry(entry);
		HasText intervalLabel = new Span();
	    calendar.setHeight(500);
		setFlexGrow(1, calendar);

		//Add all of the components to the page
		hlay.add(currentMonth);
		hlay.add(addEventButton);
        lay.add(hlay);
        lay.add(calendar);
        add(hlay);
        add(lay);
		
//
//		// Create a initial sample entry
//		Entry entry = new Entry();
//		entry.setTitle("Some event");
//		entry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 0));
//		entry.setEnd(entry.getStart().plusHours(2));
//		entry.setColor("#ff3333");
	
        
     // Create a initial sample entry
     		Entry sampleEntry = new Entry();
     		sampleEntry.setTitle("Some event");
     		sampleEntry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 30));
     		sampleEntry.setEnd(sampleEntry.getStart().plusHours(2));
     		sampleEntry.setColor("#ff3333");
     		calendar.addEntry(sampleEntry);
	}
}