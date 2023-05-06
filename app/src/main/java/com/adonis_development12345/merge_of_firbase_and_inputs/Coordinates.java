package com.adonis_development12345.merge_of_firbase_and_inputs;

public class Coordinates {

    private String id;
    private String addressesList;
    private String municipalitiesList;
    private Double longitudesList;
    private Double latitudesList;

    public Coordinates() {
    }

    public Coordinates(String id, String addressesList, String municipalitiesList, Double longitudesList, Double latitudesList) {
        this.id = id;
        this.addressesList = addressesList;
        this.municipalitiesList = municipalitiesList;
        this.longitudesList = longitudesList;
        this.latitudesList = latitudesList;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAddressesList() {
        return addressesList;
    }
    public void setAddressesList(String addressesList) {
        this.addressesList = addressesList;
    }

    public String getMunicipalitiesList() {
        return municipalitiesList;
    }
    public void setMunicipalitiesList(String municipalitiesList) {
        this.municipalitiesList = municipalitiesList;
    }

    public Double getLongitudesList() {
        return longitudesList;
    }
    public void setLongitudesList(Double longitudesList) {
        this.longitudesList = longitudesList;
    }

    public Double getLatitudesList() {
        return latitudesList;
    }
    public void setLatitudesList(Double latitudesList) {
        this.latitudesList = latitudesList;
    }
}
