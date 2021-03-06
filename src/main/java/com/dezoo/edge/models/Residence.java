package com.dezoo.edge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Residence {
    @JsonIgnore
    private String id;
    private String verblijfID;
    private String personeelID;
    private String dierID;
    private String name;
    private int maxDieren;
    private String bouwJaar;
    private boolean nocturnal;

    public Residence() {
    }

    public Residence(String verblijfID, String personeelID, String dierID, String name, int maxDieren, String bouwJaar, boolean nocturnal) {
        this.verblijfID = verblijfID;
        this.personeelID = personeelID;
        this.dierID = dierID;
        this.name = name;
        this.maxDieren = maxDieren;
        this.bouwJaar = bouwJaar;
        this.nocturnal = nocturnal;
    }

    public Residence(String id, String verblijfID, String personeelID, String dierID, String name, int maxDieren, String bouwJaar, boolean nocturnal) {
        this.id = id;
        this.verblijfID = verblijfID;
        this.personeelID = personeelID;
        this.dierID = dierID;
        this.name = name;
        this.maxDieren = maxDieren;
        this.bouwJaar = bouwJaar;
        this.nocturnal = nocturnal;
    }

    public String getPersoneelID() {
        return personeelID;
    }

    public void setPersoneelID(String personeelID) {
        this.personeelID = personeelID;
    }

    public String getDierID() {
        return dierID;
    }

    public void setDierID(String dierID) {
        this.dierID = dierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxDieren() {
        return maxDieren;
    }

    public void setMaxDieren(int maxDieren) {
        this.maxDieren = maxDieren;
    }

    public String getBouwJaar() {
        return bouwJaar;
    }

    public void setBouwJaar(String bouwJaar) {
        this.bouwJaar = bouwJaar;
    }

    public boolean isNocturnal() {
        return nocturnal;
    }

    public void setNocturnal(boolean nocturnal) {
        this.nocturnal = nocturnal;
    }

    public String getVerblijfID() {
        return verblijfID;
    }

    public void setVerblijfID(String verblijfID) {
        this.verblijfID = verblijfID;
    }
}
