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
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import com.msn.gabrielle.backend.Events;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import com.vaadin.flow.component.dialog.*;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.icon.*;

/**
 * EventsStud creates and fills the layout for the Events page within the 
 * student view.
 * @author Dyer Center Senior Design
 */
@Route(value = "eventsstud", layout = StudentPage.class)
@PageTitle("Events")
@HtmlImport("frontend://styles/shared-styles-ALUMNI.html")
public class EventsStud extends VerticalLayout{
	
	FullCalendar calendar = FullCalendarBuilder.create().build();
	int monthNumber, yearRn, yearNum, cMN;
	HorizontalLayout monthMoveLayout;
	Label currentMonth;
	String currentYear;
	Button today, lastMonth, nextMonth;
	ArrayList<Events> eventsList = new ArrayList<Events>();
	Label readTitle, readLoc, readTime;

	SQLEventStudent sqlES = new SQLEventStudent();
	
	/**
	 * Constructor; handles SQL code. 
	 */
	public EventsStud() {
        initView();
		addClassName("main-lay");
        
        sqlES.loadAll();
		addClassName("main-layout-emp");
        eventsList = sqlES.getAllEvents();
        
        displayCalendar();  
        displayEvents();
    }
	
	/**
	 * Initialize the view of the calendar.
	 */
	private void initView() {
        addClassName("main-lay");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }
	
	/**
	 * Sets the calendar title Label based on the month and year numbers.
	 */
	private void monthLabelSetUp() {
		if(monthNumber == 1) { currentMonth.setText("January " + yearNum);}
		if(monthNumber == 2) { currentMonth.setText("February " + yearNum);}
		if(monthNumber == 3) { currentMonth.setText("March " + yearNum);}
		if(monthNumber == 4) { currentMonth.setText("April " + yearNum);}
		if(monthNumber == 5) { currentMonth.setText("May " + yearNum);}
		if(monthNumber == 6) { currentMonth.setText("June " + yearNum);}
		if(monthNumber == 7) { currentMonth.setText("July " + yearNum);}
		if(monthNumber == 8) { currentMonth.setText("August " + yearNum);}
		if(monthNumber == 9) { currentMonth.setText("September " + yearNum);}
		if(monthNumber == 10) { currentMonth.setText("October " + yearNum);}
		if(monthNumber == 11) { currentMonth.setText("November " + yearNum);}
		if(monthNumber == 12) { currentMonth.setText("December " + yearNum);}
	}
	
	/**
	 * Displays the view of the Events page; creates the labels, buttons, and calendar,
	 * and handles EntryClicked functionality.
	 */
	private void displayCalendar() {
		
		calendar.setHeight(500);
		calendar.addClassName("main-lay__calendar");
		setFlexGrow(1, calendar);
		
		VerticalLayout vlay = new VerticalLayout();
		
		LocalDate x = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		String formattedDate = x.format(formatter);
		currentMonth = new Label("");
		currentMonth.addClassName("main-lay__event-title");
		currentYear = new String(formattedDate.substring(0, 4));
		yearRn = Integer.parseInt(currentYear);
		yearNum = yearRn;

		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '1') { cMN = 1; monthNumber = 1;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '2') { cMN = 2; monthNumber = 2;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '3') { cMN = 3; monthNumber = 3;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '4') { cMN = 4; monthNumber = 4;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '5') { cMN = 5; monthNumber = 5;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '6') { cMN = 6; monthNumber = 6;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '7') { cMN = 7; monthNumber = 7;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '8') { cMN = 8; monthNumber = 8;}
		if(formattedDate.charAt(5) == '0' && formattedDate.charAt(6) == '9') { cMN = 9; monthNumber = 9;}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '0') { cMN = 10; monthNumber = 10;}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '1') { cMN = 11; monthNumber = 11;}
		if(formattedDate.charAt(5) == '1' && formattedDate.charAt(6) == '2') { cMN = 12;monthNumber = 12;}
		
		monthLabelSetUp();
		
		today = new Button("Today", event -> {
        	calendar.today();
        	monthNumber = cMN;
        	yearNum = yearRn;
        	monthLabelSetUp();
        });
		today.addClassName("main-lay__event-today");
		
		lastMonth = new Button(new Icon(VaadinIcon.ANGLE_LEFT), event -> {
        	calendar.previous();
        	if(monthNumber == 1) { yearNum--; monthNumber = 12; } else { monthNumber--;}
        	monthLabelSetUp();
        });
        lastMonth.addClassName("main-lay__button");

        nextMonth = new Button(new Icon(VaadinIcon.ANGLE_RIGHT), event -> {
        	calendar.next();
        	if(monthNumber == 12) { yearNum++; monthNumber = 1; } else { monthNumber++;}
        	monthLabelSetUp();
        });
        nextMonth.addClassName("main-lay__button");
        
        monthMoveLayout = new HorizontalLayout();
        monthMoveLayout.add(today, lastMonth, nextMonth);
	    
        vlay.add(currentMonth, monthMoveLayout, calendar);
        add(vlay); 
        
        calendar.addEntryClickedListener(EntryClickedEvent -> {
        	Dialog p = new Dialog();
        	VerticalLayout k = new VerticalLayout();
        	readTitle = new Label(EntryClickedEvent.getEntry().getTitle().toString());
        	readLoc = new Label("Located in: " + EntryClickedEvent.getEntry().getDescription().toString());
        	readTime = new Label("Event begins at: " + EntryClickedEvent.getEntry().getStart().toString().substring(11, 16));
        	k.add(readTitle, readLoc, readTime);
        	p.add(k);
        	p.open();
        });
        
	}
	
	/**
	 * Iterates through the the list of Events and adds them all as Entry objects to the calendar.
	 */
	private void displayEvents() {
		calendar.removeAllEntries();
		for(int i = 0; i < eventsList.size(); i++) {
			Entry newEntry = new Entry();
			newEntry.setTitle(eventsList.get(i).getTitle());
			if(eventsList.get(i).getUrl().equals("null")) {
				newEntry.setDescription(eventsList.get(i).getLocation() + ", " +
						eventsList.get(i).getDescription());
			} else {
				newEntry.setDescription(eventsList.get(i).getLocation() + ", " +
						eventsList.get(i).getDescription() + 
						" For more information, check out " + 
						eventsList.get(i).getUrl());
			}
			newEntry.setStart(LocalDate.of(eventsList.get(i).getYear(), eventsList.get(i).getMonth(), 
								eventsList.get(i).getDay()).atTime(eventsList.get(i).getHour(), 
								eventsList.get(i).getMinute()));
			newEntry.setEnd(newEntry.getStart().plusHours(1));
			newEntry.setEditable(false);
			newEntry.setColor("#4DA8D8");
			calendar.addEntry(newEntry);
		}
	}
}