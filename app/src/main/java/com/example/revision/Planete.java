package com.example.revision;

public class Planete {
    private String nom;
    private Integer dis;
    private int Id;
    public Planete(int id,String nom, Integer dis) {
        this.nom = nom;
        this.dis = dis;
        this.Id=id;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return
                "nom=" + nom  +
                " dis=" + dis
                ;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getDis() {
        return dis;
    }

    public void setDis(Integer dis) {
        this.dis = dis;
    }


}
