package com.msn.gabrielle.ui.views.Student;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class SQLProfileStud {

	ArrayList<String> listProfileInfo = new ArrayList<String>(); // List of info for the student profile
	
	/**
	 * Constructor for the SQLProfileStud class.
	 */
	public SQLProfileStud() {}
	
	/**
	 * Will insert a new profile into TABLE_PROFILE_STUDENTS that, based off of passed values.
	 * @param name Name of the student for the new profile.
	 * @param email Email of the student for the new profile.
	 * @param password Password of the student for the new profile.
	 * @param phoneNo Phone number of the student for the new profile.
	 * @param maj1 Major 1 of the student for the new profile.
	 * @param maj2 Major 2 of the student for the new profile.
	 * @param min1 Minor 1 of the student for the new profile.
	 * @param min2 Minor 2 of the student for the new profile.
	 */
	public void insertNewProfile(String name, String email, String password, 
			String phoneNo, String maj1, String maj2,
			String min1, String min2) {
		if (phoneNo == null) phoneNo = "";
		if (maj1    == null) maj1    = "";
		if (maj2    == null) maj2    = "";
		if (min1 	== null) min1 	 = "";
		if (min2 	== null) min2 	 = "";

		int idNumber;
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

			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					   								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Couting Max ID in TABLE_PROFILE_STUDENTS");
			Statement statementCount = c.createStatement();
			String sqlCount = "SELECT MAX(ID) FROM TABLE_PROFILE_STUDENTS;";
			ResultSet rsCount = statementCount.executeQuery(sqlCount);
			rsCount.next();
			
			idNumber = rsCount.getInt(1) + 1;
			statementCount.close();
			System.out.println("Successful Count from TABLE_PROFILE_STUDENTS");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Inserting profile into TABLE_PROFILE_STUDENTS");
			Statement statementInsertStudent = c.createStatement();
			String sqlInsertStudent = "INSERT INTO TABLE_PROFILE_STUDENTS "
					+ "(ID,NAME,EMAIL,PASSWORD,PHONENO,MAJOR1,MAJOR2,MINOR1,MINOR2) VALUES "
					+ "(" + idNumber + ", '" + name + "', '" + email + "', '" + password 
					+ "', '" + phoneNo + "'," + " '" + maj1 + "', '" + maj2 + "', '" + min1 
					+ "', '" + min2 + "');";
			statementInsertStudent.executeUpdate(sqlInsertStudent);
			statementInsertStudent.close();
			c.close();
			System.out.println("Successful insetion into TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * Looks for the student based on a passed email value and finds their profile data from TABLE_PROFILE_STUDENTS.
	 * @param email Email of the student to be found in TABLE_PROFILE_STUDENTS.
	 * @return A list of skills associated with their profile.
	 */
	public ArrayList<SkillStud> getProfileValues(String email) {
		String eID = "", eName = "", eEmail = "", ePword = "", 
			   ePhone = "", eMaj1 = "", eMaj2 = "", eMin1 = "", eMin2 = "";
		ArrayList<SkillStud> listProfileSkills = new ArrayList<SkillStud>();
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

			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					   								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Selecting on Email from TABLE_PROFILE_STUDENTS");
			Statement statementSelectEmail = c.createStatement();
			String sqlSelectEmail = "SELECT * FROM TABLE_PROFILE_STUDENTS WHERE EMAIL ='" + email + "';";

			ResultSet rsEmail = statementSelectEmail.executeQuery(sqlSelectEmail);

			while(rsEmail.next()) {
				ArrayList<String> profileVals = new ArrayList<String>();
				eID	   = rsEmail.getString(1);
				eName  = rsEmail.getString(2); profileVals.add(eName);
				eEmail = rsEmail.getString(3); profileVals.add(eEmail);
				ePword = rsEmail.getString(4); profileVals.add(ePword);
				ePhone = rsEmail.getString(5); profileVals.add(ePhone);
				eMaj1  = rsEmail.getString(6); profileVals.add(eMaj1);
				eMaj2  = rsEmail.getString(7); profileVals.add(eMaj2);
				eMin1  = rsEmail.getString(8); profileVals.add(eMin1);
				eMin2  = rsEmail.getString(9); profileVals.add(eMin2);
				listProfileInfo = profileVals;
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
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					   								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Finding Skills from TABLE_SKILLS_STUDENT / TABLE_PROFILE_STUDENTS");
			Statement statementSelectSkills = c.createStatement();
			String sqlSelectSkills = "SELECT * FROM TABLE_SKILLS_STUDENT INNER JOIN " + 
									 "TABLE_PROFILE_STUDENTS ON TABLE_SKILLS_STUDENT.STUDENT_ID=TABLE_PROFILE_STUDENTS.ID " +
									 "WHERE ID = '" + eID + "';";

			ResultSet rsSkills = statementSelectSkills.executeQuery(sqlSelectSkills);
			
			while(rsSkills.next()) {
				String sID		 = rsSkills.getString(1);
				String sCategory = rsSkills.getString(2);
				String sSkill	 = rsSkills.getString(3);
				listProfileSkills.add(new SkillStud(sCategory, sSkill));
			}
			statementSelectSkills.close();
			c.close();
			System.out.println("Successful Find Skills from TABLE_SKILLS_STUDENT / TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		return listProfileSkills;
	}
	
	
	/**
	 * Finds a student's profile in the database based off of a passed email value and updates their name to the passed
	 * newName value in TABLE_PROFILE_STUDENTS.
	 * @param newName New name of the student that is to be updated in TABLE_PROFILE_STUDENTS. 
	 * @param studentEmail Email of the student to find their profile in TABLE_PROFILE_STUDENTS.
	 */
	public void updateName(String newName, String studentEmail) {
		
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

			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					   								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
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
	
	/**
	 * Finds the student based off of the passed email value and updates their majors and minors in TABLE_PROFILE_STUDENTS.
	 * @param studentEmail Email of the student to find their profile in TABLE_PROFILE_STUDENTS.
	 * @param maj1 New Major 1 of the student.
	 * @param maj2 New Major 2 of the student.
	 * @param min1 New Minor 1 of the student.
	 * @param min2 New Minor 2 of the student.
	 */
	public void updateMajorsMinors(String studentEmail, String maj1, String maj2, 
								   String min1, String min2) {
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

			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					   								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Updating Profile Name in TABLE_PROFILE_STUDENTS");
			Statement statementUpdateName = c.createStatement();
			String sqlUpdateName = "UPDATE TABLE_PROFILE_STUDENTS SET" +
									"   MAJOR1 = '" + maj1 + "', MAJOR2 = '" + maj2 + 
									"', MINOR1 = '" + min1 + "', MINOR2 = '" + min2 + "' " +
									" WHERE EMAIL = '" + studentEmail + "'; ";

			statementUpdateName.executeUpdate(sqlUpdateName);
			statementUpdateName.close();
			c.close();
			System.out.println("Successful Update Major/Minor Name in TABLE_PROFILE_STUDENTS");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * Finds the student profile based off of the passed email value and updates their skills in TABLE_SKILLS_STUDENT.
	 * @param studentEmail Email of the student to find their profile in TABLE_PROFILE_STUDENTS.
	 * @param skillList List of skills of the student to be associated with the student profile in TABLE_SKILLS_STUDENT.
	 */
	public void addSkillsToProfile(String studentEmail, ArrayList<SkillStud> skillList) {
		
		String eID = "DUMMYVALUE";
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

			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + prop.getProperty("dbLocal"),
					   								   prop.getProperty("dbLocal"),  prop.getProperty("dbLocalPassword"));
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Selecting on Email from TABLE_PROFILE_STUDENTS");
			Statement statementSelectEmail = c.createStatement();
			String sqlSelectEmail = "SELECT * FROM TABLE_PROFILE_STUDENTS WHERE EMAIL ='" + studentEmail + "';";

			ResultSet rsEmail = statementSelectEmail.executeQuery(sqlSelectEmail);
			
			while(rsEmail.next()) { eID	= rsEmail.getString(1); }
			statementSelectEmail.close();
			
			System.out.println("Successful Find Email from TABLE_PROFILE_STUDENTS");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Removing Skills from TABLE_SKILLS_STUDENT");
			Statement statementRemoveSkills = c.createStatement();
			String sqlRemoveSkills= "DELETE FROM TABLE_SKILLS_STUDENT WHERE STUDENT_ID = '" + eID + "'; ";

			statementRemoveSkills.executeUpdate(sqlRemoveSkills);
			statementRemoveSkills.close();
			
			System.out.println("Successful Skills Removal from TABLE_SKILLS_STUDENT");
			System.out.println("-----------------------------------------------------------------");
			
			System.out.println("Adding Skills for " + eID + " to TABLE_SKILLS_STUDENT");
			
			for (int i = 0; i < skillList.size(); i++) {
				Statement statementAddSkills = c.createStatement();
				String sqlAddSkills = "INSERT INTO TABLE_SKILLS_STUDENT "
						+ "(STUDENT_ID,CATEGORY,SKILL_NAME) VALUES "
						+ "(" + eID + ", '" + skillList.get(i).skillCategory + "', '" + skillList.get(i).skillName + "');";
				statementAddSkills.executeUpdate(sqlAddSkills);
				statementAddSkills.close();
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * Gets the entire list of skills that are in TABLE_SKILLS_MASTER that can be added to a student profile.
	 * @return The entire list of skills that are in TABLE_SKILLS_MASTER that can be added to a student profile.
	 */
	public ArrayList<SkillStud> loadAllSkills() {
		ArrayList<SkillStud> skillsList = new ArrayList<SkillStud>();
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

			Class.forName("org.postgresql.Driver");
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
	
	/**
	 * Get the values of the student profile.
	 * @return listProfileInfo The list of values pertaining to a student profile.
	 */
	public ArrayList<String> getProfileInformation() {
		return listProfileInfo;
	}
}
