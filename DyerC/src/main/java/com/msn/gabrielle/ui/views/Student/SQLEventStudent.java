package com.msn.gabrielle.ui.views.Student;

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

public class SQLEventStudent {

	public ArrayList<Events> listAllEvents = new ArrayList<Events>();
	public ArrayList<Events> listMonthEvents = new ArrayList<Events>();
	
	/**
	 * Constructor for the SQLEventStudent class
	 */
	public SQLEventStudent() {}
	
	
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
		Properties prop = new Properties();
		String propFileName = "config_DB.properties";
		listAllEvents.clear();
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
			System.out.println("Adding profile to database!");
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
			System.out.println("Adding profile to database!");
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
	
	
}
