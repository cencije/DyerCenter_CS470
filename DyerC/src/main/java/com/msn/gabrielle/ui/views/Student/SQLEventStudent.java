package com.msn.gabrielle.ui.views.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class SQLEventStudent {

	public SQLEventStudent() {}
	
	public void loadMonth(int monthNo) {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Adding profile to database!");
			Statement statementSelectMonth = c.createStatement();
			String sqlSelectMonth = "SELECT * FROM TABLE_EVENTS_MASTER WHERE MONTH ='" + monthNo + "';";

			ResultSet rsMonth = statementSelectMonth.executeQuery(sqlSelectMonth);
			ResultSetMetaData rsmdMonth = rsMonth.getMetaData();

			int cols = rsmdMonth.getColumnCount();
			while(rsMonth.next()) {
				String mID			=	rsMonth.getString(1);
				String mTitle		=	rsMonth.getString(2);
				String mLocation	=	rsMonth.getString(3);
				String mDesc 		=	rsMonth.getString(4);
				String mURL 		=	rsMonth.getString(5);
				String mMonth  		=	rsMonth.getString(6);
				String mYear  		= 	rsMonth.getString(7);
				String mHour  		= 	rsMonth.getString(8);
				String mMinute  	= 	rsMonth.getString(9);
				System.out.println(mID + " " + mTitle + " " + mLocation + " " + mDesc + " " + mURL + " " +
								   mMonth + " " + mYear + " " + mHour + " " + mMinute);

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
	
	public void insertEvent() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Inserting Skill into database");

			Statement statementCount = c.createStatement();
			String sqlCount = "SELECT MAX(SKILL_ID) FROM TABLE_SKILLS_MASTER;";
			ResultSet rsCount = statementCount.executeQuery(sqlCount);
			rsCount.next();
			int idNumber = rsCount.getInt(1) + 1;
			statementCount.close();
		/*
				"(EVENT_ID INT PRIMARY KEY NOT NULL,   " +
				" TITLE         TEXT       NOT NULL,   " +
				" LOCATION      TEXT       NOT NULL,   " +
				" DESCRIPTION   TEXT   			   ,   " +
				" URL           TEXT    		   ,   " +
				" DAY           INTEGER    NOT NULL,   " +
				" MONTH         INTEGER    NOT NULL,   " +
				" YEAR          INTEGER    NOT NULL,   " +
				" HOUR          INTEGER    NOT NULL,   " +
				" MINUTE        INTEGER    NOT NULL);  ";
		*/
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
}
