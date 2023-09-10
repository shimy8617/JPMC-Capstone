package com.techelevator.model;

public class Restaurant {
    private int restaurantId;
    private String restaurantName;
    private String description;
    private String phoneNumber;
    private int rating;
    private String review;
    private String photoPath;
    private int zipCode;
    private String homePage;
    private String address;

    public Restaurant() {
    }

    public Restaurant(int restaurant_id, String restaurant_name,
                      String description, String phone_number,
                      int rating, String review, String photoPath,
                      int zipCode, String homePage, String address) {
        this.restaurantId = restaurant_id;
        this.restaurantName = restaurant_name;
        this.description = description;
        this.phoneNumber = phone_number;
        this.rating = rating;
        this.review = review;
        this.photoPath = photoPath;
        this.zipCode = zipCode;
        this.homePage = homePage;
        this.address = address;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurant_id) {
        this.restaurantId = restaurant_id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurant_name) {
        this.restaurantName = restaurant_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurant_id=" + restaurantId +
                ", restaurant_name='" + restaurantName + '\'' +
                ", description='" + description + '\'' +
                ", phone_number='" + phoneNumber + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", zipCode=" + zipCode +
                ", homePage='" + homePage + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
