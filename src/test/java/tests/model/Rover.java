package tests.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Rover
{
    @JsonProperty("id")
    private int _id;
    @JsonProperty("name")
    private String _name;
    @JsonProperty("landing_date")
    private String _landing_date;
    @JsonProperty("launch_date")
    private String _launch_date;
    @JsonProperty("status")
    private String _status;
    @JsonProperty("max_sol")
    private int _max_sol;
    @JsonProperty("total_photos")
    private int _total_photos;
    @JsonProperty("max_date")
    private transient SimpleDateFormat _max_date;
    @JsonProperty("cameras")
    private List<Camera> _cameras = new ArrayList<>();

    public Rover()
    {

    }
    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getLandingDate() {
        return _landing_date;
    }

    public void setLandingDate(String _landing_date) {
        this._landing_date = _landing_date;
    }

    public String getLaunchDate() {
        return _launch_date;
    }

    public void setLaunchDate(String _launch_date) {
        this._launch_date = _launch_date;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    public int getMaxSol() {
        return _max_sol;
    }

    public void setMaxSol(int _max_sol) {
        this._max_sol = _max_sol;
    }

    public int getTotalPhotos() {
        return _total_photos;
    }

    public void setTotalPhotos(int _total_photos) {
        this._total_photos = _total_photos;
    }

    public SimpleDateFormat getMaxDate() {
        return _max_date;
    }

    public void setMaxDate(SimpleDateFormat _max_date) {
        this._max_date = _max_date;
    }

    public List<Camera> getCameras() {
        return _cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this._cameras = cameras;
    }


}