package com.msn.gabrielle.ui.views.Employee;

import com.vaadin.flow.component.button.Button;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.msn.gabrielle.ui.EmployeePage;
import com.msn.gabrielle.ui.views.Student.SQLProfileStud;
import com.msn.gabrielle.ui.views.Student.SkillStud;

@Route(value = "skillsemp", layout = EmployeePage.class)
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
	public SkillsEmp() {
		loadSkillsList();
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
		btnAddSkill.addClassName("view-toolbar__button");
		
		btnDeleteSkill = new Button("Delete Skill", event -> {
			
			// if it exists
			
			String category = tfSkillCategory.getValue();
			String skill = tfSkillName.getValue();
			
			// check database
			boolean skillExists = checkSkill(category, skill);
			if (skillExists) {
				// Remove the skill from the database, update the Grid
				deleteSkill(category, skill);
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
		btnDeleteSkill.setDisableOnClick(true);
		btnDeleteSkill.setEnabled(true);
		btnDeleteSkill.addClassName("view-toolbar__button");
		
		hlTFSkills = new HorizontalLayout();
		hlTFSkills.add(tfSkillCategory); hlTFSkills.add(tfSkillName);
		hlBTNSkills = new HorizontalLayout();
		hlBTNSkills.add(btnDeleteSkill); hlBTNSkills.add(btnAddSkill);
		
		Label lblSkillsTable = new Label("Skills Table");

		vlSkills = new VerticalLayout();
		vlSkills.add(lblSkillsTable); vlSkills.add(grid);
		add(hlTFSkills); add(hlBTNSkills); add(vlSkills);
	}
	public void loadSkillsList() {
		
		skillsList = sqlPStud.loadAllSkills();
	}
	
	public void addSkill(String category, String name) {
		
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
	public boolean checkSkill(String category, String name) {
		boolean skillExists = false;
		// Database check
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Skill check of TABLE_SKILLS_MASTER!");
			Statement statementGetCount = c.createStatement();
			String sqlGetCount = "SELECT * FROM TABLE_SKILLS_MASTER WHERE CATEGORY = '" + category +
							"' AND SKILL_NAME = '" + name + "';";
			statementGetCount.executeQuery(sqlGetCount);
			
			ResultSet rsCount = statementGetCount.executeQuery(sqlGetCount);
			//int count = 0;
			if (rsCount.next()) 
			{
				skillExists = true;
				
			}
			
			statementGetCount.close();
			
			c.close();
			
			
			System.out.println("Successful Skill check of TABLE_SKILLS_MASTER!");
			//if (count >= 1) 
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		return skillExists;
	}
	
	public void deleteSkill(String category, String name) {
		
		// Database check
			try {
				
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
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
    public void updateGrid() {
    	loadSkillsList();
    	grid.setItems(skillsList);
    	//Recall to the database to change values as necessary
    }
}
