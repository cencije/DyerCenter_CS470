package com.msn.gabrielle.ui.views.Employee;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.msn.gabrielle.backend.Events;

public class SQLEventEmp {

	public ArrayList<Events> listAllEvents = new ArrayList<Events>();
	public ArrayList<Events> listMonthEvents = new ArrayList<Events>();
	
	/**
	 * Constructor for the SQLEventEmp class
	 */
	public SQLEventEmp() {}
	
	/**
	 * Generates the list of all events by calling loadAll() and returns that list
	 * @return The list of events in TABLE_EVENTS_MASTER.
	 */
	public ArrayList<Events> getAllEvents() {
		loadAll();
		return listAllEvents;
	}
	
	/**
	 * Loads the list of all events found in TABLE_EVENTS_MASTER.
	 * Adds them to the list object listAllEvents.
	 */
	public void loadAll() {
		
		listAllEvents.clear();
		Properties prop = new Properties();
		String propFileName = "config_DB.properties";
		try {
			Class.forName("org.postgresql.Driver");
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					 								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
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

	/**
	 * Loads the events associated with the passed month value.
	 * @param monthNo The number of the current displayed month so that the database can load them all.
	 */
	public void loadMonth(int monthNo) {
		
		Properties prop = new Properties();
		String propFileName = "config_DB.properties";
		try {
			listMonthEvents.clear();
			
			Class.forName("org.postgresql.Driver");
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					 								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
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

	/**
	 * Inserts an event into TABLE_EVENTS_MASTER based on the passed values.
	 * @param mTitle Title of the event to be inserted.
	 * @param mLocation Location of the event to be inserted.
	 * @param mDesc Description of the event to be inserted.
	 * @param mURL URL of the event to be inserted.
	 * @param mDay Day of the event to be inserted.
	 * @param mMonth Month of the event to be inserted.
	 * @param mYear Year of the event to be inserted.
	 * @param mHour Hour of the event to be inserted.
	 * @param mMinute Minute of the event to be inserted.
	 */
	public void insertEvent(String mTitle, String mLocation, String mDesc, String mURL,
							String mDay, String mMonth, String mYear, String mHour, String mMinute ) {
		Properties prop = new Properties();
		String propFileName = "config_DB.properties";
		try {
			Class.forName("org.postgresql.Driver");
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					 								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
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
	
	/**
	 * Deletes an event from TABLE_EVENTS_MASTER based on the passed values.
	 * @param title Title of the event to be deleted.
	 * @param location Location of the event to be deleted.
	 * @param desc Description of the event to be deleted.
	 * @param url URL of the event to be deleted.
	 * @param day Day of the event to be deleted.
	 * @param month Month of the event to be deleted.
	 * @param year Year of the event to be deleted.
	 * @param hour Hour of the event to be deleted.
	 * @param min Minute of the event to be deleted.
	 */
	public void deleteEvent(String title, String location, String desc, String url,
							 String day, String month, String year, String hour, String min) {
		Properties prop = new Properties();
		String propFileName = "config_DB.properties";
		try {
			Class.forName("org.postgresql.Driver");
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					 								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
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
