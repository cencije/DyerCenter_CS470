package com.msn.gabrielle.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.msn.gabrielle.ui.views.Student.SkillStud;

/**
 * Projects.java - a simple class for creating Project Objects
 * @author Dyer Center Senior Design
 *
 */
public class Projects {
	
	private String projectIDSQL; // project id in database
	
    private String projectTitle = ""; //project title
    
    private String description = ""; //project description
    
    private String proposedBy = ""; //project proposer's name 
   
    private String location = ""; // location of project
    
    private String startDate = ""; // first date of the project
    
    private String endDate = ""; //second date of the project
    
    private String datePosted = ""; //date the project was posted 
    
    private String paid = ""; // either "Paid", "Unpaid", or "Unknown
    
    private String timePosted = ""; //time project proposal was posted 
    
    private List<String> skills; //List of skills required for the project
    
    List<SkillStud> listSkillStud; //List of skills required for the project
    
    /**
     * Constructor with no inputs
     */
    public Projects() {
        skills = new ArrayList<String>();
    }
    
    /**
     * Constructor that takes in a project title
     * 
     * @param projectTitle is the title of the project getting created
     */
    public Projects(String projectTitle) {
        this.projectTitle = projectTitle;
        skills = new ArrayList<String>();
    }
    
    /**
     * Constructor that takes in every parameter necessary to create a project, besides the list
     * of skills a project requires
     * 
     * @param id is the project id
     * @param projectTitle is the title of the project
     * @param startDate is the first date for the project
     * @param endDate is the second date (end) for the project
     * @param location is the location of where the project is
     * @param description is the description of the project
     * @param paid is either "Paid" "Unpaid" or "Unknown"
     * @param proposedBy is the name of who proposed the project
     * @param datePosted is the date the project was posted on
     */
    public Projects (String id, String projectTitle, String startDate, String endDate, String location,
    				 String description, String paid, String proposedBy, String datePosted) {
    	this.projectIDSQL = id;
    	this.projectTitle = projectTitle;
    	this.description = description;
    	this.paid = paid;
    	this.proposedBy = proposedBy;
    	this.location = location;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.datePosted = datePosted;
    	skills = new ArrayList<String>();
    }
    
    /**
     * Gets the value of the project id
     *
     * @return the value of the project id
     */
    public String getProjectIDSQL() {
        return projectIDSQL;
    }
    
    /**
     * Gets the value of the project title
     *
     * @return the value of the project title
     */
    public String getProjectTitle() {
        return projectTitle;
    }

    /**
     * Sets the value the project title
     *
     * @param projectTitle New value of the project title.
     */
    public void setProjectTitle(String projectTitle) {
    	System.out.println("Title:" + projectTitle);
        this.projectTitle = projectTitle;
    }
    
    /**
     * Gets the value of the project proposer
     *
     * @return the value of the project proposer
     */
    public String getProposedBy() {
        return proposedBy;
    }

    /**
     * Sets the value of the project proposer
     *
     * @param proposedBy new value of the project proposer
     */
    public void setProposedBy(String proposedBy) {
    	System.out.println("Name:" + proposedBy);
        this.proposedBy = proposedBy;
    }
    
    /**
     * Gets the value of the project description
     *
     * @return the value of the project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value name
     *
     * @param description
     *            new value of description
     */
    public void setDescription(String description) {
    	System.out.println("Description:" + description);
        this.description = description;
    }
    
    /**
     * Method to print out a project 
     */
    @Override
    public String toString() {
        return "Project{" + getProjectTitle() + '}';
    }
    
    /**
     * Get the location of the project 
     * 
     * @return the location
     */
    public String getLocation() {
    	return location;
    }
    
    /**
     * Set the location for the project proposal 
     * 
     * @param loc is the string to set for location
     */
    public void setLocation(String loc) {
    	System.out.println("location:" + loc);
    	location = loc;
    }
    
    /**
     * Get the start date for a project 
     * 
     * @return start date of the project
     */
    public String getStartDate() {
    	return startDate;
    }
    
    /**
     * Set the start date of the project
     * 
     * @param sD is the start date to be set
     */
    public void setStartDate(String sD) {
    	System.out.println("Start Date:" + sD);
    	startDate = sD;
    }
    
    /**
     * Get the end date for a project 
     * 
     * @return end date of the project
     */
    public String getEndDate() {
    	return endDate;
    }
    
    /**
     * Set the end date of the project
     * 
     * @param eD is the start date to be set.
     */
    public void setEndDate(String eD) {
    	System.out.println("end Date:" + eD);
    	endDate = eD;
    }
    
    /**
     * Get the date the project was posted 
     * 
     * @return date the project was posted 
     */
    public String getDatePosted() {
    	return datePosted;
    }
    
    /**
     * Set the date the project was posted 
     * 
     * @param pD date the project was posted to be set
     */
    public void setDatePosted(String pD) {
    	System.out.println("Posted Date:" + pD);
    	datePosted = pD;
    } 
    
    /**
     * Get the time the project was posted 
     * 
     * @return time the project was posted 
     */
    public String getTimePosted() {
    	return timePosted;
    }
    
    /**
     * Set the salary for the project 
     * 
     * @param sal is the salary for the project 
     */
    public void setPay(String sal) {
    	System.out.println("Pay:" + sal);
    	paid = sal;
    }
    
    /**
     * Get the salary for the project 
     * 
     * @return the salary for the project  
     */
    public String getPay() {
    	return paid;
    }
     
    /**
     * Gets the skills list of strings 
     * 
     * @return list of skills String
     */
    public List<String> getSkillsSet(){
    	return skills;
    }
    
    /**
     * Sets the project skill set
     * @param set is the string list of skills
     */
    public void setSkillsSet(Set<String> set){
    	skills = new ArrayList<String>(); 
        for (String x : set) 
          skills.add(x); 
    }
    
    /**
     * Gets the skill list of SkillStud objects, which is the skill set 
     * 
     * @return list of skills SkillStud
     */
    public List<SkillStud> getSkillList(){
    	return listSkillStud;
    }
    
    /**
     * Set the skill list of SkillStud objects
     * 
     * @param skillsList is the list of SkillStud objects you are setting
     */
    public void setSkillsList(List<SkillStud> skillsList) {
    	listSkillStud = skillsList;
    }
}
