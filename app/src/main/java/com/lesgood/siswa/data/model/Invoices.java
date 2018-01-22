package com.lesgood.siswa.data.model;

public class Invoices{
	private int amount;
	private int tarif;
	private int total;
	private String code;
	private String iid;
	private int totalSiswa;
	private double fee;
	private int totalPertemuan;
	private int discount;
	private String oid;
	private String status;

	public Invoices() {
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setTarif(int tarif){
		this.tarif = tarif;
	}

	public int getTarif(){
		return tarif;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setIid(String iid){
		this.iid = iid;
	}

	public String getIid(){
		return iid;
	}

	public void setTotalSiswa(int totalSiswa){
		this.totalSiswa = totalSiswa;
	}

	public int getTotalSiswa(){
		return totalSiswa;
	}

	public void setFee(double fee){
		this.fee = fee;
	}

	public double getFee(){
		return fee;
	}

	public void setTotalPertemuan(int totalPertemuan){
		this.totalPertemuan = totalPertemuan;
	}

	public int getTotalPertemuan(){
		return totalPertemuan;
	}

	public void setDiscount(int discount){
		this.discount = discount;
	}

	public int getDiscount(){
		return discount;
	}

	public void setOid(String oid){
		this.oid = oid;
	}

	public String getOid(){
		return oid;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
