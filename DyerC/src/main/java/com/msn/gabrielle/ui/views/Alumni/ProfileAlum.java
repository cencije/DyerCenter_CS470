package com.msn.gabrielle.ui.views.Alumni;

import com.msn.gabrielle.ui.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Class for the profile page for Alumni. Routes from AlumniPage.class and uses that layout 
 * @author Dyer Center Senior Design
 */
@Route(value = "profilealum", layout = AlumniPage.class)
@PageTitle("Profile")
public class ProfileAlum extends VerticalLayout{
	String profileName, profileEmail;
	
	/**
	 * Constructor for ProfileAlum. Makes the labels and layouts.
	 */
	public ProfileAlum() {
		
		loadProfileValues();
		
		//sets up the labels for profile page
		Label lblName = new Label("Name:");
		Label lblProfileName = new Label(profileName);
		Label lblEmail = new Label("Email:");
		Label lblProfileEmail = new Label(profileEmail);
		
		HorizontalLayout hlName = new HorizontalLayout();
		hlName.add(lblName); hlName.add(lblProfileName);
		HorizontalLayout hlEmail = new HorizontalLayout();
		hlEmail.add(lblEmail); hlEmail.add(lblProfileEmail);
		
		VerticalLayout vlNonGridSide = new VerticalLayout();
		vlNonGridSide.add(hlName); vlNonGridSide.add(hlEmail);
		add(vlNonGridSide);
	}
	
	/**
	 * Loads the profile values for the alumni from the database.
	 */
	public void loadProfileValues() {
		profileName = "N/A";
		profileEmail = "N/A";
		profileName = "Ferris Asher";
		profileEmail = "asherf@alumniEmail";
	}
}
