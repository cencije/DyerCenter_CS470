package com.msn.gabrielle.ui.views.Alumni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.msn.gabrielle.backend.Projects;
import com.msn.gabrielle.ui.views.Student.SkillStud;

public class SQLProjectAlum {

	
public SQLProjectAlum() { }
	
	
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
					+ "', '" + end + "', '" + location + "', '" + desc + "', '" + pay + "', '" + proposer 
					+ "', '" + posted + "');";
			statementInsertProject.executeUpdate(sqlInsertProject);
			statementInsertProject.close();
			c.close();
			System.out.println("Successful Insertion to TABLE_PROJECT_INDEX");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		return idNumber;
	}
	
	public void insertProjectSkills(int idNo, ArrayList<SkillStud> listSkills) {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Inserting Project into TABLE_PROJECT_SKILLS");

			for (int i = 0; i < listSkills.size(); i++) {
				Statement statementInsertSkill = c.createStatement();
				String sqlInsertSkill = "INSERT INTO TABLE_PROJECT_SKILLS "
						+ "(PROJECT_ID, CATEGORY, SKILLNAME) VALUES "
						+ "(" + idNo + ", '" + listSkills.get(i).skillCategory 
						+ "', '" +  listSkills.get(i).skillName +"');";
				statementInsertSkill.executeUpdate(sqlInsertSkill);
				statementInsertSkill.close();
			}
			c.close();
			System.out.println("Successful Insertion to TABLE_PROJECT_SKILLS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}	
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
				listProjects.add(new Projects(pID, pTitle,pStart,pEnd,pLocation,pDesc,pPaid,pProp,pPosted));
			}
			
			statementLoadProjects.close();

			System.out.println("Successful Loading from TABLE_PROJECT_INDEX");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Loading Skill Studs for Projects from TABLE_PROJECT_SKILLS");
			
			for (int i = 0; i < listProjects.size(); i++) {
				ArrayList<SkillStud> skillsListProject = new ArrayList<SkillStud>();
				Statement statementGetProjectSkills= c.createStatement();
				
				String sqlGetProjectSkills = "SELECT * FROM TABLE_PROJECT_SKILLS WHERE PROJECT_ID = '" + 
											 listProjects.get(i).getProjectIDSQL() + "';";
				ResultSet rsGetProjectSkills = statementGetProjectSkills.executeQuery(sqlGetProjectSkills);
				while(rsGetProjectSkills.next()) {
					
					String pID2	= rsGetProjectSkills.getString(1);
					String pCat = rsGetProjectSkills.getString(2);
					String pName = rsGetProjectSkills.getString(3);
					System.out.println(pID2 + " " + pCat + " " + pName);
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
	
	public ArrayList<SkillStud> loadAllSkills() {
		ArrayList<SkillStud> skillsList = new ArrayList<SkillStud>();
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
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
