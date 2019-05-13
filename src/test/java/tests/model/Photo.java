package tests.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;

public class Photo
{
    @JsonProperty("id")
    private int _id;
    @JsonProperty("sol")
    private int _sol;
    @JsonProperty("camera")
    private Camera _camera;
    @JsonProperty("img_src")
    private String _img_src;
    @JsonProperty("earth_date")
    private transient SimpleDateFormat _earth_date;
    @JsonProperty("rover")
    private Rover _rover;

    public Photo()
    {

    }
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getSol() {
        return _sol;
    }

    public void setSol(int sol) {
        this._sol = sol;
    }

    public Camera getCamera() {
        return _camera;
    }

    public void setCamera(Camera camera) {
        this._camera = camera;
    }

    public String getImgSrc() {
        return _img_src;
    }

    public void setImgSrc(String img_src) {
        this._img_src = img_src;
    }

    public SimpleDateFormat getEarthDate() {
        return _earth_date;
    }

    public void setEarthDate(SimpleDateFormat earth_date) {
        this._earth_date = earth_date;
    }

    public Rover getRover() {
        return _rover;
    }

    public void setRover(Rover rover) {
        this._rover = rover;
    }
}

