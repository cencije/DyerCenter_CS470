package com.msn.gabrielle.ui.views.Employee;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
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

import com.msn.gabrielle.ui.EmployeePage;
import com.msn.gabrielle.ui.views.Student.SkillStud;

@Route(value = "skillsemp", layout = EmployeePage.class)
@PageTitle("Skills List")
public class SkillsEmp extends VerticalLayout { 
	Button btnAddSkill, btnDeleteSkill;
	TextField tfSkillMajor, tfSkillName;
	HorizontalLayout hlTFSkills, hlBTNSkills;
	VerticalLayout vlSkills;
	Notification nSkillExists;
	Button btnNSkillExists;
	
	ArrayList<SkillStud> skillsList = new ArrayList<SkillStud>();
	public SkillsEmp() {
		loadSkillsList();
		Grid<SkillStud> grid = new Grid<>(SkillStud.class);
		grid.setItems(skillsList);
	
		tfSkillMajor = new TextField();
		tfSkillMajor.setLabel("Skill Major");
		tfSkillMajor.setPlaceholder("Enter the Skill's Major");
		
		tfSkillName = new TextField();
		tfSkillName.setLabel("Skill Name");
		tfSkillName.setPlaceholder("Enter the Skill's Name");
		
		hlBTNSkills = new HorizontalLayout();
		
		btnAddSkill = new Button("Add New Skill", event -> {
			try {
				String major = tfSkillMajor.getValue();
				String skill = tfSkillName.getValue();
				
				if (major.trim().equals("") || skill.trim().equals("")) {
					Label lblNotif = new Label("Please fill out both text fields!");
					nSkillExists = new Notification(lblNotif);
					nSkillExists.setDuration(3000);
					nSkillExists.setPosition(Position.MIDDLE);
					nSkillExists.open();
				}
				else {
					
					boolean isNew = checkSkill(major, skill);
					if (isNew) { 
						//addSkill(major, skill);
						
						tfSkillMajor.clear(); tfSkillName.clear();
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
		
		btnDeleteSkill = new Button("Delete Skill", event -> {
			
			// if it exists
			
			String major = "LOL";
			String skill = "XD";
			// check database
			boolean skillExists = checkSkill(major, skill);
			if (skillExists) {
				// Remove the skill from the database, update the Grid
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
		
		hlTFSkills = new HorizontalLayout();
		hlTFSkills.add(tfSkillMajor); hlTFSkills.add(tfSkillName);
		hlBTNSkills = new HorizontalLayout();
		hlBTNSkills.add(btnDeleteSkill); hlBTNSkills.add(btnAddSkill);
		
		vlSkills = new VerticalLayout();
		vlSkills.add(grid);
		add(hlTFSkills); add(hlBTNSkills); add(vlSkills);
	}
	public void loadSkillsList() {
		
		/*
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
	    Statement stm;
	    stm = conn.createStatement();
	    String sql = "Select * From SKILLSTABLE";
	    ResultSet rst;
	    rst = stm.executeQuery(sql);
	    while (rst.next()) {
	        SkillStud skill = new SkillStud(rst.getString("skillMajor"), rst.getString("skillName"));
	        skillsList.add(skill);
	    }
	    */
		
		skillsList.add(new SkillStud("Skill Major 1", "Skill Name 1"));
		skillsList.add(new SkillStud("Skill Major 2", "Skill Name 2"));
	}
	
	public void addSkill(String major, String name) {
		
		try {
			/*
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Adding profile to database!");
			Statement statementAdd = c.createStatement();
			String sqlAdd = "INSERT INTO SKILLSTABLE (MAJOR,NAME) VALUES " +
			   "('"+ major + "', '" + name + "');";
			statementAdd.executeUpdate(sqlAdd);
			statementAdd.close();
			c.close();
			*/
			System.out.println("Successful skill add to database!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	public boolean checkSkill(String major, String name) {
		boolean skillExists = false;
		// Database check
		try {
			/*
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Adding profile to database!");
			Statement statementAdd = c.createStatement();
			String sqlAdd = "INSERT INTO SKILLSTABLE (MAJOR,NAME) VALUES " +
			   "('"+ major + "', '" + name + "');";
			statementAdd.executeUpdate(sqlAdd);
			statementAdd.close();
			c.close();
			*/
			System.out.println("Successful skill removal from database!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		return skillExists;
	}
	
    public void updateGrid() {
    	
    	//Recall to the database to change values as necessary
    }
}
