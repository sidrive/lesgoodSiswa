package com.lesgood.app.data.model;

/**
 * Created by sim-x on 11/29/17.
 */

public class Pustaka {
  String name;
  String url;
  int progress;
  public Pustaka(){}
  public Pustaka(String name, String url, int progress) {
    this.name = name;
    this.url = url;
    this.progress = progress;
  }

  @Override
  public String toString() {
    return "Pustaka{" +
        "name='" + name + '\'' +
        ", url='" + url + '\'' +
        ", progress=" + progress +
        '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }
}
