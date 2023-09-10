package com.techelevator.model;

public class TypeOfFood {
    private int typeId;
    private String categoryName;

    public TypeOfFood() {
    }

    public TypeOfFood(int typeId, String categoryName) {
        this.typeId = typeId;
        this.categoryName = categoryName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
