package com.msn.gabrielle.ui.views.Student;

import java.io.Serializable;

public class SkillStud implements Serializable {

	public String skillCategory; // Category of skill
	public String skillName; // Name of skill 
	
	
	/**
	 * Constructor for the skill object
	 * @param category Category of the skill
	 * @param name Name of the skill
	 */
	public SkillStud(String category, String name) {
		skillCategory = category;
		skillName = name;
	}
	
	/**
	 * Gets the category of the skill
	 * @return Category of the skill
	 */
	public String getCategory() { return skillCategory; }
	
	/**
	 * Gets the name of the skill
	 * @return Name of the skill
	 */
	public String getName()  { return skillName; }
	
}
