package com.example.root.mapdemo.entity;

import android.widget.ImageView;

public class Model {

    public Model(){
        super();
        imageView = null;
    }

    private int id;
    private String modelName;
    private String fuelType;
    private float fuelPrice;
    private String categoria;
    private float bassPrice;
    private int year;
    private int passangers;
    private int luggage;
    private int cylinders;
    private boolean airConditioner;
    private String transmission;
    private float insurance;
    private float fullTank;
    private String images;
    final private ImageView imageView;


    public ImageView getImageView() {
        return imageView;
    }



    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

//    public Model(String name, int age, int photoId) {
//        this.modelName = name;
//        this.year = age;
//        this.carPhotoId = photoId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public float getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(float fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getBassPrice() {
        return bassPrice;
    }

    public void setBassPrice(float bassPrice) {
        this.bassPrice = bassPrice;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPassangers() {
        return passangers;
    }

    public void setPassangers(int passangers) {
        this.passangers = passangers;
    }

    public int getLuggage() {
        return luggage;
    }

    public void setLuggage(int luggage) {
        this.luggage = luggage;
    }

    public int getCylinders() {
        return cylinders;
    }

    public void setCylinders(int cylinders) {
        this.cylinders = cylinders;
    }

    public boolean isAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(boolean airConditioner) {
        this.airConditioner = airConditioner;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public float getInsurance() {
        return insurance;
    }

    public void setInsurance(float insurance) {
        this.insurance = insurance;
    }

    public float getFullTank() {
        return fullTank;
    }

    public void setFullTank(float fullTank) {
        this.fullTank = fullTank;
    }
}
