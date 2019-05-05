package com.msn.gabrielle.ui.views.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class SQLProfileStud {

	public void insertNewProfile(String name, String email, String password, 
			String phoneNo, String maj1, String maj2,
			String min1, String min2) {
		if (phoneNo == null) phoneNo = "";
		if (maj1    == null) maj1    = "";
		if (maj2    == null) maj2    = "";
		if (min1 	== null) min1 	 = "";
		if (min2 	== null) min2 	 = "";

		int idNumber;

		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Inserting profile to database");
			Statement statementCount = c.createStatement();
			String sqlCount = "SELECT MAX(ID) FROM TABLE_PROFILE_STUDENTS;";
			ResultSet rsCount = statementCount.executeQuery(sqlCount);
			rsCount.next();
			//System.out.println("TOTAL COUNT IS: " + rsCount.getInt(1));
			idNumber = rsCount.getInt(1) + 1;
			statementCount.close();

			Statement statementInsertStudent = c.createStatement();
			String sqlInsertStudent = "INSERT INTO TABLE_PROFILE_STUDENTS "
					+ "(ID,NAME,EMAIL,PASSWORD,PHONENO,MAJOR1,MAJOR2,MINOR1,MINOR2) VALUES "
					+ "(" + idNumber + ", '" + name + "', '" + email + "', '" + password 
					+ "', '" + phoneNo + "'," + " '" + maj1 + "', '" + maj2 + "', '" + min1 
					+ "', '" + min2 + "');";
			statementInsertStudent.executeUpdate(sqlInsertStudent);
			statementInsertStudent.close();
			c.close();
			System.out.println("Successful Count from TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}

	}

	public void getProfileValues(String email) {
		String eID = "", eName = "", eEmail = "", ePword = "", 
			   ePhone = "", eMaj1 = "", eMaj2 = "", eMin1 = "", eMin2 = "";
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Selecting on Email from TABLE_PROFILE_STUDENTS");
			Statement statementSelectEmail = c.createStatement();
			String sqlSelectEmail = "SELECT * FROM TABLE_PROFILE_STUDENTS WHERE EMAIL ='" + email + "';";

			ResultSet rsEmail = statementSelectEmail.executeQuery(sqlSelectEmail);
			ResultSetMetaData rsmdEmail = rsEmail.getMetaData();

			int cols = rsmdEmail.getColumnCount();
			
			while(rsEmail.next()) {
				eID	   = rsEmail.getString(1);
				eName  = rsEmail.getString(2);
				eEmail = rsEmail.getString(3);
				ePword = rsEmail.getString(4);
				ePhone = rsEmail.getString(5);
				eMaj1  = rsEmail.getString(6);
				eMaj2  = rsEmail.getString(7);
				eMin1  = rsEmail.getString(8);
				eMin2  = rsEmail.getString(9);
				System.out.println(eID + " " + eName + " " + eEmail + " " + ePword + " " + ePhone + " " +
						eMaj1 + " " + eMaj2 + " " + eMin1 + " " + eMin2);
				/*
				for (int i = 1; i <= cols; i++) {

				if (rsCount.getString(i).equals("")) System.out.print("NULL");
				else System.out.print(rsCount.getString(i));
				System.out.print("	");
				}*/

			}
			statementSelectEmail.close();
			c.close();
			System.out.println("Successful Find Email from TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Finding Skills from TABLE_SKILLS_STUDENT / TABLE_PROFILE_STUDENTS");
			Statement statementSelectSkills = c.createStatement();
			String sqlSelectSkills = "SELECT * FROM TABLE_SKILLS_STUDENT INNER JOIN " + 
									 "TABLE_PROFILE_STUDENTS ON TABLE_SKILLS_STUDENT.STUDENT_ID=TABLE_PROFILE_STUDENTS.ID " +
									 "WHERE ID = '" + eID + "';";

			ResultSet rsSkills = statementSelectSkills.executeQuery(sqlSelectSkills);
			ResultSetMetaData rsmdSkills = rsSkills.getMetaData();

			int cols = rsmdSkills.getColumnCount();
			while(rsSkills.next()) {
				String sID		 = rsSkills.getString(1);
				String sCategory = rsSkills.getString(2);
				String sSkill	 = rsSkills.getString(3);
				System.out.println(sID + " " + sCategory + " " + sSkill);

			}
			statementSelectSkills.close();
			c.close();
			System.out.println("Successful Find Skills from TABLE_SKILLS_STUDENT / TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	
	public void updateName(String newName, String studentEmail) {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Updating Profile Name in TABLE_PROFILE_STUDENTS");
			Statement statementUpdateName = c.createStatement();
			String sqlUpdateName = "UPDATE TABLE_PROFILE_STUDENTS SET NAME = '" + newName + 
									 "' WHERE EMAIL = '" + studentEmail + "'; ";

			statementUpdateName.executeUpdate(sqlUpdateName);
			statementUpdateName.close();
			c.close();
			System.out.println("Successful Update Profile Name in TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
}
