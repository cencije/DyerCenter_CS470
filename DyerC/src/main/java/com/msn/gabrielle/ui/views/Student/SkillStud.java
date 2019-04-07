package com.msn.gabrielle.ui.views.Student;

import java.io.Serializable;

public class SkillStud implements Serializable {

	String skillMajor;
	String skillName;
	
	public SkillStud(String major, String name) {
		skillMajor = major;
		skillName = name;
	}
	
	public String getMajor() { return skillMajor; }
	public String getName()  { return skillName; }
	
}
