package com.msn.gabrielle.ui.views.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLProjectEmp {

	
	public SQLProjectEmp() { }
	
	
	public int insertProject(String title, String start, String end, String location,
							  String desc, String pay, String proposer, String posted) {
		int idNumber = 0;
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Inserting Project into TABLE_PROJECT_INDEX");

			Statement statementCount = c.createStatement();
			String sqlCount = "SELECT MAX(ID) FROM TABLE_PROJECT_INDEX;";
			ResultSet rsCount = statementCount.executeQuery(sqlCount);
			rsCount.next();
			idNumber = rsCount.getInt(1) + 1;
			statementCount.close();
			Statement statementInsertProject = c.createStatement();
			
			String sqlInsertProject = "INSERT INTO TABLE_PROJECT_INDEX "
					+ "(ID,TITLE,START_DATE,END_DATE,LOCATION,DESCRIPTION,PAID,PROPOSER_NAME,DATEPOSTED) VALUES "
					+ "(" + idNumber + ", '" + title + "', '" + start 
					+ "', '" + end + "', '" + desc + "', '" + pay + "', '" + proposer 
					+ "', '" + posted + "');";
			statementInsertProject.executeUpdate(sqlInsertProject);
			statementInsertProject.close();
			c.close();
			System.out.println("Successful Insertion to TABLE_EVENTS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		return idNumber;
	}
	
	public void insertProjectSkills(int idNo, ArrayList<String> listSkills) {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Inserting Project into TABLE_PROJECT_SKILLS");

			for (int i = 0; i < listSkills.size(); i++) {
				Statement statementInsertSkill = c.createStatement();
				
				String sqlInsertSkill = "INSERT INTO TABLE_PROJECT_SKILLS "
						+ "(PROJECT_ID, SKILLNAME) VALUES "
						+ "(" + idNo + ", '" + listSkills.get(i) + "');";
				statementInsertSkill.executeUpdate(sqlInsertSkill);
				statementInsertSkill.close();
			}
			c.close();
			System.out.println("Successful Insertion to TABLE_EVENTS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
	}
}
