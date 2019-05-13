package tests.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Camera
{
    @JsonProperty("id")
    private int _id;
    @JsonProperty("name")
    private String _name;
    @JsonProperty("rover_id")
    private int _rover_id;
    @JsonProperty("full_name")
    private String _full_name;

    public Camera()
    {

    }
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getRoverId() {
        return _rover_id;
    }

    public void setRoverId(int rover_id) {
        this._rover_id = rover_id;
    }

    public String getFullName() {
        return _full_name;
    }

    public void setFullName(String full_name) {
        this._full_name = full_name;
    }
}

