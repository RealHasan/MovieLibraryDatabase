package com.example.sqldatabase;

public class MovieModel {

   private int id;
   private String name;
   private int release;
   private boolean isActive;

   // constructors


    public MovieModel(int id, String name, int release, boolean isActive) {
        this.id = id;
        this.name = name;
        this.release = release;
        this.isActive = isActive;
    }

    public MovieModel() {
    }

    // toStrings necessary for printing the contents of class object

    @Override
    public String toString() {
        return
                name +
                ", RELEASED: " + release +" " +
                ", IN CINEMAS: " + isActive
                ;
    }


    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
