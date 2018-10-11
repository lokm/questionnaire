package fr.nouas.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=70, nullable=false)
	private String lastname;
	@Column(length=70, nullable=false)
	private String firstname;
	@Column(length=90, nullable=false, unique=true)
	private String email;
	@Column(length=40, nullable=false)
	private String password;
	@Column(length=20, nullable=true)
	private String role;
	@ManyToMany(mappedBy="users", cascade=CascadeType.PERSIST)
	private List<Questionnaire> questionnaires = new ArrayList <Questionnaire>();
	
	public User() {}
	
	public User(String lastname, String firstname, String email, String password, String role) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.password = password;
		this.role = role;
		
	}
	
	public User(int id, String lastname, String firstname, String email, String password, String role) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}
	
	
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public List<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}

	public void setQuestionnaires(List<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}

	public void addQuestionnaire(Questionnaire questionnaire) {
		this.questionnaires.add(questionnaire);
	}
	

	public void setNom(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	
	
}
