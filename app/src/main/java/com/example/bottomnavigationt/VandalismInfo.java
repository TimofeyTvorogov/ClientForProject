package com.example.bottomnavigationt;

public class VandalismInfo {
    private Long id;
    private Double lat;
    private Double lon;
    private String address;
    private String type;
    private String object;
    private Long votes;
    private Boolean cleaned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Boolean getCleaned() {
        return cleaned;
    }

    public void setCleaned(Boolean cleaned) {
        this.cleaned = cleaned;
    }
}
