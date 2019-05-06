package com.msn.gabrielle.ui.views.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import com.msn.gabrielle.backend.Events;

public class SQLEventStudent {

	public ArrayList<Events> listAllEvents = new ArrayList<Events>();
	public ArrayList<Events> listMonthEvents = new ArrayList<Events>();
	
	public SQLEventStudent() {}
	
	public void loadAll() {
		
		listAllEvents.clear();
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Adding profile to database!");
			Statement statementSelectMonth = c.createStatement();
			String sqlSelectMonth = "SELECT * FROM TABLE_EVENTS_MASTER;";

			ResultSet rsMonth = statementSelectMonth.executeQuery(sqlSelectMonth);
			//ResultSetMetaData rsmdMonth = rsMonth.getMetaData();

			//int cols = rsmdMonth.getColumnCount();
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
			System.out.println("Adding profile to database!");
			Statement statementSelectMonth = c.createStatement();
			String sqlSelectMonth = "SELECT * FROM TABLE_EVENTS_MASTER WHERE MONTH ='" + monthNo + "';";

			ResultSet rsMonth = statementSelectMonth.executeQuery(sqlSelectMonth);
			//ResultSetMetaData rsmdMonth = rsMonth.getMetaData();

			//int cols = rsmdMonth.getColumnCount();
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
	
	
}
