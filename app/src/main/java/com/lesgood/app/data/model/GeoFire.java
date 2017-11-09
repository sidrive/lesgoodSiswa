package com.lesgood.app.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by sim-x on 11/9/17.
 */

public class GeoFire {
  @SerializedName(".priority")
  @Expose
  private String priority;
  @SerializedName("g")
  @Expose
  private String g;
  @SerializedName("l")
  @Expose
  private List<Double> l = null;

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getG() {
    return g;
  }

  public void setG(String g) {
    this.g = g;
  }

  public List<Double> getL() {
    return l;
  }

  public void setL(List<Double> l) {
    this.l = l;
  }
}
