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

//import org.vaadin.stefan.fullcalendar.CalendarView;
//import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
//import com.vaadin.flow.shared.Registration;

import com.msn.gabrielle.ui.*;
import com.msn.gabrielle.backend.Events;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.HasText;
//import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.textfield.*;

@Route(value = "eventsemp", layout = EmployeePage.class)
@PageTitle("Events")
@HtmlImport("frontend://styles/calendar-style.html")
public class EventsEmp extends VerticalLayout{
	
	private Button addEventButton, saveEvent, closeD;
	VerticalLayout lay, entireBox;
	HorizontalLayout hlay, titleLayout, locationLayout, dateLayout,
			timeLayout, descriptionLayout, ButtonsLay;
	FullCalendar calendar = FullCalendarBuilder.create().build();
	String titleValue, locationValue, datimeValue, descValue, urlLink;
	int monthValue, dayValue, hourValue, minuteValue, 
		monthNumber, cMN, yearValue, yearNum;
	public ArrayList<Events> eventsList = new ArrayList<Events>();

	public EventsEmp() {
        initView();
        
        //SQLEventEmp sqlEE = new SQLEventEmp();
        //sqlEE.loadAll();
        //eventsList = sqlEE.getAllEvents();
        
        lay = new VerticalLayout();
        hlay = new HorizontalLayout();
        ButtonsLay = new HorizontalLayout();
        
        titleLayout = new HorizontalLayout();
        locationLayout = new HorizontalLayout();
        dateLayout = new HorizontalLayout();
        timeLayout = new HorizontalLayout();
        descriptionLayout = new HorizontalLayout();
        entireBox = new VerticalLayout();
        
        Label addTitleLbl = new Label("Event Title:");
		Label addLocLbl = new Label("Location:");
		Label addDescLbl = new Label("Description:");
        
		TextField titleField = new TextField();
		titleField.addValueChangeListener(event -> {
			titleValue = event.getValue();
		});
		titleLayout.add(addTitleLbl, titleField);
		
		TextField locField = new TextField();
		locField.addValueChangeListener(event -> {
			locationValue = event.getValue();
		});
		locationLayout.add(addLocLbl, locField);
		
		Label monthLbl = new Label("Month:");
		ComboBox<Integer> month = new ComboBox<Integer>();
		month.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		
		ComboBox<Integer> dayCBox = new ComboBox<Integer>();
		dayCBox.setPlaceholder("Choose month first");
		ArrayList<Integer> longMonths = new ArrayList<Integer>(31);
		ArrayList<Integer> midMonths = new ArrayList<Integer>(30);
		ArrayList<Integer> febLength = new ArrayList<Integer>(28);
		int dd = 1;
		while (dd < 32) {
			if(dd <= 28) { febLength.add(dd); }
			if(dd <= 30) { midMonths.add(dd); }
			longMonths.add(dd);
			dd++;
		}
		System.out.println("LONGMONTHS: " + longMonths);
		System.out.println("MID_MONTHS: " + midMonths);
		System.out.println("FEBRUARY: " + febLength);
		
		Label yearLbl = new Label("Year:");
		ComboBox<Integer> yearCBox = new ComboBox<Integer>();
		yearCBox.setItems(2019, 2020, 2021, 2022, 2023);
		yearCBox.addValueChangeListener(event -> { yearValue = yearCBox.getValue(); });
		
		Label dateLbl = new Label("Day:");
		
		Label hourLbl = new Label("Hour:   ");
		ComboBox<Integer> hour = new ComboBox<Integer>();
		hour.setItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 
				12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23);
		
		Label minLbl = new Label("Minute:");
		ComboBox<Integer> min = new ComboBox<Integer>();
		min.setItems(05, 10, 15, 20, 25, 30, 35, 45, 50, 55);
		
		month.addValueChangeListener(event -> { 
			monthValue = event.getValue();

			if(monthValue==1 || monthValue==3 || monthValue==5 ||
					monthValue==7 || monthValue==8 || monthValue==10
					|| monthValue ==12) { dayCBox.setItems(longMonths);}
			else if(monthValue==2 ) { dayCBox.setItems(febLength);}
			else { dayCBox.setItems(midMonths);}	
		});
		
		
		dayCBox.addValueChangeListener(event -> { dayValue = event.getValue();});
		
		hour.addValueChangeListener(event -> { hourValue = event.getValue();});
		
		min.addValueChangeListener(event -> { minuteValue = event.getValue();});
		
		TextField descField = new TextField();
		descField.addValueChangeListener(event -> { descValue = event.getValue();});
		
		Label urlLbl = new Label("Link: ");
		TextField urlField = new TextField();
		urlField.addValueChangeListener(event -> {
			urlLink = event.getValue();
		});
		HorizontalLayout urlLayout = new HorizontalLayout();
		urlLayout.add(urlLbl, urlField);
		
		descriptionLayout.add(addDescLbl, descField);
		
		dateLayout.add(monthLbl, month, dateLbl, dayCBox, yearLbl, yearCBox);
		timeLayout.add(hourLbl, hour, minLbl, min);
		
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        
        saveEvent = new Button("Save Event", event ->  {
        	Events newE = new Events(titleValue, locationValue, descValue, urlLink,
        							dayValue, monthValue, yearValue, hourValue, minuteValue);
        	
//        	sqlEE.insertEvent(titleValue, locationValue, descValue, urlLink, 
//        					  Integer.toString(dayValue), Integer.toString(monthValue), Integer.toString(yearValue),
//        					  Integer.toString(hourValue), Integer.toString(minuteValue));
        	Entry ne = new Entry();
        	ne.setTitle(titleValue);
        	ne.setDescription(locationValue + ", " + descValue + 
					" For more information, check out " + urlLink);
        	ne.setStart(LocalDate.of(yearValue, monthValue, dayValue).atTime(hourValue, minuteValue));
        	ne.setEnd(ne.getStart().plusHours(1));
        	//eventsList.add(newE);
        	//eventsList = sqlEE.getAllEvents();
        	calendar.removeAllEntries();
        	displayEvents();
        	
        	titleValue = null;
        	locationValue = null;
        	descValue = null;
        	urlLink = null;
        	/*titleField.clear();
        	locField.clear();
        	descField.clear();
        	urlField.clear();
        	month.clear();
        	dayCBox.clear();
        	yearCBox.clear();
        	hour.clear();
        	min.clear();*/
        	
        	dialog.close();
        	
//        	Dialog d = new Dialog();
//        	VerticalLayout eventClickedVLay = new VerticalLayout();
//        	Label titleLabel = new Label(titleValue);
//        	Label locLabel = new Label(locationValue);
//        	HorizontalLayout timeHLay = new HorizontalLayout();
//        	Label timeLabel = new Label();
//        	Label minLabel = new Label();
//        	timeHLay.add(timeLabel, minLabel);
//        	Label desLabel = new Label(descValue);
//        	eventClickedVLay.add(titleLabel, locLabel, timeHLay, desLabel);
//        	calendar.addEntryClickedListener(EntryClickedEvent -> {
//        		d.add(eventClickedVLay);
//        		add(d);
//        		d.open();
//        	});
        });
        saveEvent.addClassName("view-toolbar__button");
        
		closeD = new Button("Close", event ->  {
			titleValue = null;
        	locationValue = null;
        	descValue = null;
        	urlLink = null;
        	titleField.clear();
        	locField.clear();
        	descField.clear();
        	urlField.clear();
        	month.clear();
        	dayCBox.clear();
        	yearCBox.clear();
        	hour.clear();
        	min.clear();
        	dialog.close();
		});
		closeD.addClassName("view-toolbar__button");
		
		ButtonsLay.add(saveEvent, closeD);
		
		entireBox.add(titleLayout, locationLayout, descriptionLayout, 
				urlLayout, dateLayout, timeLayout, ButtonsLay);
        
        dialog.add(entireBox);
        
        addEventButton = new Button("Add an Event", new Icon("lumo", "plus"),  event ->  {
        	try {
        		dialog.open();
        	} catch(Exception e) { e.printStackTrace(); }  }       
        );
        addEventButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addEventButton.addClassName("view-toolbar__event-click");
        displayCalendar();
        displayEvents();
    }
	
	private void displayEvents() {
		for(int i = 0; i < eventsList.size(); i++) {
			Entry newEntry = new Entry();
			newEntry.setTitle(eventsList.get(i).getTitle());
			newEntry.setDescription(eventsList.get(i).getLocation() + ", " +
					eventsList.get(i).getDescription() + 
					" For more information, check out " + 
					eventsList.get(i).getUrl());
			newEntry.setStart(LocalDate.of(eventsList.get(i).getYear(), eventsList.get(i).getMonth(), 
								eventsList.get(i).getDay()).atTime(eventsList.get(i).getHour(), 
								eventsList.get(i).getMinute()));
			newEntry.setEnd(newEntry.getStart().plusHours(1));
			calendar.addEntry(newEntry);
		}
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
		currentMonth.addClassName("view-toolbar__event-title");
		String currentYear = new String(formattedDate.substring(0, 4));
		yearNum = Integer.parseInt(currentYear);
		
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
	    	
		//calendar.addEntry(entry);
		HasText intervalLabel = new Span();
	    calendar.setHeight(500);
	    calendar.addClassName("calendar-color");
	    
		setFlexGrow(1, calendar);
		
		Button today = new Button("Today", event -> { 
			calendar.today();
			monthNumber = cMN;
			
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
		});
		today.addClassName("view-toolbar__event-today");
		
        Button lastMonth = new Button(new Icon(VaadinIcon.ANGLE_LEFT), event -> {
        	calendar.previous();
        	if(monthNumber == 1) { yearNum--; monthNumber = 12; } else { monthNumber--;}
        	
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
        });
        lastMonth.addClassName("view-toolbar__event-click");
        
        Button nextMonth = new Button(new Icon(VaadinIcon.ANGLE_RIGHT), event -> {
        	calendar.next();
        	if(monthNumber == 12) { yearNum++; monthNumber = 1; } else { monthNumber++;}
        	
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
        });
        nextMonth.addClassName("view-toolbar__event-click");
        
        HorizontalLayout monthMoveLayout = new HorizontalLayout();
        monthMoveLayout.add(today, lastMonth, nextMonth, addEventButton);

		//Add all of the components to the page
        lay.add(currentMonth, monthMoveLayout, calendar);
        add(lay);
        
        calendar.addDayNumberClickedListener(event -> {
        	Dialog d = new Dialog();
        	d.setCloseOnEsc(false);
            d.setCloseOnOutsideClick(false);
            Label sup = new Label("sup");
            d.add(sup);
        });
        
        
        //Event n = new Event();
     		Entry sampleEntry = new Entry();
     		sampleEntry.setTitle("Some event");
     		sampleEntry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 30));
     		sampleEntry.setEnd(sampleEntry.getStart().plusHours(2));
     		sampleEntry.setColor("#ff3333");
     		//calendar.addEntry(sampleEntry);
	}
}