package com.lesgood.app.data.model;

/**
 * Created by sim-x on 12/19/17.
 */

public class HistoryOders {
  private String oid;
  private String gid;
  private String status;
  private long createAt;

  public HistoryOders() {
  }

  public HistoryOders(String oid, String gid, String status, long createAt) {
    this.oid = oid;
    this.gid = gid;
    this.status = status;
    this.createAt = createAt;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getGid() {
    return gid;
  }

  public void setGid(String gid) {
    this.gid = gid;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }
}
