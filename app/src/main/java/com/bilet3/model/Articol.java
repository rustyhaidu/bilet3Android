package com.bilet3.model;

import java.io.Serializable;

public class Articol implements Serializable {
    private String titlu;
    private String abstractArticol;
    private String autori;

    public Articol(String titlu, String abstractArticol, String autori) {
        this.titlu = titlu;
        this.abstractArticol = abstractArticol;
        this.autori = autori;
    }

    public Articol() {
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAbstractArticol() {
        return abstractArticol;
    }

    public void setAbstractArticol(String abstractArticol) {
        this.abstractArticol = abstractArticol;
    }

    public String getAutori() {
        return autori;
    }

    public void setAutori(String autori) {
        this.autori = autori;
    }
}
