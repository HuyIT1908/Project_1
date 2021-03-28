package com.example.project_1.Models;

public class Chi {
    private String maChiTieu;
    private String userName;
    private String soTienChi;
    private String ngayChi;
    private String chuThich;

    public Chi() {
    }

    public Chi(String maChiTieu, String userName, String soTienChi, String ngayChi, String chuThich) {
        this.maChiTieu = maChiTieu;
        this.userName = userName;
        this.soTienChi = soTienChi;
        this.ngayChi = ngayChi;
        this.chuThich = chuThich;
    }

    public String getMaChiTieu() {
        return maChiTieu;
    }

    public void setMaChiTieu(String maChiTieu) {
        this.maChiTieu = maChiTieu;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSoTienChi() {
        return soTienChi;
    }

    public void setSoTienChi(String soTienChi) {
        this.soTienChi = soTienChi;
    }

    public String getNgayChi() {
        return ngayChi;
    }

    public void setNgayChi(String ngayChi) {
        this.ngayChi = ngayChi;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }

}
