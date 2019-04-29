package com.msn.gabrielle.ui.views.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class SQLProjectStud {

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
			System.out.println("Adding profile to database!");
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
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PostgresMall");
			System.out.println("Adding profile to database!");
			Statement statementSelectEmail = c.createStatement();
			String sqlSelectEmail = "SELECT * FROM TABLE_PROFILE_STUDENTS WHERE EMAIL ='" + email + "';";
			
			ResultSet rsCount = statementSelectEmail.executeQuery(sqlSelectEmail);
			ResultSetMetaData rsmdEmail = rsCount.getMetaData();
			
			int cols = rsmdEmail.getColumnCount();
			while(rsCount.next()) {
				for (int i = 1; i <= cols; i++) {
					
					if (rsCount.getString(i).equals("")) System.out.print("NULL");
					else System.out.print(rsCount.getString(i));
					System.out.print("	");
				}
				System.out.println();
			}
			
			statementSelectEmail.close();
			c.close();
			System.out.println("Successful Find Email from TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
	}
}
