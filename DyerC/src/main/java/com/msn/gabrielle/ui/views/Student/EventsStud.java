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
import java.time.format.DateTimeFormatter;

import org.vaadin.stefan.fullcalendar.CalendarView;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import com.vaadin.flow.component.dialog.*;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.icon.*;

@Route(value = "eventsstud", layout = StudentPage.class)
@PageTitle("Events")
@HtmlImport("frontend://styles/shared-styles.html")
public class EventsStud extends VerticalLayout{
	
	FullCalendar calendar = FullCalendarBuilder.create().build();
	int monthNumber;

	public EventsStud() {
        initView();
        displayCalendar();   
    }
	
	private void initView() {
        addClassName("main-layout");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }
	
	private void displayCalendar() {
//		 // Create a new calendar instance and attach it to our layout
//		FullCalendar calendar = FullCalendarBuilder.create().build();
//		add(calendar);
//
//		calendar.addEntry(entry);
		HasText intervalLabel = new Span();
	    // combo box to select a view for the calendar, like "monthly", "weekly", ...
	    // FullCalendar calendar = FullCalendarBuilder.create().build();
	    calendar.setHeight(500);
		setFlexGrow(1, calendar);
		
		VerticalLayout vlay = new VerticalLayout();
		
		LocalDate x = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		String formattedDate = x.format(formatter);
		Label currentMonth = new Label("");
		currentMonth.addClassName("view-toolbar__event-title");
		String currentYear = new String(formattedDate.substring(0, 4));
		
		Button today = new Button("Today", event -> {
        	calendar.today();
        });
		today.addClassName("view-toolbar__event-today");

		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '1') { monthNumber = 1;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '2') { monthNumber = 2;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '3') { monthNumber = 3;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '4') { monthNumber = 4;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '5') { monthNumber = 5;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '6') { monthNumber = 6;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '7') { monthNumber = 7;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '8') { monthNumber = 8;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '9') { monthNumber = 9;}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '0') { monthNumber = 10;}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '1') { monthNumber = 11;}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '2') { monthNumber = 12;}
		
		if(monthNumber == 1) { currentMonth.setText("January " + currentYear);}
		if(monthNumber == 2) { currentMonth.setText("February " + currentYear);}
		if(monthNumber == 3) { currentMonth.setText("March " + currentYear);}
		if(monthNumber == 4) { currentMonth.setText("April " + currentYear);}
		if(monthNumber == 5) { currentMonth.setText("May " + currentYear);}
		if(monthNumber == 6) { currentMonth.setText("June " + currentYear);}
		if(monthNumber == 7) { currentMonth.setText("July " + currentYear);}
		if(monthNumber == 8) { currentMonth.setText("August " + currentYear);}
		if(monthNumber == 9) { currentMonth.setText("September " + currentYear);}
		if(monthNumber == 10) { currentMonth.setText("October " + currentYear);}
		if(monthNumber == 11) { currentMonth.setText("November " + currentYear);}
		if(monthNumber == 12) { currentMonth.setText("December " + currentYear);}
		
		Button lastMonth = new Button(new Icon(VaadinIcon.ANGLE_LEFT), event -> {
        	calendar.previous();
        	if(monthNumber == 1) { monthNumber = 12; } else { monthNumber--;}
        	
        	if(monthNumber == 1) { currentMonth.setText("January " + currentYear);}
    		if(monthNumber == 2) { currentMonth.setText("February " + currentYear);}
    		if(monthNumber == 3) { currentMonth.setText("March " + currentYear);}
    		if(monthNumber == 4) { currentMonth.setText("April " + currentYear);}
    		if(monthNumber == 5) { currentMonth.setText("May " + currentYear);}
    		if(monthNumber == 6) { currentMonth.setText("June " + currentYear);}
    		if(monthNumber == 7) { currentMonth.setText("July " + currentYear);}
    		if(monthNumber == 8) { currentMonth.setText("August " + currentYear);}
    		if(monthNumber == 9) { currentMonth.setText("September " + currentYear);}
    		if(monthNumber == 10) { currentMonth.setText("October " + currentYear);}
    		if(monthNumber == 11) { currentMonth.setText("November " + currentYear);}
    		if(monthNumber == 12) { currentMonth.setText("December " + currentYear);}
        	
        	System.out.println("CURRENT_MONTH: " + monthNumber);
        });
        lastMonth.addClassName("view-toolbar__event-click");

        Button nextMonth = new Button(new Icon(VaadinIcon.ANGLE_RIGHT), event -> {
        	calendar.next();
        	if(monthNumber == 12) { monthNumber = 1; } else { monthNumber++;}
        	
        	if(monthNumber == 1) { currentMonth.setText("January " + currentYear);}
    		if(monthNumber == 2) { currentMonth.setText("February " + currentYear);}
    		if(monthNumber == 3) { currentMonth.setText("March " + currentYear);}
    		if(monthNumber == 4) { currentMonth.setText("April " + currentYear);}
    		if(monthNumber == 5) { currentMonth.setText("May " + currentYear);}
    		if(monthNumber == 6) { currentMonth.setText("June " + currentYear);}
    		if(monthNumber == 7) { currentMonth.setText("July " + currentYear);}
    		if(monthNumber == 8) { currentMonth.setText("August " + currentYear);}
    		if(monthNumber == 9) { currentMonth.setText("September " + currentYear);}
    		if(monthNumber == 10) { currentMonth.setText("October " + currentYear);}
    		if(monthNumber == 11) { currentMonth.setText("November " + currentYear);}
    		if(monthNumber == 12) { currentMonth.setText("December " + currentYear);}
        	
        	System.out.println("CURRENT_MONTH: " + monthNumber);
        });
        nextMonth.addClassName("view-toolbar__event-click");
        
        HorizontalLayout monthMoveLayout = new HorizontalLayout();
        monthMoveLayout.add(today, lastMonth, nextMonth);
	    
        vlay.add(currentMonth, monthMoveLayout, calendar);
        add(vlay); 
        
		//add(calendar);
		
		// Create a initial sample entry
		Entry sampleEntry = new Entry();
		sampleEntry.setTitle("Example Event");
		sampleEntry.setStart(LocalDate.now().withDayOfMonth(24).atTime(12, 15));
		sampleEntry.setEnd(sampleEntry.getStart().plusHours(2));
		calendar.addEntry(sampleEntry);
		
		Dialog d = new Dialog();
		Label title = new Label(sampleEntry.getTitle());
		Label time = new Label(sampleEntry.getStart().toString().substring(11, 16));
		Label loc = new Label("Hugel 100");
		Label desc = new Label("Very cool speaker, lunch will be provided");
		VerticalLayout lay = new VerticalLayout();
		lay.add(title, time, loc, desc);
		d.add(lay);
		add(d);
		
		calendar.addEntryClickedListener(event -> {
			d.open();
		});
		

	}
}