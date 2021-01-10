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
    private boolean vertebrate;
    private String classification;

    //Constructors
    public Animal() {
    }

    public Animal(String animalId, String name, String species, Date dateOfBirth, boolean vertebrate, String classification) {
        this.animalId = animalId;
        this.name = name;
        this.kind = species;
        this.dateOfBirth = dateOfBirth;
        this.vertebrate = vertebrate;
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
        return vertebrate;
    }

    public void setVertebrate(boolean vertebrate) {
        this.vertebrate = vertebrate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
