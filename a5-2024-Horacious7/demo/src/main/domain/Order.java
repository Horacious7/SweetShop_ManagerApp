package main.domain;

import java.io.Serializable;

public class Order<ID> implements Identifiable<ID>, Serializable {
    private ID id;
    private ID cakeId;
    private String orderDate;
    private String customerName;
    private String address;
    private String status;
    private double price;

    public Order(ID id, ID cakeId, String orderDate, String customerName, String address, String status, double price) {
        this.id = id;
        this.cakeId = cakeId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.address = address;
        this.status = status;
        this.price = price;
    }

    @Override
    public ID getId() {
        return id;
    }

    public ID getCakeId() {
        return cakeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public void setCakeId(ID cakeId) {
        this.cakeId = cakeId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return id + "," + cakeId+ "," + orderDate + "," + customerName + "," + address + "," + status + "," + price ;
    }
}