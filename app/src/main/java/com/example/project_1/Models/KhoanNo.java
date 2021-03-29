package com.example.project_1.Models;

public class KhoanNo {
    private String maKhoanNo;
    private String userName;
    private String soTienNo;
    private String ngayNo;
    private String ngayTra;
    private String chuThich;

    public KhoanNo() {
    }

    public KhoanNo(String maKhoanNo, String userName, String soTienNo, String ngayNo, String chuThich) {
        this.maKhoanNo = maKhoanNo;
        this.userName = userName;
        this.soTienNo = soTienNo;
        this.ngayNo = ngayNo;
        this.chuThich = chuThich;
    }

    public String getMaKhoanNo() {
        return maKhoanNo;
    }

    public void setMaKhoanNo(String maKhoanNo) {
        this.maKhoanNo = maKhoanNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSoTienNo() {
        return soTienNo;
    }

    public void setSoTienNo(String soTienNo) {
        this.soTienNo = soTienNo;
    }

    public String getNgayNo() {
        return ngayNo;
    }

    public void setNgayNo(String ngayNo) {
        this.ngayNo = ngayNo;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }

    @Override
    public String toString() {
        return "KhoanNo{" +
                "maKhoanNo='" + maKhoanNo + '\'' +
                ", userName='" + userName + '\'' +
                ", soTienNo='" + soTienNo + '\'' +
                ", ngayNo='" + ngayNo + '\'' +
                ", ngayTra='" + ngayTra + '\'' +
                ", chuThich='" + chuThich + '\'' +
                '}';
    }
}
