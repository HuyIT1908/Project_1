package com.example.project_1.Models;

public class Thu {
    private String maThuNhap;
    private String userName;
    private String soTienThu;
    private String ngayNhanTien;
    private String chuThich;

    public Thu() {
    }

    public Thu(String maThuNhap, String userName, String soTienThu, String ngayNhanTien, String chuThich) {
        this.maThuNhap = maThuNhap;
        this.userName = userName;
        this.soTienThu = soTienThu;
        this.ngayNhanTien = ngayNhanTien;
        this.chuThich = chuThich;
    }

    public String getMaThuNhap() {
        return maThuNhap;
    }

    public void setMaThuNhap(String maThuNhap) {
        this.maThuNhap = maThuNhap;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSoTienThu() {
        return soTienThu;
    }

    public void setSoTienThu(String soTienThu) {
        this.soTienThu = soTienThu;
    }

    public String getNgayNhanTien() {
        return ngayNhanTien;
    }

    public void setNgayNhanTien(String ngayNhanTien) {
        this.ngayNhanTien = ngayNhanTien;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }

    @Override
    public String toString() {
        return "Thu{" +
                "maThuNhap='" + maThuNhap + '\'' +
                ", userName='" + userName + '\'' +
                ", soTienThu='" + soTienThu + '\'' +
                ", ngayNhanTien='" + ngayNhanTien + '\'' +
                ", chuThich='" + chuThich + '\'' +
                '}';
    }
}
