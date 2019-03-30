package com.msn.gabrielle.ui.views.Student;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "profilestud", layout = StudentPage.class)
@PageTitle("Profile")
public class ProfileStud extends VerticalLayout{
	String profileName, profileMajor1, profileMajor2;
	public ProfileStud() {
		loadProfileValues();
		Label lblProfileName = new Label(profileName);
		//lblProfileName.
		add(lblProfileName);
		Label lblMajor =  new Label(profileMajor1);
		add(lblMajor);
		ComboBox<String> cbMajor1 = new ComboBox<>("Major 1");
		
		cbMajor1.setItems(
				"AB International Studies/BS Engineering Major", "Africana Studies", 
				"American Studies", "Anthropology and Sociology", "Art", "Asian Studies",
				"Biochemistry", "Biology",
				"Chemical Engineering", "Chemistry", "Civil Engineering", "Computer Science",
				"Economics", "English", "Electrical & Computer Engineering", "Engineering Studies",
				"English", "Environmental Science", "Environmental Studies",
				"Film and Media Studies", "Fo", "French",
				"Geology", "German", "Government and Law", "Government and Law: French",
				"Government and Law: German", "Government and Law: Spanish",
				"History",
				"International Affairs",
				"Mathematics", "Mathematics and Economics, A.B. Joint Major","Mechanical Engineering",
				"Military Science", "Music",
				"Neuroscience", 
				"Philosophy", "Physics", "Policy Studies", "Psychology", 
				"Religion and Politics", "Religious Studies", "Russian and East European Studies",
				"Spanish", 
				"Theater",
				"Women’s and Gender Studies",
				"Undecided");
		cbMajor1.addValueChangeListener(event -> {
		    if (!event.getSource().isEmpty()) {
		        
		    }
		});
		add(cbMajor1);
		ComboBox<String> cbMajor2 = new ComboBox<>("Major 2");
		
		cbMajor2.setItems("N/A", 
				"AB International Studies/BS Engineering Major", "Africana Studies",
				"American Studies", "Anthropology and Sociology", "Art", "Asian Studies", 
				"Biochemistry", "Biology", 
				"Chemical Engineering", "Chemistry", "Civil Engineering", "Computer Science", 
				"Economics", "English", "Electrical & Computer Engineering", "Engineering Studies", 
				"English", "Environmental Science", "Environmental Studies", 
				"Film and Media Studies", "French", 
				"Geology", "German", "Government and Law", "Government and Law: French", 
				"Government and Law: German", "Government and Law: Spanish", 
				"History", 
				"International Affairs", 
				"Mathematics", "Mathematics and Economics, A.B. Joint Major", "Mechanical Engineering", 
				"Military Science", "Music", 
				"Neuroscience",
				"Philosophy", "Physics", "Policy Studies", "Psychology",
				"Religion and Politics", "Religious Studies", "Russian and East European Studies", 
				"Spanish",
				"Theater", 
				"Women’s and Gender Studies");
		cbMajor2.addValueChangeListener(event -> {
		    if (!event.getSource().isEmpty()) {
		        
		    }
		});
		add(cbMajor2);
		ComboBox<String> cbMinor1 = new ComboBox<>("Minor 1");
		
		cbMinor1.setItems("N/A", "Africana Studies", "Aging Studies", 
				"Anthropology and Sociology", "Architectural Studies", "Art", "Asian Studies",
				"Biotechnology/Bioengineering",
				"Chemistry", "Chinese", "Computational Methods",
				"Classic Civilization", "Computer Science",
				"Economics", "English", "Environmental Science", "Environmental Studies",
				"Film and Media Studies", "French",
				"Geology", "German", "Global History", "Government and Law",  
				"Health, Life Sciences and Society", "History",
				"Italian Studies",
				"Jewish Studies",
				"Latin American and Caribbean Studies", "Literature in Translation",
				"Mathematics", "Mechanical Engineering", 
				"Medieval, Renaissance, and Early Modern Studies", "Music",
				"Philosophy", "Physics", "Psychology", 
				"Religious Studies", "Russian", "Russian and East European Studies",
				"Spanish", 
				"Theater",
				"Women’s and Gender Studies", "Writing",
				"Undecided");
		cbMinor1.addValueChangeListener(event -> {
		    if (!event.getSource().isEmpty()) {
		        
		    }
		});
		add(cbMinor1);
	}
	public void loadProfileValues() {
		profileName = "John Goodway";
		profileMajor1 = "Computer Science";
		profileMajor2 = "Spanish";
		
	}
}