package com.msn.gabrielle.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.msn.gabrielle.ui.views.Student.SQLProfileStud;
import com.msn.gabrielle.ui.views.Student.SQLProjectStud;
import com.msn.gabrielle.ui.views.Student.SkillsLoader;

public class SQLTablesManager {


	/**
	 * Constructor for the SQLTablesManager class.
	 */
	public SQLTablesManager() { }
	
	
	/**
	 * Used to create the SQL Tables if they do not exist. 
	 * Otherwise the tables existence will be checked and nothing else will be done.
	 */
	public void determineDBStates() {
		createProfileTables();
		createSkillTables();
		createEventsTables();
		createProjectTables();
		//SQLProfileStud sqlPS = new SQLProfileStud();
		//sqlPS.insertNewProfile("fqfgqg", "email", "password", "phoneNo", null, "m2", null, "min2");
		//sqlPS.getProfileValues("lol");
		//sqlPS.updateName("HAHA", "lol");
	
		// Code to load in the Skills List
		//SkillsLoader sl = new SkillsLoader();
		//sl.loadInJSONValues();
		 
	}
	
	/**
	 * Creates the tables associated with profiles.
	 * TABLE_PROFILE_STUDENTS, TABLE_PROFILE_ALUMNI, TABLE_PROFILE_EMPLOYEE.
	 */
	public void createProfileTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_PROFILE_STUDENTS");
			Statement statementCreate_TPS = c.createStatement();
			String stringCreateTPS = "CREATE TABLE IF NOT EXISTS TABLE_PROFILE_STUDENTS" +
			"(ID INT PRIMARY KEY     NOT NULL, " +
			" NAME           TEXT    NOT NULL, " +
			" EMAIL          TEXT    NOT NULL, " +
			" PASSWORD       TEXT    NOT NULL, " +
			" PHONENO        TEXT     		 , " +
			" MAJOR1         TEXT     		 , " +
			" MAJOR2         TEXT     		 , " +
			" MINOR1         TEXT     		 , " +
			" MINOR2         TEXT     		 ); ";
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
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_PROFILE_ALUMNI");
			Statement statementCreate_TPS = c.createStatement();
			String stringCreateTPA = "CREATE TABLE IF NOT EXISTS TABLE_PROFILE_ALUMNI" +
			"(ID INT PRIMARY KEY     NOT NULL, " +
			" NAME           TEXT    NOT NULL, " +
			" EMAIL          TEXT    NOT NULL, " +
			" PASSWORD       TEXT    NOT NULL, " +
			" PHONENO        TEXT     		 ); ";
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
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_PROFILE_EMPLOYEE");
			Statement statementCreate_TPE = c.createStatement();
			String stringCreateTPE = "CREATE TABLE IF NOT EXISTS TABLE_PROFILE_EMPLOYEE" +
			"(ID INT PRIMARY KEY     NOT NULL, " +
			" NAME           TEXT    NOT NULL, " +
			" EMAIL          TEXT    NOT NULL, " +
			" PASSWORD       TEXT    NOT NULL, " +
			" PHONENO        TEXT     		 ); ";
			statementCreate_TPE.executeUpdate(stringCreateTPE);
			statementCreate_TPE.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_PROFILE_EMPLOYEE");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
	}
	
	/**
	 * Drops the tables associated with profiles.
	 * TABLE_PROFILE_STUDENTS, TABLE_PROFILE_ALUMNI, TABLE_PROFILE_EMPLOYEE.
	 *
	 * @param boolStudent If TABLE_PROFILE_STUDENTS should be dropped.
	 * @param boolAlumni If TABLE_PROFILE_ALUMNI should be dropped.
	 * @param boolEmployee If TABLE_PROFILE_EMPLOYEE should be dropped.
	 */
	public void dropProfileTables(boolean boolStudent, boolean boolAlumni, boolean boolEmployee) {
		
		if (boolStudent) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
				System.out.println("Dropping Table TABLE_PROFILE_STUDENT");
				Statement statementDrop_TPS = c.createStatement();
				String stringDropTPS = "DROP TABLE IF EXISTS TABLE_PROFILE_STUDENTS;";
				statementDrop_TPS.executeUpdate(stringDropTPS);
				statementDrop_TPS.close();
				c.close();
				System.out.println("Successful Drop of TABLE_PROFILE_STUDENTS");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("FAILED: Drop of TABLE_PROFILE_STUDENTS");
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
		}
		if (boolAlumni) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
				System.out.println("Dropping Table IF EXISTS TABLE_PROFILE_ALUMNI");
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
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
				System.out.println("Dropping Table TABLE_PROFILE_EMPLOYEE");
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
	
	/**
	 * Creates the tables associated with events.
	 * TABLE_EVENTS_MASTER.
	 */
	public void createEventsTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_EVENTS_MASTER");
			Statement statementCreate_TEM = c.createStatement();
			String stringCreateTEM = "CREATE TABLE IF NOT EXISTS TABLE_EVENTS_MASTER" +
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
			statementCreate_TEM.executeUpdate(stringCreateTEM);
			statementCreate_TEM.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_EVENTS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
	}
	
	/**
	 * Creates the tables associated with skills.
	 * TABLE_SKILLS_MASTER, TABLE_SKILLS_STUDENT.
	 */
	public void createSkillTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_SKILLS_MASTER");
			Statement statementCreate_TSM = c.createStatement();
			String stringCreateTSM = "CREATE TABLE IF NOT EXISTS TABLE_SKILLS_MASTER" +
			"(SKILL_ID INT PRIMARY KEY   NOT NULL,   " +
			" CATEGORY           TEXT    NOT NULL,   " +
			" SKILL_NAME         TEXT    NOT NULL ); ";
			statementCreate_TSM.executeUpdate(stringCreateTSM);
			statementCreate_TSM.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_SKILLS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_SKILLS_STUDENT");
			Statement statementCreate_TSS = c.createStatement();
			String stringCreateTSS = "CREATE TABLE IF NOT EXISTS TABLE_SKILLS_STUDENT" +
			"(STUDENT_ID INT		 NOT NULL, " +
			" CATEGORY   TEXT    NOT NULL, " +
			" SKILL_NAME TEXT    NOT NULL ); ";
			statementCreate_TSS.executeUpdate(stringCreateTSS);
			statementCreate_TSS.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_SKILLS_STUDENT");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * Drops the tables associated with skills.
	 * TABLE_SKILLS_MASTER, TABLE_SKILLS_STUDENT.
	 * 
	 * @param boolMaster If TABLE_SKILLS_MASTER should be dropped.
	 * @param boolStudent If TABLE_SKILLS_STUDENT should be dropped.
	 */
	public void dropSkillTables(boolean boolMaster, boolean boolStudent) {
		
		if (boolMaster) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
				System.out.println("Dropping Table TABLE_SKILLS_MASTER");
				Statement statementDrop_TSM = c.createStatement();
				String stringDropTSM = "DROP TABLE IF EXISTS TABLE_SKILLS_MASTER;";
				statementDrop_TSM.executeUpdate(stringDropTSM);
				statementDrop_TSM.close();
				c.close();
				System.out.println("Successful Drop of TABLE_SKILLS_MASTER");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("FAILED: Drop of TABLE_SKILLS_MASTER");
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
		}
		if (boolStudent) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
				System.out.println("Dropping Table TABLE_SKILLS_STUDENT");
				Statement statementDrop_TSS = c.createStatement();
				String stringDropTSS = "DROP TABLE IF EXISTS TABLE_SKILLS_STUDENT;";
				statementDrop_TSS.executeUpdate(stringDropTSS);
				statementDrop_TSS.close();
				c.close();
				System.out.println("Successful Drop of TABLE_SKILLS_STUDENT");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("FAILED: Drop of TABLE_SKILLS_STUDENT");
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
		}
	}
	
	/**
	 * Creates the tables associated with projects.
	 * TABLE_PROJECT_INDEX, TABLE_PROJECT_SKILLS.
	 */
	public void createProjectTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_PROJECT_INDEX");
			Statement statementCreate_TPI = c.createStatement();
			String sqlCreateTPI = "CREATE TABLE IF NOT EXISTS TABLE_PROJECT_INDEX" +
			"(ID INT PRIMARY KEY	NOT NULL, " +
			" TITLE          TEXT	NOT NULL, " +
			" START_DATE     TEXT	NOT NULL, " +
			" END_DATE       TEXT	NOT NULL, " +
			" LOCATION       TEXT	NOT NULL, " +
			" DESCRIPTION    TEXT	NOT NULL, " +
			" PAID			 TEXT	NOT NULL, " +
			" PROPOSER_NAME  TEXT	NOT NULL, " +
			" DATEPOSTED     TEXT     		 ); ";
			statementCreate_TPI.executeUpdate(sqlCreateTPI);
			statementCreate_TPI.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_PROJECT_INDEX");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_PROJECT_SKILLS");
			Statement statementCreate_TPS = c.createStatement();
			String sqlCreateTPS = "CREATE TABLE IF NOT EXISTS TABLE_PROJECT_SKILLS" +
			"(PROJECT_ID	 INT     NOT NULL, " +
			" CATEGORY       TEXT	 NOT NULL, " +
			" SKILLNAME      TEXT    NOT NULL); ";
			statementCreate_TPS.executeUpdate(sqlCreateTPS);
			statementCreate_TPS.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_PROJECT_SKILLS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_PROJECT_PROPOSED");
			Statement statementCreate_TPP = c.createStatement();
			String sqlCreateTPP = "CREATE TABLE IF NOT EXISTS TABLE_PROJECT_PROPOSED" +
			"(ID INT PRIMARY KEY	NOT NULL, " +
			" TITLE          TEXT	NOT NULL, " +
			" START_DATE     TEXT	NOT NULL, " +
			" END_DATE       TEXT	NOT NULL, " +
			" LOCATION       TEXT	NOT NULL, " +
			" DESCRIPTION    TEXT	NOT NULL, " +
			" PAID			 TEXT	NOT NULL, " +
			" PROPOSER_NAME  TEXT	NOT NULL, " +
			" DATEPOSTED     TEXT     		 ); ";
			statementCreate_TPP.executeUpdate(sqlCreateTPP);
			statementCreate_TPP.close();
			c.close();
			System.out.println("Successful Creation/Existence of TABLE_PROJECT_PROPOSED");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
}
