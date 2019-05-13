package com.msn.gabrielle.ui.views.Student;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class SkillsLoader {
	ArrayList<String> listSkillCategories = new ArrayList<String>();
	ArrayList<String> listSkills = new ArrayList<String>();
 
	public SkillsLoader() {}
	public void loadInJSONValues() {
		
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        File testFile = new File("");
        String currentPath = testFile.getAbsolutePath();
        System.out.println("current path is: " + currentPath);
        System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
        try (FileReader reader = new FileReader("categories.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray arraySkills = (JSONArray) obj;
             
            //Iterate over employee array
            arraySkills.forEach( skillItem -> parseSkillObject( (JSONObject) skillItem ) );
            getListValues();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
    private void parseSkillObject(JSONObject skill) {

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
	        JSONObject skillObject = (JSONObject) skill.get("categories");
	        System.out.println(skillObject);
	        for (int i = 0; i < skillObject.keySet().size(); i++) {
	        	String heading = (String) skillObject.keySet().toArray()[i];
	        	listSkillCategories.add(heading);
	        	JSONObject skillHeading = (JSONObject) skillObject.get(heading);
	        	JSONArray skills = (JSONArray) skillHeading.get("skills");
	        	for (int j = 0; j < skills.size(); j++) {
	        		String stringSkill = (String) skills.toArray()[j];
	        		listSkills.add(stringSkill);
	        		Statement statementInsertStudent = c.createStatement();
	    			String sqlInsertStudent = "INSERT INTO TABLE_SKILLS_MASTER "
	    					+ "(SKILL_ID,CATEGORY,SKILL_NAME) VALUES "
	    					+ "(" + idNumber + ", '" + heading + "', '" + stringSkill + "');";
	    			statementInsertStudent.executeUpdate(sqlInsertStudent);
	    			statementInsertStudent.close();

	    			System.out.println("Successful Count from TABLE_SKILLS_MASTER");
	    			idNumber++;
	        	}
	        }
	        c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
    }
    
    public void getListValues() {
    	System.out.println("---------- Printing List of Categories ----------");
    	for (int i = 0; i < listSkillCategories.size(); i++) {
    		System.out.println(listSkillCategories.get(i));
    	}
    	System.out.println("---------- Printing List of Skills ----------");
    	for (int i = 0; i < listSkills.size(); i++) {
    		System.out.println(listSkills.get(i));
    	}
    }
}

 

    