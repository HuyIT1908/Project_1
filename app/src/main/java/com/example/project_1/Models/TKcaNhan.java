package com.example.project_1.Models;

public class TKcaNhan {
    private String maNguoiDung;
    private String tenNguoiDung;
    private String user_Name;
    private String tongSoTien;

    public TKcaNhan() {
    }

    public TKcaNhan(String maNguoiDung, String tenNguoiDung, String user_Name, String tongSoTien) {
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.user_Name = user_Name;
        this.tongSoTien = tongSoTien;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getTongSoTien() {
        return tongSoTien;
    }

    public void setTongSoTien(String tongSoTien) {
        this.tongSoTien = tongSoTien;
    }
}
