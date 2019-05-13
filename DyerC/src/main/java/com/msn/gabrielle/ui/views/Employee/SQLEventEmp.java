package com.msn.gabrielle.ui.views.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import com.msn.gabrielle.backend.Events;

public class SQLEventEmp {

	public ArrayList<Events> listAllEvents = new ArrayList<Events>();
	public ArrayList<Events> listMonthEvents = new ArrayList<Events>();
	
	public SQLEventEmp() {}
	
	public ArrayList<Events> getAllEvents() {
		loadAll();
		return listAllEvents;
	}
	public void loadAll() {
		
		listAllEvents.clear();
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Loading All Events from TABLE_EVENTS_MASTER!");
			Statement statementSelectMonth = c.createStatement();
			String sqlSelectMonth = "SELECT * FROM TABLE_EVENTS_MASTER;";

			ResultSet rsMonth = statementSelectMonth.executeQuery(sqlSelectMonth);

			while(rsMonth.next()) {
				String mID			=	rsMonth.getString(1);
				String mTitle		=	rsMonth.getString(2);
				String mLocation	=	rsMonth.getString(3);
				String mDesc 		=	rsMonth.getString(4);
				String mURL 		=	rsMonth.getString(5);
				String mDay  		=	rsMonth.getString(6);
				String mMonth  		=	rsMonth.getString(7);
				String mYear  		= 	rsMonth.getString(8);
				String mHour  		= 	rsMonth.getString(9);
				String mMinute  	= 	rsMonth.getString(10);
				System.out.println(mID + " " + mTitle + " " + mLocation + " " + mDesc + " " + mURL + " " +
								   mMonth + " " + mYear + " " + mHour + " " + mMinute);
				Events ev = new Events(mTitle, mLocation, mDesc, mURL, Integer.parseInt(mDay), 
									   Integer.parseInt(mMonth), Integer.parseInt(mYear), 
									   Integer.parseInt(mHour), Integer.parseInt(mMinute));
				listAllEvents.add(ev);
			}
			statementSelectMonth.close();
			c.close();
			System.out.println("Successful Load Month from TABLE_EVENTS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}

	public void loadMonth(int monthNo) {
		
		try {
			listMonthEvents.clear();
			
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Loading Month from TABLE_EVENTS_MASTER");
			Statement statementSelectMonth = c.createStatement();
			String sqlSelectMonth = "SELECT * FROM TABLE_EVENTS_MASTER WHERE MONTH ='" + monthNo + "';";

			ResultSet rsMonth = statementSelectMonth.executeQuery(sqlSelectMonth);

			while(rsMonth.next()) {
				String mID			=	rsMonth.getString(1);
				String mTitle		=	rsMonth.getString(2);
				String mLocation	=	rsMonth.getString(3);
				String mDesc 		=	rsMonth.getString(4);
				String mURL 		=	rsMonth.getString(5);
				String mDay  		=	rsMonth.getString(6);
				String mMonth  		=	rsMonth.getString(7);
				String mYear  		= 	rsMonth.getString(8);
				String mHour  		= 	rsMonth.getString(9);
				String mMinute  	= 	rsMonth.getString(10);
				System.out.println(mID + " " + mTitle + " " + mLocation + " " + mDesc + " " + mURL + " " +
								   mMonth + " " + mYear + " " + mHour + " " + mMinute);
				Events ev = new Events(mTitle, mLocation, mDesc, mURL, Integer.parseInt(mDay), 
						   Integer.parseInt(mMonth), Integer.parseInt(mYear), 
						   Integer.parseInt(mHour), Integer.parseInt(mMinute));
				listMonthEvents.add(ev);
			}
			statementSelectMonth.close();
			c.close();
			System.out.println("Successful Load Month from TABLE_EVENTS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}

	public void insertEvent(String mTitle, String mLocation, String mDesc, String mURL,
							String mDay, String mMonth, String mYear, String mHour, String mMinute ) {
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Inserting Event into database");

			Statement statementCount = c.createStatement();
			String sqlCount = "SELECT MAX(EVENT_ID) FROM TABLE_EVENTS_MASTER;";
			ResultSet rsCount = statementCount.executeQuery(sqlCount);
			rsCount.next();
			int idNumber = rsCount.getInt(1) + 1;
			statementCount.close();
			Statement statementInsertEvent = c.createStatement();
			
			String sqlInsertEvent = "INSERT INTO TABLE_EVENTS_MASTER "
					+ "(EVENT_ID,TITLE,LOCATION,DESCRIPTION,URL,DAY,MONTH,YEAR,HOUR,MINUTE) VALUES "
					+ "(" + idNumber + ", '" + mTitle + "', '" + mLocation 
					+ "', '" + mDesc + "', '" + mURL + "', '" + mDay + "', '" + mMonth 
					+ "', '" + mYear + "', '" + mHour + "', '" + mMinute + "');";
			statementInsertEvent.executeUpdate(sqlInsertEvent);
			statementInsertEvent.close();
			c.close();
			System.out.println("Successful Insertion to TABLE_EVENTS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	public void deleteEvent(String title, String location, String desc, String url,
							 String day, String month, String year, String hour, String min) {
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Deleting Event from TABLE_EVENTS_MASTER");

			Statement statementDeleteEvent = c.createStatement();
			
			String sqlDeleteEvent = "DELETE FROM TABLE_EVENTS_MASTER WHERE TITLE = '" + title +
									  "' AND LOCATION = '" + location + "' AND DESCRIPTION = '" + desc +
									  "' AND URL = '" + url + "' AND DAY = '" + day +
									  "' AND MONTH = '" + month + "' AND YEAR = '" + year +
									  "' AND HOUR = '" + hour + "' AND MIN = '" + min +"';";
			statementDeleteEvent.executeUpdate(sqlDeleteEvent);
			statementDeleteEvent.close();
			c.close();
			System.out.println("Successful Deletion from TABLE_EVENTS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
}
