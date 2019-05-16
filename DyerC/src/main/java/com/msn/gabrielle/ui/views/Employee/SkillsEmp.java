package com.msn.gabrielle.ui.views.Employee;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.msn.gabrielle.ui.EmployeePage;
import com.msn.gabrielle.ui.views.Student.SQLProfileStud;
import com.msn.gabrielle.ui.views.Student.SkillStud;

@Route(value = "skillsemp", layout = EmployeePage.class)
@HtmlImport("frontend://styles/shared-styles-ALUMNI.html")
@PageTitle("Skills List")
public class SkillsEmp extends VerticalLayout { 
	
	SQLProfileStud sqlPStud = new SQLProfileStud();
	
	Button btnAddSkill, btnDeleteSkill;
	TextField tfSkillCategory, tfSkillName;
	HorizontalLayout hlTFSkills, hlBTNSkills;
	VerticalLayout vlSkills;
	Notification nSkillExists;
	Button btnNSkillExists;
	
	Grid<SkillStud> grid;
	
	ArrayList<SkillStud> skillsList = new ArrayList<SkillStud>();
	
	
	/**
	 * Constructor for SkillsEmp that builds the UI and loads all of the skills from the database.
	 * Allows the user to add and remove skills.
	 */
	public SkillsEmp() {
		addClassName("main-lay");
		loadSkillsList();
		addClassName("main-layout-emp");
		grid = new Grid<>(SkillStud.class);
		grid.setItems(skillsList);
	
		tfSkillCategory = new TextField();
		tfSkillCategory.setLabel("Skill Category");
		tfSkillCategory.setPlaceholder("Enter the Skill's Category");
		
		tfSkillName = new TextField();
		tfSkillName.setLabel("Skill Name");
		tfSkillName.setPlaceholder("Enter the Skill's Name");
		
		hlBTNSkills = new HorizontalLayout();
		
		btnAddSkill = new Button("Add New Skill", event -> {
			try {
				String category = tfSkillCategory.getValue();
				String skill = tfSkillName.getValue();
				
				if (category.trim().equals("") || skill.trim().equals("")) {
					Label lblNotif = new Label("Please fill out both text fields!");
					nSkillExists = new Notification(lblNotif);
					nSkillExists.setDuration(3000);
					nSkillExists.setPosition(Position.MIDDLE);
					nSkillExists.open();
				}
				else {
					boolean exists = checkSkill(category, skill);
					if (!exists) { 
						addSkill(category, skill);
						updateGrid();
						tfSkillCategory.clear(); tfSkillName.clear();
					}
					else {
						Label lblNotif = new Label("The entered skill already exists!");
						nSkillExists = new Notification(lblNotif);
						nSkillExists.setDuration(3000);
						nSkillExists.setPosition(Position.MIDDLE);
						nSkillExists.open();
					}
				}
			} catch (Exception e) { e.printStackTrace(); }
		});
		btnAddSkill.setEnabled(true);
		btnAddSkill.addClassName("main-lay__button");
		
		btnDeleteSkill = new Button("Delete Skill", event -> {
			
			// if it exists
			
			String category = tfSkillCategory.getValue();
			String skill = tfSkillName.getValue();
			
			// check database
			boolean skillExists = checkSkill(category, skill);
			if (skillExists) {
				// Remove the skill from the database, update the Grid
				deleteSkill(category, skill);
				tfSkillCategory.clear();
				tfSkillName.clear();
				updateGrid();
			}
			else {
				Label lblNotif = new Label("The entered skill does not exist!");
				nSkillExists = new Notification(lblNotif);
				nSkillExists.setDuration(3000);
				nSkillExists.setPosition(Position.MIDDLE);
				nSkillExists.open();
			}
		});
		btnDeleteSkill.setEnabled(true);
		btnDeleteSkill.addClassName("main-lay__button");
		
		hlTFSkills = new HorizontalLayout();
		hlTFSkills.add(tfSkillCategory); hlTFSkills.add(tfSkillName);
		hlBTNSkills = new HorizontalLayout();
		hlBTNSkills.add(btnDeleteSkill); hlBTNSkills.add(btnAddSkill);
		
		Label lblSkillsTable = new Label("Skills Table");

		vlSkills = new VerticalLayout();
		vlSkills.add(lblSkillsTable); vlSkills.add(grid);
		add(hlTFSkills); add(hlBTNSkills); add(vlSkills);
	}
	
	
	/**
	 * Loads all the skills from the database and sets the skills list to the returned list.
	 */
	public void loadSkillsList() { skillsList = sqlPStud.loadAllSkills(); }
	
	/**
	 * Adds the passed skill given its category and name to TABLE_SKILLS_MASTER.
	 * @param category Category of the skill
	 * @param name Name of the skill
	 */
	public void addSkill(String category, String name) {
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
			System.out.println("Inserting Skill into database");
	
			Statement statementCount = c.createStatement();
			String sqlCount = "SELECT MAX(SKILL_ID) FROM TABLE_SKILLS_MASTER;";
			ResultSet rsCount = statementCount.executeQuery(sqlCount);
			rsCount.next();
			int idNumber = rsCount.getInt(1) + 1;
			statementCount.close();
			
	        //Get employee object within list
	        
    		Statement statementInsertSkill = c.createStatement();
			String sqlInsertSkill = "INSERT INTO TABLE_SKILLS_MASTER "
					+ "(SKILL_ID,CATEGORY,SKILL_NAME) VALUES "
					+ "(" + idNumber + ", '" + category + "', '" + name + "');";
			statementInsertSkill.executeUpdate(sqlInsertSkill);
			statementInsertSkill.close();

			System.out.println("Successful Count from TABLE_SKILLS_MASTER");
	
	        c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * Finds the provided skill in the table to determine if it should be added. Checks existence.
	 * @param category Category of the skill
	 * @param name Name of the skill
	 * @return Whether or not the skill existed in TABLE_SKILLS_MASTER 
	 */
	public boolean checkSkill(String category, String name) {
		boolean skillExists = false;
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
			System.out.println("Skill check of TABLE_SKILLS_MASTER!");
			Statement statementGetCount = c.createStatement();
			String sqlGetCount = "SELECT * FROM TABLE_SKILLS_MASTER WHERE CATEGORY = '" + category +
							"' AND SKILL_NAME = '" + name + "';";
			statementGetCount.executeQuery(sqlGetCount);
			
			ResultSet rsCount = statementGetCount.executeQuery(sqlGetCount);
			if (rsCount.next()) { skillExists = true; }
			
			statementGetCount.close();
			
			c.close();
			
			System.out.println("Successful Skill check of TABLE_SKILLS_MASTER!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		return skillExists;
	}
	
	/**
	 * Deletes the skill from TABLE_SKILLS_MASTER based upon the passed values of the category and name.
	 * @param category Category of the skill.
	 * @param name Name of the skill.
	 */
	public void deleteSkill(String category, String name) {
		
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
				System.out.println("Adding profile to database!");
				Statement statementRemoveSkill = c.createStatement();
				String sqlRemoveSkill = "DELETE FROM TABLE_SKILLS_MASTER WHERE CATEGORY = '" + category + 
								"' AND SKILL_NAME = '" + name + "';";
				
				statementRemoveSkill.executeUpdate(sqlRemoveSkill);
				statementRemoveSkill.close();
				c.close();

				System.out.println("Successful skill removal from database!");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
	}
	
    /**
     * Updates the grid of skills that are within TABLE_SKILLS_MASTER on the UI. Loades them first and then sets.
     */
    public void updateGrid() {
    	loadSkillsList();
    	grid.setItems(skillsList);
    }
}
