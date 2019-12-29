package com.example.styleomega.Model;

public class Cart {

    private String pid,productName,price,quantity,discount,size;

    public Cart() {

    }

    public Cart(String pid, String productName, String price, String quantity, String discount,String size) {
        this.pid = pid;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
