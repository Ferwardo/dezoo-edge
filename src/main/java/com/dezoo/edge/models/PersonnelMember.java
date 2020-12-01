package com.dezoo.edge.models;

import java.util.Date;

public class PersonnelMember {
	//Fields
	private int Id;

	private String personnelId;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	private String address;
	private String postalCode;
	private String privatePhoneNumber;

	private String personelCategory;

	//Constructors
	public PersonnelMember() {
	}

	public PersonnelMember(int id, String personnelId, String firstName, String lastName, Date dateOfBirth, String address, String postalCode, String privatePhoneNumber, String personelCategory) {
		Id = id;
		this.personnelId = personnelId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.postalCode = postalCode;
		this.privatePhoneNumber = privatePhoneNumber;
		this.personelCategory = personelCategory;
	}

	public PersonnelMember(String personnelId, String firstName, String lastName, Date dateOfBirth, String address, String postalCode, String privatePhoneNumber, String personelCategory) {
		this.personnelId = personnelId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.postalCode = postalCode;
		this.privatePhoneNumber = privatePhoneNumber;
		this.personelCategory = personelCategory;
	}

//    getters and setters

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(String personelId) {
		this.personnelId = personelId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPrivatePhoneNumber() {
		return privatePhoneNumber;
	}

	public void setPrivatePhoneNumber(String privatePhoneNumber) {
		this.privatePhoneNumber = privatePhoneNumber;
	}

	public String getPersonelCategory() {
		return personelCategory;
	}

	public void setPersonelCategory(String personelCategory) {
		this.personelCategory = personelCategory;
	}
}
