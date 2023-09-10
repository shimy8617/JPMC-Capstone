package com.techelevator.model;

import java.util.List;

public class PreferencesDto {

    private int userId;
    private List<Integer> typeIdList;


    public PreferencesDto(int userId, List<Integer> typeIdList) {
        this.userId = userId;
        this.typeIdList = typeIdList;
    }

    public PreferencesDto() {

    }

    public List<Integer> getTypeIdList() {
        return typeIdList;
    }

    public void setTypeIdList(List<Integer> typeIdList) {
        this.typeIdList = typeIdList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



}
