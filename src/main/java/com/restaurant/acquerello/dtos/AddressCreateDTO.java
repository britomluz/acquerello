package com.restaurant.acquerello.dtos;

public class AddressCreateDTO {
    private String street;
    private Integer number;
    private String zip;
    private String state;
    private String reference;

    public AddressCreateDTO() {}

    public AddressCreateDTO(String street, Integer number, String zip, String state, String reference) {
        this.street = street;
        this.number = number;
        this.zip = zip;
        this.state = state;
        this.reference = reference;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
