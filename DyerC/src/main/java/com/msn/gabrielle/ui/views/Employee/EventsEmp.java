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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.msn.gabrielle.ui.*;
import com.msn.gabrielle.backend.Events;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.datepicker.*;
import com.vaadin.flow.component.timepicker.*;

import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.textfield.*;

@Route(value = "eventsemp", layout = EmployeePage.class)
@PageTitle("Events")
@HtmlImport("frontend://styles/calendar-style.html")
public class EventsEmp extends VerticalLayout{
	
	FullCalendar calendar = FullCalendarBuilder.create().build();
	private Button addEventButton, saveEvent, closeD, lastMonth, nextMonth, today, removeEvent;
	VerticalLayout lay, addEventDialogVLay;
	HorizontalLayout hlay, timeLayout, ButtonsLay, monthMoveLayout;
	String titleValue, locationValue, datimeValue, descValue, urlLink, currentYear;
	int monthValue, dayValue, hourValue, minuteValue, 
		monthNumber, cMN, yearValue, yearNum;
	public ArrayList<Events> eventsList = new ArrayList<Events>();
	Label errorMessage, currentMonth, readTitle, readLoc, readTime;
	TextField titleField, locField, descField, urlField;
	Dialog newEventDialog, eventClickedDialog;
	DatePicker dp;
	TimePicker tp;
	SQLEventEmp sqlEE = new SQLEventEmp();
	public EventsEmp() {
        initView();
        
         
        sqlEE.loadAll();
        eventsList = sqlEE.getAllEvents();
        
        lay = new VerticalLayout();
        hlay = new HorizontalLayout();
      
        calendar.addEntryClickedListener(EntryClickedEvent -> {
        	Dialog p = new Dialog();
        	VerticalLayout k = new VerticalLayout();
        	Entry e = EntryClickedEvent.getEntry();
        	readTitle = new Label(e.getTitle().toString());
        	readLoc = new Label("Located in: " + e.getDescription().toString());
        	readTime = new Label("Event begins at: " + e.getStart().toString().substring(11, 16));
        	removeEvent = new Button("Remove this event");
        	removeEvent.addClickListener(event -> {
        		//sqlEE.deleteEvent(e.getTitle(), e.getDescription(), e., e.location, desc, url, day, month, year, hour, min);
        		calendar.removeEntry(EntryClickedEvent.getEntry());
        		p.close();
        		//SQL code to remove the event
        	});
        	k.add(readTitle, readLoc, readTime, removeEvent);
        	p.add(k);
        	p.open();
        });
        
        displayCalendar();
        displayEvents();
        
        ButtonsLay = new HorizontalLayout();
		saveEvent = new Button("Save Event");
		closeD = new Button("Close");
		errorMessage = new Label("Please fill out all of the required fields.");
        
        ButtonsLay.add(saveEvent, closeD);
    }
	
	private void openAddEvent() {
		
		// Create the Dialog
		newEventDialog = new Dialog();
        newEventDialog.setCloseOnEsc(false);
        newEventDialog.setCloseOnOutsideClick(false);
        
        // Create the title text box
        titleField = new TextField("Event Title:");
		titleField.addValueChangeListener(event -> { titleValue = event.getValue(); });
        
        // Create the location text field
		locField = new TextField("Location:");
		locField.addValueChangeListener(event -> { locationValue = event.getValue(); });
		
		// Create the description text field
		descField = new TextField("Description:");
		descField.addValueChangeListener(event -> { descValue = event.getValue();});
		
		// Create the URL link text field
		urlField = new TextField("Link:");
		urlField.addValueChangeListener(event -> { urlLink = event.getValue();});
		
		// Create the date picker
		dp = new DatePicker("Choose a date:");
		dp.addValueChangeListener(event -> {
			LocalDate dateIn = event.getValue();
			if(dateIn != null) {
				DateTimeFormatter f = DateTimeFormatter.BASIC_ISO_DATE;
				String formattedDateIn = dateIn.format(f);
				String formattedYear = new String(formattedDateIn.substring(0,4));
				String formattedMonth = new String(formattedDateIn.substring(4,6));
				String formattedDay = new String(formattedDateIn.substring(6,8));
				yearValue = Integer.parseInt(formattedYear);
				monthValue = Integer.parseInt(formattedMonth);
				dayValue = Integer.parseInt(formattedDay);
			}
		});
		
		// Create the hour combo box (Part 1 of time)
		tp = new TimePicker("Choose a time:");
		tp.addValueChangeListener(event -> {
			if(event.getValue()!= null) {
				hourValue = event.getValue().getHour();
				minuteValue = event.getValue().getMinute();
			}
		});
		
		// Create the save button
		saveEvent.addClickListener(event ->  {
			// If a required field was not filled
        	if(titleField.isEmpty() || locField.isEmpty() || descField.isEmpty() || dp.isEmpty() ) {
        		// Show the error messages
        		addEventDialogVLay.add(errorMessage);
        		titleField.setLabel("Event Title: *");
        		locField.setLabel("Location: *");
        		descField.setLabel("Description: *");
        		dp.setLabel("Choose a date: *");
        		tp.setLabel("Choose a time: *");
        	} else {
        		// Create a new Events object
        		Events newE = new Events(titleValue, locationValue, descValue, urlLink,
        							dayValue, monthValue, yearValue, hourValue, minuteValue);
        		// Create the new Events as an Entry
        		Entry ne = new Entry();
        		ne.setTitle(titleValue);
        		if(urlLink== null) {
        			ne.setDescription(locationValue + ", " + descValue);
        		} else {
        			ne.setDescription(locationValue + ", " + descValue + 
            				" For more information, check out " + urlLink);
        		}
        		ne.setStart(LocalDate.of(yearValue, monthValue, dayValue).atTime(hourValue, minuteValue));
        		ne.setEnd(ne.getStart().plusHours(1));
        		ne.setEditable(false);
        		// Add the new Event to the events list, and to the calendar
        		sqlEE.insertEvent(titleValue, locationValue, descValue, urlLink, Integer.toString(dayValue), 
        						  Integer.toString(monthValue), Integer.toString(yearValue), 
        						  Integer.toString(hourValue), Integer.toString(minuteValue));
        		sqlEE.loadAll();
                eventsList = sqlEE.getAllEvents();
                displayEvents();
        		// Clear all of the fields
        		clearFields();

        		// Reset the error messages
        		addEventDialogVLay.remove(errorMessage);
        		titleField.setLabel("Event Title:");
        		locField.setLabel("Location:");
        		descField.setLabel("Description:");
        		dp.setLabel("Choose a date:");
        		tp.setLabel("Choose a time:");
        	
    			// Close the Dialog
    			newEventDialog.close();
        	}
		});
		saveEvent.addClassName("view-toolbar__button");
		
		// Create the close button
		closeD.addClickListener(event ->  {
			// If text fields were filled in
			if(titleField.isEmpty()==false || locField.isEmpty()==false || descField.isEmpty()==false || 
					urlField.isEmpty()==false ) {
				// clear them
				clearFields();
				// Reset variable values
	        	titleValue = null;
	        	locationValue = null;
	        	descValue = null;
	        	urlLink = null;
	        	dayValue = 0;
	        	monthValue = 0; 
	        	yearValue = 0;
	        	hourValue = 0; 
	        	minuteValue = 0;
			}
        	// Remove the error messages 
			titleField.setLabel("Event Title:");
    		locField.setLabel("Location:");
    		descField.setLabel("Description:");
    		dp.setLabel("Choose a date:");
    		tp.setLabel("Choose a time:");
    		// Close the Dialog
    		newEventDialog.close();
		});
		closeD.addClassName("view-toolbar__button");
		
		// Add all of these elements to a Vertical Layout, add that to the Dialog
		addEventDialogVLay = new VerticalLayout();
		addEventDialogVLay.add(titleField, locField, descField, urlField, dp, tp, ButtonsLay);
		newEventDialog.add(addEventDialogVLay);
		
		// Open the Dialog
		newEventDialog.open();
	}
	
	private void clearFields() {
		titleField.clear();
		locField.clear();
		descField.clear();
		urlField.clear();
		dp.clear();
		tp.clear();
	}
	
	private void displayEvents() {
		calendar.removeAllEntries();
		for(int i = 0; i < eventsList.size(); i++) {
			Entry newEntry = new Entry();
			newEntry.setTitle(eventsList.get(i).getTitle());
			if(eventsList.get(i).getUrl()==null) {
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
			calendar.addEntry(newEntry);
		}
	}
	
	private void initView() {
        addClassName("events");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }
	
	private void monthLabelSetUp() {
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
	}
	
	private void displayCalendar() {
		
	    calendar.setHeight(500);
	    calendar.addClassName("calendar-color");
		setFlexGrow(1, calendar);
		
		// Get the current date using LocalDate
		LocalDate x = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		String formattedDate = x.format(formatter);
		currentMonth = new Label("");
		currentMonth.addClassName("view-toolbar__event-title");
		currentYear = new String(formattedDate.substring(0, 4));
		yearNum = Integer.parseInt(currentYear);
		
		//Set up the page's main Label
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
		
		// Button to return to the current month
		today = new Button("Today", event -> { 
			calendar.today();
			monthNumber = cMN;
			monthLabelSetUp();
		});
		today.addClassName("view-toolbar__event-today");
		
		// Button to move back into past months
        lastMonth = new Button(new Icon(VaadinIcon.ANGLE_LEFT), event -> {
        	calendar.previous();
        	if(monthNumber == 1) { yearNum--; monthNumber = 12; } else { monthNumber--;}
        	monthLabelSetUp();
        });
        lastMonth.addClassName("view-toolbar__event-click");
        
        // Button to move forward into future months
        nextMonth = new Button(new Icon(VaadinIcon.ANGLE_RIGHT), event -> {
        	calendar.next();
        	if(monthNumber == 12) { yearNum++; monthNumber = 1; } else { monthNumber++;}
        	monthLabelSetUp();
        });
        nextMonth.addClassName("view-toolbar__event-click");
        

        // Create the add Events button
        addEventButton = new Button("Add an Event", new Icon("lumo", "plus"),  event ->  {
        	try {
        		openAddEvent();
        	} catch(Exception e) { e.printStackTrace(); }  }       
        );
        addEventButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Create the Horizontal Layout for the buttons, and add them
        monthMoveLayout = new HorizontalLayout();
        monthMoveLayout.add(today, lastMonth, nextMonth, addEventButton);

		//Add all of the components to the page
        lay.add(currentMonth, monthMoveLayout, calendar);
        add(lay);
	}
}