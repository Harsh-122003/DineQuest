package com.example.dinequest.Domain;

public class Category {
    private int Id;
    private String ImagePath;
    private String Name;

    public Category(int Id, String ImagePath, String Name) {
        this.Id = Id;
        this.ImagePath = ImagePath;
        this.Name = Name;
    }

    public Category(){

    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
