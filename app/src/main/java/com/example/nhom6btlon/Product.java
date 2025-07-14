package com.example.nhom6btlon;

public class Product {
    public int id;
    public String name;
    public String price;
    public int image;

    public Product(int id, String name, String price, int image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return image;
    }
}
