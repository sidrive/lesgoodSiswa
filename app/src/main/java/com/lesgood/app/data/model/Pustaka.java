package com.lesgood.app.data.model;

/**
 * Created by sim-x on 11/29/17.
 */

public class Pustaka {
  String name;
  String url;
  String cat;
  String level;
  String subCat;
  String code;
  int progress;

  public Pustaka(){}

  public Pustaka(String name, String url, String cat, String level, String subCat,
      String code, int progress) {
    this.name = name;
    this.url = url;
    this.cat = cat;
    this.level = level;
    this.subCat = subCat;
    this.code = code;
    this.progress = progress;
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

  public String getCat() {
    return cat;
  }

  public void setCat(String cat) {
    this.cat = cat;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getSubCat() {
    return subCat;
  }

  public void setSubCat(String subCat) {
    this.subCat = subCat;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
