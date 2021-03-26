package com.example.project_1.Models;

public class NguoiDung {
    private String userName;
    private String password;
    private String hoTen;
    private String gioiTinh;
    private String phone;
    private String tongSoTien;

    public NguoiDung() {
    }

    public NguoiDung(String userName, String password, String hoTen, String gioiTinh, String phone) {
        this.userName = userName;
        this.password = password;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTongSoTien() {
        return tongSoTien;
    }

    public void setTongSoTien(String tongSoTien) {
        this.tongSoTien = tongSoTien;
    }

    @Override
    public String toString() {
        return getUserName() + "  |  " + getHoTen();
    }
}
