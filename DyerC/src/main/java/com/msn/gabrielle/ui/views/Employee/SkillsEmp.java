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

import com.msn.gabrielle.ui.EmployeePage;
import com.msn.gabrielle.ui.views.Student.SkillStud;

@Route(value = "skillsemp", layout = EmployeePage.class)
@PageTitle("Skills List")
public class SkillsEmp extends VerticalLayout { 
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
		
		/*
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
	    Statement stm;
	    stm = conn.createStatement();
	    String sql = "Select * From SKILLSTABLE";
	    ResultSet rst;
	    rst = stm.executeQuery(sql);
	    while (rst.next()) {
	        SkillStud skill = new SkillStud(rst.getString("skillCategory"), rst.getString("skillName"));
	        skillsList.add(skill);
	    }
	    */
		
		skillsList.add(new SkillStud("Skill Category 1", "Skill Name 1"));
		skillsList.add(new SkillStud("Skill Category 2", "Skill Name 2"));
	}
	
	public void addSkill(String category, String name) {
		
		try {
			/*
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Adding profile to database!");
			Statement statementAdd = c.createStatement();
			String sqlAdd = "INSERT INTO SKILLSTABLE (MAJOR,NAME) VALUES " +
			   "('"+ category + "', '" + name + "');";
			statementAdd.executeUpdate(sqlAdd);
			statementAdd.close();
			c.close();
			*/
			
			skillsList.add(new SkillStud(category, name));
			
			System.out.println("Successful skill add to database!");
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
			/*
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Adding profile to database!");
			Statement statementAdd = c.createStatement();
			String sqlAdd = "INSERT INTO SKILLSTABLE (MAJOR,NAME) VALUES " +
			   "('"+ category + "', '" + name + "');";
			statementAdd.executeUpdate(sqlAdd);
			statementAdd.close();
			c.close();
			*/
			Iterator<SkillStud> itr = skillsList.iterator();
			while (itr.hasNext()) {
				SkillStud skillS = itr.next();
			    if (skillS.getCategory().equals(category) && skillS.getName().equals(name)) {
			    	skillExists = true;
			    	break;
			    }
			}
			System.out.println("Successful skill removal from database!");
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
				/*
				Class.forName("org.postgresql.Driver");
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
				System.out.println("Adding profile to database!");
				Statement statementAdd = c.createStatement();
				String sqlAdd = "INSERT INTO SKILLSTABLE (MAJOR,NAME) VALUES " +
				   "('"+ category + "', '" + name + "');";
				statementAdd.executeUpdate(sqlAdd);
				statementAdd.close();
				c.close();
				*/
				int i = 0;
				Iterator<SkillStud> itr = skillsList.iterator();
				while (itr.hasNext()) {
					
					SkillStud skillS = itr.next();
				    if (skillS.getCategory().equals(category) && skillS.getName().equals(name)) {
				    	skillsList.remove(i);
				    	break;
				    }
				    i++;
				}
				System.out.println("Successful skill removal from database!");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
			}
	}
    public void updateGrid() {
    	grid.setItems(skillsList);
    	//Recall to the database to change values as necessary
    }
}
