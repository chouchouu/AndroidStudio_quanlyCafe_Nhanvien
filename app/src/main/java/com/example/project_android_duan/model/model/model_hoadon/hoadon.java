package com.example.project_android_duan.model.model.model_hoadon;

import com.example.project_android_duan.model.model.model_giohang.GioHang;

import java.io.Serializable;
import java.util.List;

public class hoadon implements Serializable {
    private String mahoadon;
    private String manhanvien;
    private List<GioHang> gioHangList;
    private String date;
    private int Total;

    public hoadon() {

    }

    public hoadon(String mahoadon, String manhanvien, List<GioHang> gioHangList, String date, int total) {
        this.mahoadon = mahoadon;
        this.manhanvien = manhanvien;
        this.gioHangList = gioHangList;
        this.date = date;
        Total = total;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public List<GioHang> getGioHangList() {
        return gioHangList;
    }

    public void setGioHangList(List<GioHang> gioHangList) {
        this.gioHangList = gioHangList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
