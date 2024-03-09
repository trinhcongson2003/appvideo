package com.example.myapplication;

public class Video {

    private int IdVD;
    private String TenVD;
    private String VDURL;
    private int Thumbnail;
    private int Timeline;
    private int TongTG;
    private int IdHistory;
    public Video(int idVD, String tenVD, String VDurl, int thumbnail, int timeline, int tongTG, int idHistory) {
        IdVD = idVD;
        TenVD = tenVD;
        this.VDURL = VDurl;
        Thumbnail = thumbnail;
        Timeline = timeline;
        TongTG = tongTG;
        IdHistory = idHistory;
    }
    public int getIdVD() {
        return IdVD;
    }

    public void setIdVD(int idVD) {
        IdVD = idVD;
    }

    public String getTenVD() {
        return TenVD;
    }

    public void setTenVD(String tenVD) {
        TenVD = tenVD;
    }

    public String getVDURL() {
        return VDURL;
    }

    public void setVDURL(String VDURL) {
        this.VDURL = VDURL;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getTimeline() {
        return Timeline;
    }

    public void setTimeline(int timeline) {
        Timeline = timeline;
    }

    public int getTongTG() {
        return TongTG;
    }

    public void setTongTG(int tongTG) {
        TongTG = tongTG;
    }

    public int getIdHistory() {
        return IdHistory;
    }

    public void setIdHistory(int idHistory) {
        IdHistory = idHistory;
    }
}
