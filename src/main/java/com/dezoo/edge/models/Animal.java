package com.dezoo.edge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Animal {
    //Fields
    @JsonIgnore
    private int Id;
    private String animalId;
    private String name;
    private String kind;
    private Date dateOfBirth;
    private boolean isVertebrate;
    private String classification;

    //Constructors
    public Animal() {
    }

    public Animal(String animalId, String name, String species, Date dateOfBirth, boolean isVertebrate, String classification) {
        this.animalId = animalId;
        this.name = name;
        this.kind = species;
        this.dateOfBirth = dateOfBirth;
        this.isVertebrate = isVertebrate;
        this.classification = classification;
    }

    public Animal(int id, String animalId, String name, String species, Date dateOfBirth, boolean isVertebrate, String classification) {
        Id = id;
        this.animalId = animalId;
        this.name = name;
        this.kind = species;
        this.dateOfBirth = dateOfBirth;
        this.isVertebrate = isVertebrate;
        this.classification = classification;
    }

    //getters & setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isVertebrate() {
        return isVertebrate;
    }

    public void setVertebrate(boolean vertebrate) {
        isVertebrate = vertebrate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
