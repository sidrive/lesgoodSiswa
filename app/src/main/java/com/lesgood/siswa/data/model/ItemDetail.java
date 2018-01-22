package com.lesgood.siswa.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetail {

@SerializedName("price")
@Expose
private Integer price;
@SerializedName("quantity")
@Expose
private Integer quantity;
@SerializedName("name")
@Expose
private String name;

public Integer getPrice() {
return price;
}

public void setPrice(Integer price) {
this.price = price;
}

public Integer getQuantity() {
return quantity;
}

public void setQuantity(Integer quantity) {
this.quantity = quantity;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

}