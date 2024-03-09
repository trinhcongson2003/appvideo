package com.example.myapplication;

public class Video {

    private String IdVD;
    private String TenVD;
    private String VDURL;
    private String Thumbnail;
    private int Timeline;
    private int TongTG;
    private int IdHistory;
    public Video(String idVD, String tenVD, String VDURL, String thumbnail, int timeline, int tongTG, int idHistory) {
        IdVD = idVD;
        TenVD = tenVD;
        this.VDURL = VDURL;
        Thumbnail = thumbnail;
        Timeline = timeline;
        TongTG = tongTG;
        IdHistory = idHistory;
    }
    public String getIdVD() {
        return IdVD;
    }

    public void setIdVD(String idVD) {
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

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
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
