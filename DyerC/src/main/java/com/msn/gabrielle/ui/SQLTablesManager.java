package com.msn.gabrielle.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.msn.gabrielle.ui.views.Student.SQLProfileStud;
import com.msn.gabrielle.ui.views.Student.SQLProjectStud;
import com.msn.gabrielle.ui.views.Student.SkillsLoader;

public class SQLTablesManager {

	public SQLTablesManager() { }
	
	public void determineDBStates() {
		//dropProfileTables(true, true, true);
		dropSkillTables(true, false);
		createProfileTables();
		createSkillTables();
		createEventsTables();
		SQLProfileStud sqlPS = new SQLProfileStud();
		//sqlPS.insertNewProfile("fqfgqg", "email", "password", "phoneNo", null, "m2", null, "min2");
		sqlPS.getProfileValues("lol");
		sqlPS.updateName("HAHA", "lol");
		
		SkillsLoader sl = new SkillsLoader();
		sl.loadInJSONValues();
		
		
	}
	
	public void createProfileTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
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
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
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
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
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
	public void dropProfileTables(boolean boolStudent, boolean boolAlumni, boolean boolEmployee) {
		
		if (boolStudent) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
															"postgres", "PostgresMall");
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
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
															"postgres", "PostgresMall");
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
	
	public void createEventsTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
			System.out.println("Creating/Checking Existence of TABLE_SKILLS_MASTER");
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
	public void createProjectTables() {
		
	}
	public void createSkillTables() {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
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
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
														"postgres", "PostgresMall");
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
	
	public void dropSkillTables(boolean boolMaster, boolean boolStudent) {
		
		if (boolMaster) {
			try {
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
															"postgres", "PostgresMall");
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
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
															"postgres", "PostgresMall");
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
	
}
