package com.entity;

public class Buy {

    private int buyid;
    private String userid;
    private String  commodityname;
    private String commodityphoto;
    private double number;
    private String commoditydes;

    @Override
    public String toString() {
        return "buy{" +
                "buyid=" + buyid +
                ", userid='" + userid + '\'' +
                ", commodityname='" + commodityname + '\'' +
                ", commodityphoto='" + commodityphoto + '\'' +
                ", number=" + number +
                ", commoditydes='" + commoditydes + '\'' +
                '}';
    }

    public int getBillid() {
        return buyid;
    }

    public void setBillid(int billid) {
        this.buyid = billid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCommodityname() {
        return commodityname;
    }

    public void setCommodityname(String commodityname) {
        this.commodityname = commodityname;
    }

    public String getCommodityphoto() {
        return commodityphoto;
    }

    public void setCommodityphoto(String commodityphoto) {
        this.commodityphoto = commodityphoto;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getCommoditydes() {
        return commoditydes;
    }

    public void setCommoditydes(String commoditydes) {
        this.commoditydes = commoditydes;
    }
}
