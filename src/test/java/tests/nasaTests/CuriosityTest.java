package tests.nasaTests;


import com.google.gson.Gson;
import groovy.util.logging.Slf4j;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.controller.TestController;
import tests.model.PhotoListResponse;
import tests.utility.LoggerCuriosity;


@Slf4j
public class CuriosityTest extends TestController
{

    private String search_by_sol = "sol";

    private String search_by_earth_date = "earth_date";

    private int martialTotalDays = 1000;

    private int limitPhotos = 10;

    @Test
    public void tenFirstPhotosFromSolAndEarthCuriosityAreTheSameTest()
    {
        LoggerCuriosity.info("Starting Test: 'tenFirstPhotosFromSolAndEarthCuriosityAreTheSameTest' ");

        PhotoListResponse photoListBySol = getFirst10MarsBySolPhotosFromCuriosity();

        String landingDate = photoListBySol.photos.get(0).getRover().getLandingDate();

        if(landingDate==null)
            Assert.fail("The landing date from Rover is null");

        String earthDate = calculateEarthDate(landingDate,martialTotalDays);

        if(earthDate.isEmpty())
            Assert.fail("The earth date is empty");

        PhotoListResponse photoListByEarthDate = getFirst10MarsByEarthDatePhotosFromCuriosity(earthDate);

        validateMetaData(photoListBySol, photoListByEarthDate);
        validatePhotosComparingImages(photoListBySol, photoListByEarthDate);

        LoggerCuriosity.info("Test: 'tenFirstPhotosFromSolAndEarthCuriosityAreTheSameTest' finished");

    }

    @Test
    public void noCameraTook10TimesMorePhotosThanOtherCuriosityTest()
    {
        LoggerCuriosity.info("Starting Test 2: 'noCameraTook10TimesMorePhotosThanOtherCuriosityTest'");
        //List of cameras used by Curiosity
        String[] cameraSet = {"FHAZ", "RHAZ", "CHEMCAM","MAHLI", "MARDI", "NAVCAM", "MAST"};

        HashMap<String, Integer> cameraPhotoCount = getAllPhotosByCamera(cameraSet);

        LoggerCuriosity.info("Starting validation - Check if any camera made 10 times more images than any other");

        validateAmountOfPhotos(cameraPhotoCount);

        LoggerCuriosity.info("Test: 'noCameraTook10TimesMorePhotosThanOtherCuriosityTest' - finished");
    }



    public PhotoListResponse getFirst10MarsBySolPhotosFromCuriosity()
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(search_by_sol, Integer.toString(martialTotalDays));
        parameters.put("page",Integer.toString(1) );
        PhotoListResponse photoListBySol= getMarsPhotosFromCuriosityRequest(parameters);

        if(photoListBySol.photos.size()==0 || photoListBySol.photos.size()<limitPhotos)
            Assert.fail("The list of Photos by Sol is empty or has less than 10 photos");

        return new PhotoListResponse(photoListBySol.photos.subList(0, limitPhotos));
    }
    //
    // - A Martian day (Sol) is 24 h 37 m 22.663 s, that is, approximately 1.0275 Terrestrial days.
    // Conversion = 1027.5   -Curiosity Sol to Earth Date Conversion.
    //
    public String calculateEarthDate(String landingDate, int totalMartianDays)
    {
        LoggerCuriosity.info("Starting to calculate Earth date");

        double conversionTerrestrialDays = 1.0275;
        double totalEarthDays = totalMartianDays * conversionTerrestrialDays;

        String earthDate= "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            Date roverLandingDate = simpleDateFormat.parse(landingDate);
            Long earthDays = roverLandingDate.getTime() + TimeUnit.DAYS.toMillis((long)totalEarthDays);
            earthDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(earthDays));


        }
        catch(ParseException e)
        {
            LoggerCuriosity.error("Error while parsing the earth date - Message:  " + e.getMessage());
        }

        return earthDate;
    }

    public PhotoListResponse getFirst10MarsByEarthDatePhotosFromCuriosity(String earthDate)
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(search_by_earth_date, earthDate);
        parameters.put("page",Integer.toString(1) );

        PhotoListResponse photoListByEarthDate= getMarsPhotosFromCuriosityRequest(parameters);

        if(photoListByEarthDate.photos.size()==0 || photoListByEarthDate.photos.size()<limitPhotos)
            Assert.fail("The list of Photos by Earth Date is empty or has less than 10 photos");

        return new PhotoListResponse(photoListByEarthDate.photos.subList(0, limitPhotos));
    }

    public void validatePhotosComparingImages(PhotoListResponse photoListBySol,PhotoListResponse photoListByEarthDate)
    {
        LoggerCuriosity.info("Starting to validate images");
        for (int i= 0; i < limitPhotos; i++)
        {
            Assert.assertTrue(
                    compareImages(photoListBySol.photos.get(i).getImgSrc(), photoListByEarthDate.photos.get(i).getImgSrc()),
                    "This images are different: Photo from Sol: "  + photoListBySol.photos.get(i).getImgSrc()
                            + " Photo from Earth :" +photoListByEarthDate.photos.get(i).getImgSrc());
        }

    }

    public boolean compareImages(String urlImageSol, String urlImageEarth)
    {

        boolean validation = false;
        BufferedImage imgSol = null;
        BufferedImage imgEarth = null;

        try
        {
            URL urlSol = new URL(urlImageSol);
            URL urlEarth = new URL(urlImageEarth);
            imgSol = ImageIO.read(urlSol);
            imgEarth = ImageIO.read(urlEarth);
            LoggerCuriosity.info("Images successfully downloaded");
        }
        catch (IOException e)
        {
            LoggerCuriosity.error("Error while downloading the images - Message:  " + e.getMessage());
        }

        int width1 = imgSol.getWidth();
        int width2 = imgEarth.getWidth();
        int height1 = imgSol.getHeight();
        int height2 = imgEarth.getHeight();

        if ((width1 != width2) || (height1 != height2))
        {
            LoggerCuriosity.error("Error Images dimensions mismatch");
        }
        else
        {
            LoggerCuriosity.info("Images dimensions match");
            long difference = 0;
            for (int y = 0; y < height1; y++)
            {
                for (int x = 0; x < width1; x++)
                {
                    int rgbA = imgSol.getRGB(x, y);
                    int rgbB = imgEarth.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }

            // Total number of red pixels = width * height
            // Total number of blue pixels = width * height
            // Total number of green pixels = width * height
            // So total number of pixels = width * height * 3
            double total_pixels = width1 * height1 * 3;

            // Normalizing the value of different pixels
            // for accuracy(average pixels per color
            // component)
            double avg_different_pixels = difference / total_pixels;

            // There are 255 values of pixels in total
            double percentage = (avg_different_pixels / 255) * 100;

            LoggerCuriosity.info("Difference Percentage: " + percentage);
            LoggerCuriosity.info("Comparison finished");
            validation = true;
        }

        return validation;
    }

    public void validateMetaData(PhotoListResponse photoListBySol,PhotoListResponse photoListByEarthDate)
    {
        String metaDataSol = new Gson().toJson(photoListBySol);
        String metaDataEarth = new Gson().toJson(photoListByEarthDate);
        Assert.assertTrue(metaDataSol.equals(metaDataEarth),
                "Metadata is not the same - Sol MetaData: "
                        + metaDataEarth + " ---- Earth MetaData: " + metaDataEarth);
    }

    public HashMap<String, Integer> getAllPhotosByCamera(String[] cameraSet)
    {
        HashMap<String, Integer> cameraPhotoCount = new HashMap<>();

        Map<String, String> parameters = new HashMap<>();

        LoggerCuriosity.info("Get all photos by camera");

        for (int i = 0; i < cameraSet.length; i++)
        {
            parameters.clear();
            parameters.put(search_by_sol, Integer.toString(martialTotalDays));
            parameters.put("camera", cameraSet[i]);
            cameraPhotoCount.put(cameraSet[i], getMarsPhotosFromCuriosityRequest(parameters).photos.size());
        }
        return cameraPhotoCount;
    }

    public void validateAmountOfPhotos(HashMap<String, Integer> cameraPhotoCount)
    {
        for (Map.Entry<String, Integer> camera : cameraPhotoCount.entrySet())
        {
            for (Map.Entry<String, Integer> compareCamera : cameraPhotoCount.entrySet())
            {
                if(camera.getValue()!= 0)
                    Assert.assertFalse((camera.getValue()*10) < compareCamera.getValue(), "Camera Name : " + compareCamera.getKey()
                            + "["+compareCamera.getValue()+"]"
                            + " has 10 times more images than: " + camera.getKey()
                            + "["+camera.getValue()+"]");
                else
                    Assert.assertFalse((camera.getValue()+10) <= compareCamera.getValue(), "Camera Name : " + compareCamera.getKey()
                            + "["+compareCamera.getValue()+"]"
                            + " has 10 times more images than: " + camera.getKey()
                            + "["+camera.getValue()+"]");
            }
        }
    }
}
