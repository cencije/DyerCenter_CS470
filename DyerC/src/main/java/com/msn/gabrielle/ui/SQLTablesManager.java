package com.msn.gabrielle.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLTablesManager {

	public SQLTablesManager() { }
	
	public void determineDBStates() {
		
		createProfileTables();
		dropProfileTables(true, false, false);
	}
	
	public boolean createProfileTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
			System.out.println("Adding project to database!");
			Statement statementCreate_TPS = c.createStatement();
			String stringCreateTPS = "CREATE TABLE IF NOT EXISTS TABLE_PROFILE_STUDENTS" +
			"(ID INT PRIMARY KEY     NOT NULL, " +
			" NAME           TEXT    NOT NULL, " +
			" EMAIL          TEXT    NOT NULL, " +
			" PASSWORD       TEXT     		 , " +
			" PHONENO        TEXT     		 , " +
			" MAJOR1         TEXT     		 , " +
			" MAJOR2         TEXT     		 , " +
			" MINOR1         TEXT     		 , " +
			" MINOR2         TEXT     		 ) ";
			statementCreate_TPS.executeUpdate(stringCreateTPS);
			statementCreate_TPS.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
			System.out.println("Adding project to database!");
			Statement statementCreate_TPS = c.createStatement();
			String stringCreateTPA = "CREATE TABLE IF NOT EXISTS TABLE_PROFILE_ALUMNI" +
			"(ID INT PRIMARY KEY     NOT NULL, " +
			" NAME           TEXT    NOT NULL, " +
			" EMAIL          TEXT    NOT NULL, " +
			" PASSWORD       TEXT     		 , " +
			" PHONENO        TEXT     		 ) ";
			statementCreate_TPS.executeUpdate(stringCreateTPA);
			statementCreate_TPS.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_PROFILE_ALUMNI");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
			System.out.println("Adding project to database!");
			Statement statementCreate_TPE = c.createStatement();
			String stringCreateTPE = "CREATE TABLE IF NOT EXISTS TABLE_PROFILE_EMPLOYEE" +
			"(ID INT PRIMARY KEY     NOT NULL, " +
			" NAME           TEXT    NOT NULL, " +
			" EMAIL          TEXT    NOT NULL, " +
			" PASSWORD       TEXT     		 , " +
			" PHONENO        TEXT     		 ) ";
			statementCreate_TPE.executeUpdate(stringCreateTPE);
			statementCreate_TPE.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_PROFILE_EMPLOYEE");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		return true;
	}
	public void dropProfileTables(boolean boolStudent, boolean boolAlumni, boolean boolEmployee) {
		
		if (boolStudent) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
															"postgres", "PostgresMall");
				System.out.println("Dropping Table TABLE_PROFILE_STUDENT!");
				Statement statementDrop_TPS = c.createStatement();
				String stringDropTPS = "DROP TABLE IF EXISTS TABLE_PROFILE_STUDENT;";
				statementDrop_TPS.executeUpdate(stringDropTPS);
				statementDrop_TPS.close();
				c.close();
				System.out.println("Successful Drop of TABLE_PROFILE_STUDENT");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("FAILED: Drop of TABLE_PROFILE_STUDENT");
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
		}
		if (boolAlumni) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
															"postgres", "PostgresMall");
				System.out.println("Dropping Table IF EXISTS TABLE_PROFILE_ALUMNI!");
				Statement statementDrop_TPA = c.createStatement();
				String stringDropTPA = "DROP TABLE TABLE_PROFILE_ALUMNI;";
				statementDrop_TPA.executeUpdate(stringDropTPA);
				statementDrop_TPA.close();
				c.close();
				System.out.println("Successful Drop of TABLE_PROFILE_ALUMNI");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("FAILED: Drop of TABLE_PROFILE_ALUMNI");
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
		}
		if (boolEmployee) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
															"postgres", "PostgresMall");
				System.out.println("Dropping Table TABLE_PROFILE_EMPLOYEE!");
				Statement statementDrop_TPE = c.createStatement();
				String stringDropTPE = "DROP TABLE IF EXISTS TABLE_PROFILE_EMPLOYEE;";
				statementDrop_TPE.executeUpdate(stringDropTPE);
				statementDrop_TPE.close();
				c.close();
				System.out.println("Successful Drop of TABLE_PROFILE_EMPLOYEE");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("FAILED: Drop of TABLE_PROFILE_EMPLOYEE");
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
		}
	}
	
}
