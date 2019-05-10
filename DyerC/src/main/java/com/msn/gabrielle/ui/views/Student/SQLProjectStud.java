package com.msn.gabrielle.ui.views.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.msn.gabrielle.backend.Projects;

public class SQLProjectStud {

	
	SQLProfileStud sqlProfStud = new SQLProfileStud();
	public SQLProjectStud() { }
	
	public ArrayList<Projects> loadMatchingProjects(String email) {
		
		ArrayList<SkillStud> listSkills = new ArrayList<SkillStud>();
		//System.out.println(listSkills);
		ArrayList<String> listProjectIDs = new ArrayList<String>();
		
		Set<String> uniqueProjects;
		listSkills = sqlProfStud.getProfileValues(email);
		
		String pID = "", pTitle = "", pStart = "", pEnd = "", 
				   pLocation = "", pDesc = "", pPaid = "", pProp = "", pPosted = "";
		ArrayList<Projects> listProjects = new ArrayList<Projects>();
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Getting Unique Matching Project IDS from TABLE_PROJECT_SKILLS");

			
			for (int i = 0; i < listSkills.size(); i++) {
				SkillStud currentSkill = listSkills.get(i);
				String cat = currentSkill.skillCategory;
				String name = currentSkill.skillName;
				System.out.println("SELECT * FROM TABLE_PROJECT_SKILLS WHERE CATEGORY = '" + cat +
						 "' AND SKILLNAME = '" + name + "';");
				Statement statementLoadProjects = c.createStatement();
				String sqlLoadProjects = "SELECT * FROM TABLE_PROJECT_SKILLS WHERE CATEGORY = '" + cat +
										 "' AND SKILLNAME = '" + name + "';";
				ResultSet rsProjects = statementLoadProjects.executeQuery(sqlLoadProjects);
				while (rsProjects.next()) {
					pID = rsProjects.getString(1);
					listProjectIDs.add(pID);
				}
			}
			uniqueProjects = new HashSet<String>(listProjectIDs);
			System.out.println(uniqueProjects);
			
			System.out.println("Successful Unique Matching Project IDS from TABLE_PROJECT_SKILLS");
			
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Loading Unique Projects from TABLE_PROJECT_INDEX");
			ArrayList<String> uniqueList = new ArrayList<String>(); 
		    for (String id : uniqueProjects) uniqueList.add(id); 
			for (int i = 0; i < uniqueList.size(); i++) {
				Statement statementLoadUniqueProjects= c.createStatement();
				
				String sqlLoadUniqueProjects = "SELECT * FROM TABLE_PROJECT_INDEX WHERE ID = '" + uniqueList.get(i) + "';";
				ResultSet rsUniqueProjects = statementLoadUniqueProjects.executeQuery(sqlLoadUniqueProjects);
				while(rsUniqueProjects.next()) {
					
					pID	   = rsUniqueProjects.getString(1);
					pTitle  = rsUniqueProjects.getString(2);
					pStart = rsUniqueProjects.getString(3); 
					pEnd = rsUniqueProjects.getString(4); 
					pLocation = rsUniqueProjects.getString(5); 
					pDesc  = rsUniqueProjects.getString(6); 
					pPaid  = rsUniqueProjects.getString(7); 
					pProp  = rsUniqueProjects.getString(8); 
					pPosted  = rsUniqueProjects.getString(9); 
					System.out.println(pID + " " + pTitle + " " + pStart + " " + pEnd + " " + pLocation + " " + 
									   pDesc + " " + pPaid + " " + pProp + " " + pPosted);
					
					// Create New Project and Add to List
					listProjects.add(new Projects(pTitle,pStart,pEnd,pLocation,pDesc,pPaid,pProp,pPosted));
				}
				statementLoadUniqueProjects.close();
			}
			
			System.out.println("Successful Loading Unique Projects from TABLE_PROJECT_SKILLS");
			
			
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}	
		return listProjects;
	}
	
	public ArrayList<Projects> loadProjects() {
		
		String pID = "", pTitle = "", pStart = "", pEnd = "", 
				   pLocation = "", pDesc = "", pPaid = "", pProp = "", pPosted = "";
		ArrayList<Projects> listProjects = new ArrayList<Projects>();
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Loading Projects from TABLE_PROJECT_INDEX");

			Statement statementLoadProjects = c.createStatement();
			
			String sqlLoadProjects = "SELECT * FROM TABLE_PROJECT_INDEX;";
			
			ResultSet rsProjects = statementLoadProjects.executeQuery(sqlLoadProjects);

			while(rsProjects.next()) {
				
				pID	   = rsProjects.getString(1);
				pTitle  = rsProjects.getString(2);
				pStart = rsProjects.getString(3); 
				pEnd = rsProjects.getString(4); 
				pLocation = rsProjects.getString(5); 
				pDesc  = rsProjects.getString(6); 
				pPaid  = rsProjects.getString(7); 
				pProp  = rsProjects.getString(8); 
				pPosted  = rsProjects.getString(9); 
				
				// Create New Project and Add to List
				listProjects.add(new Projects(pTitle,pStart,pEnd,pLocation,pDesc,pPaid,pProp,pPosted));
			}
			
			statementLoadProjects.close();

			c.close();
			System.out.println("Successful Loading from TABLE_PROJECT_INDEX");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}	
		return listProjects;
	}
}
