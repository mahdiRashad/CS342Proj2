import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyWeatherAPI extends WeatherAPI {
    public static GridInformation getGridInfo(String latitude, String longitude) {
        String GridURL = "https://api.weather.gov/points/" + latitude + "," + longitude;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(GridURL)).build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper om = new ObjectMapper();
            Response toGet = om.readValue(response.body(), Response.class);
            return toGet.properties;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Response {
    public GridInformation properties;
}
@JsonIgnoreProperties(ignoreUnknown = true)
class GridInformation {
    public String gridId;
    public int gridX;
    public int gridY;
}

