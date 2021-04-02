package com.example.project_1.Models;

public class KHchi {
    private String maDuChi;
    private String userName;
    private String soTienDuChi;
    private String ngayDuChi;
    private String chuThich;
    private String status;

    public KHchi() {
    }

    public KHchi(String maDuChi, String userName, String soTienDuChi, String ngayDuChi, String chuThich, String status) {
        this.maDuChi = maDuChi;
        this.userName = userName;
        this.soTienDuChi = soTienDuChi;
        this.ngayDuChi = ngayDuChi;
        this.chuThich = chuThich;
        this.status = status;
    }

    public String getMaDuChi() {
        return maDuChi;
    }

    public void setMaDuChi(String maDuChi) {
        this.maDuChi = maDuChi;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSoTienDuChi() {
        return soTienDuChi;
    }

    public void setSoTienDuChi(String soTienDuChi) {
        this.soTienDuChi = soTienDuChi;
    }

    public String getNgayDuChi() {
        return ngayDuChi;
    }

    public void setNgayDuChi(String ngayDuChi) {
        this.ngayDuChi = ngayDuChi;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "KHchi{" +
                "maDuChi='" + maDuChi + '\'' +
                ", userName='" + userName + '\'' +
                ", soTienDuChi='" + soTienDuChi + '\'' +
                ", ngayDuChi='" + ngayDuChi + '\'' +
                ", chuThich='" + chuThich + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
