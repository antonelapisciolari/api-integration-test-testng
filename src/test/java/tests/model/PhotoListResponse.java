package tests.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PhotoListResponse
{

    @JsonProperty("photos")
    public List<Photo> photos;

    public List<Photo> getPhotos()
    {
        return photos;
    }

    public void setPhotos(List<Photo> photos)
    {
        this.photos = photos;
    }

    public PhotoListResponse()
    {

    }
    public PhotoListResponse(List<Photo> photos)
    {
        setPhotos(photos);
    }
}
