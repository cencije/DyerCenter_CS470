package com.msn.gabrielle.ui.views.Student;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.msn.gabrielle.backend.Projects;

public class SQLProjectStud {

	
	SQLProfileStud sqlProfStud = new SQLProfileStud();
	
	/**
	 * Constructor for the SQLProjectStud class
	 */
	public SQLProjectStud() { }
	
	/**
	 * Looks up a student's profile based on email and gets their profile_id from TABLE_PROFILE_STUDENTS.
	 * Takes the profile_id and finds their skills in TABLE_SKILLS_STUDENT.
	 * Takes the list of skills and matches it to projects in TABLE_PROJECT_SKILLS based on category and skill name.
	 * Returns the list of projects that match based upon both values.
	 * @param email Email of the student to be found TABLE_PROFILE_STUDENTS.
	 * @return The list of projects that match a student's skills and category.
	 */
	public ArrayList<Projects> loadMatchingProjectsCatSkill(String email) {
		
		ArrayList<SkillStud> listSkills = new ArrayList<SkillStud>();
		ArrayList<ArrayList<SkillStud>> projectsSkills = new ArrayList<ArrayList<SkillStud>>();
		ArrayList<String> listProjectIDs = new ArrayList<String>();
		
		Set<String> uniqueProjects;
		listSkills = sqlProfStud.getProfileValues(email);
		
		String pID = "", pTitle = "", pStart = "", pEnd = "", 
				   pLocation = "", pDesc = "", pPaid = "", pProp = "", pPosted = "";
		ArrayList<Projects> listProjects = new ArrayList<Projects>();
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
					
					// Create New Project and Add to List
					Projects p = new Projects(pID, pTitle,pStart,pEnd,pLocation,pDesc,pPaid,pProp,pPosted);
					listProjects.add(p);
				}
				statementLoadUniqueProjects.close();
			}
			
			System.out.println("Successful Loading Unique Projects from TABLE_PROJECT_SKILLS");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Loading Skill Studs for Projects from TABLE_PROJECT_SKILLS");
			
			for (int i = 0; i < listProjects.size(); i++) {
				List<SkillStud> skillsListProject = new ArrayList<SkillStud>();
				Statement statementGetProjectSkills= c.createStatement();
				
				String sqlGetProjectSkills = "SELECT * FROM TABLE_PROJECT_SKILLS WHERE PROJECT_ID = '" + 
											 listProjects.get(i).getProjectIDSQL() + "';";
				ResultSet rsGetProjectSkills = statementGetProjectSkills.executeQuery(sqlGetProjectSkills);
				while(rsGetProjectSkills.next()) {
					
					String pID2	= rsGetProjectSkills.getString(1);
					String pCat = rsGetProjectSkills.getString(2);
					String pName = rsGetProjectSkills.getString(3);
					skillsListProject.add(new SkillStud(pCat, pName));
				}
				listProjects.get(i).setSkillsList(skillsListProject);
				statementGetProjectSkills.close();
			}
			System.out.println("Successful Loading Skill Studs for Projects from TABLE_PROJECT_SKILLS");
			
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}	
		return listProjects;
	}
	
	/**
	 * Looks up a student's profile based on email and gets their profile_id from TABLE_PROFILE_STUDENTS.
	 * Takes the profile_id and finds their skills in TABLE_SKILLS_STUDENT.
	 * Takes the list of skills and matches it to projects in TABLE_PROJECT_SKILLS based on category alone.
	 * Returns the list of projects that match based upon skill category.
	 * @param email Email of the student to be found TABLE_PROFILE_STUDENTS.
	 * @return The list of projects that match a student's skill categories.
	 */
	public ArrayList<Projects> loadMatchingProjectsCategory(String email) {
		
		List<SkillStud> listSkills = new ArrayList<SkillStud>();
		ArrayList<String> listProjectIDs = new ArrayList<String>();
		
		Set<String> uniqueProjects;
		listSkills = sqlProfStud.getProfileValues(email);
		
		String pID = "", pTitle = "", pStart = "", pEnd = "", 
				   pLocation = "", pDesc = "", pPaid = "", pProp = "", pPosted = "";
		ArrayList<Projects> listProjects = new ArrayList<Projects>();
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
			System.out.println("Getting Unique Matching Project IDS from TABLE_PROJECT_SKILLS");

			
			for (int i = 0; i < listSkills.size(); i++) {
				SkillStud currentSkill = listSkills.get(i);
				String cat = currentSkill.skillCategory;
				System.out.println("SELECT * FROM TABLE_PROJECT_SKILLS WHERE CATEGORY = '" + cat +"';");
				Statement statementLoadProjects = c.createStatement();
				String sqlLoadProjects = "SELECT * FROM TABLE_PROJECT_SKILLS WHERE CATEGORY = '" + cat +"';";
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
					// Create New Project and Add to List
					Projects p = new Projects(pID, pTitle,pStart,pEnd,pLocation,pDesc,pPaid,pProp,pPosted);
					listProjects.add(p);
				}
				statementLoadUniqueProjects.close();
			}
			
			System.out.println("Successful Loading Unique Projects from TABLE_PROJECT_INDEX");
			
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Loading Skill Studs for Projects from TABLE_PROJECT_SKILLS");
			
			for (int i = 0; i < listProjects.size(); i++) {
				List<SkillStud> skillsListProject = new ArrayList<SkillStud>();
				Statement statementGetProjectSkills= c.createStatement();
				
				String sqlGetProjectSkills = "SELECT * FROM TABLE_PROJECT_SKILLS WHERE PROJECT_ID = '" + 
											 listProjects.get(i).getProjectIDSQL() + "';";
				ResultSet rsGetProjectSkills = statementGetProjectSkills.executeQuery(sqlGetProjectSkills);
				while(rsGetProjectSkills.next()) {
					
					String pID2	= rsGetProjectSkills.getString(1);
					String pCat = rsGetProjectSkills.getString(2);
					String pName = rsGetProjectSkills.getString(3);
					skillsListProject.add(new SkillStud(pCat, pName));
				}
				listProjects.get(i).setSkillsList(skillsListProject);
				statementGetProjectSkills.close();
			}
			System.out.println("Successful Loading Skill Studs for Projects from TABLE_PROJECT_SKILLS");
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}	
		return listProjects;
	}
	
	/**
	 * Loads all the projects that have been approved from TABLE_PROJECT_INDEX.
	 * @return The list of all projects that have been approved.
	 */
	public ArrayList<Projects> loadProjects() {
		
		String pID = "", pTitle = "", pStart = "", pEnd = "", 
				   pLocation = "", pDesc = "", pPaid = "", pProp = "", pPosted = "";
		ArrayList<Projects> listProjects = new ArrayList<Projects>();
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
				Projects p = new Projects(pID, pTitle,pStart,pEnd,pLocation,pDesc,pPaid,pProp,pPosted);
				listProjects.add(p);
			}
			statementLoadProjects.close();
			System.out.println("Successful Loading from TABLE_PROJECT_INDEX");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Loading Skill Studs for Projects from TABLE_PROJECT_SKILLS");
			
			for (int i = 0; i < listProjects.size(); i++) {
				List<SkillStud> skillsListProject = new ArrayList<SkillStud>();
				Statement statementGetProjectSkills= c.createStatement();
				
				String sqlGetProjectSkills = "SELECT * FROM TABLE_PROJECT_SKILLS WHERE PROJECT_ID = '" + 
											 listProjects.get(i).getProjectIDSQL() + "';";
				ResultSet rsGetProjectSkills = statementGetProjectSkills.executeQuery(sqlGetProjectSkills);
				while(rsGetProjectSkills.next()) {
					
					String pID2	= rsGetProjectSkills.getString(1);
					String pCat = rsGetProjectSkills.getString(2);
					String pName = rsGetProjectSkills.getString(3);
					skillsListProject.add(new SkillStud(pCat, pName));
				}
				listProjects.get(i).setSkillsList(skillsListProject);
				statementGetProjectSkills.close();
			}
			System.out.println("Successful Loading Skill Studs for Projects from TABLE_PROJECT_SKILLS");
			c.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}	
		return listProjects;
	}
	
	/**
	 * Loads the list of skills that can be used for projects.
	 * @return The list of skills that can be used for projects.
	 */
	public List<SkillStud> loadAllSkills() {
		List<SkillStud> skillsList = new ArrayList<SkillStud>();
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
			System.out.println("Loading all skills from TABLE_SKILLS_MASTER");
			Statement statementGetAllSkills = c.createStatement();
			String sqlGetAllSkills = "SELECT * FROM TABLE_SKILLS_MASTER;";

			ResultSet rsSkills = statementGetAllSkills.executeQuery(sqlGetAllSkills);
			while(rsSkills.next()) {
				String sCategory = rsSkills.getString(2);
				String sSkill	 = rsSkills.getString(3);
				skillsList.add(new SkillStud(sCategory, sSkill));
			}
			statementGetAllSkills.close();
			c.close();
			System.out.println("Successful loading of all skills from TABLE_SKILLS_MASTER");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		return skillsList;
	}
}
