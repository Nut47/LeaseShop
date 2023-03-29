package com.entity;

public class Bill {

    private int billid;
    private String userid;
    private String commodityname;
    private String commodityphoto;
    private Double number;
    private String commoditydes;
    private String leaseterm;
    private String state;
    private String deposit;
    private String address;
    private String stateBuy;

    public String getCommodityphoto() {
        return commodityphoto;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    private String totalprice;

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public void setLeaseterm(String leaseterm){
        this.leaseterm=leaseterm;
    }
    public String getLeaseterm(){return leaseterm;}
    public String getState(){return state;}
    public void setState(String state){
        this.state=state;
    }
    public String getCommodityname() {
        return commodityname;
    }

    public void setCommodityname(String commodityname) {
        this.commodityname = commodityname;
    }

    public void setCommodityphoto(String commodityphoto) {
        this.commodityphoto = commodityphoto;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String getCommoditydes() {
        return commoditydes;
    }

    public void setCommoditydes(String commoditydes) {
        this.commoditydes = commoditydes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateBuy() {return stateBuy;}

    public void setStateBuy(String stateBuy) {
        this.stateBuy = stateBuy;
    }

    @Override
    public String toString() {
        return "bill{" +
                "billid=" + billid +
                ", userid='" + userid + '\'' +
                ", commodityname='" + commodityname + '\'' +
                ", commodityphoto='" + commodityphoto + '\'' +
                ", number=" + number +
                ", commoditydes='" + commoditydes + '\'' +
                ", leaseterm='" + leaseterm + '\'' +
                ", deposit='" + deposit + '\'' +
                ", totalprice='" + totalprice + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", stateBuy='" + stateBuy + '\'' +
                '}';
    }
}
