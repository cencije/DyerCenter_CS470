package com.msn.gabrielle.ui.views.Student;

import java.io.Serializable;

public class SkillStud implements Serializable {

	String skillCategory;
	String skillName;
	
	public SkillStud(String category, String name) {
		skillCategory = category;
		skillName = name;
	}
	
	public String getCategory() { return skillCategory; }
	public String getName()  { return skillName; }
	
}
