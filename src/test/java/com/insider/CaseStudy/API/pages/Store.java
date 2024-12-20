package com.insider.CaseStudy.API.pages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Store {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("petId")
    private Integer petId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("shipDate")
    private String shipDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("complete")
    private boolean complete;

    // Default Constructor
    public Store() {}

    // Parameterized Constructor
    public Store(Integer id, Integer petId, Integer quantity, String shipDate, String status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Store.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("petId");
        sb.append('=');
        sb.append(((this.petId == null) ? "<null>" : this.petId));
        sb.append(',');
        sb.append("quantity");
        sb.append('=');
        sb.append(((this.quantity == null) ? "<null>" : this.quantity));
        sb.append(',');
        sb.append("shipDate");
        sb.append('=');
        sb.append(((this.shipDate == null) ? "<null>" : this.shipDate));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null) ? "<null>" : this.status));
        sb.append(',');
        sb.append("complete");
        sb.append('=');
        sb.append(((!this.complete) ? "<null>" : this.complete));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
