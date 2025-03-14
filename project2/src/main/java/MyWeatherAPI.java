import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyWeatherAPI extends WeatherAPI {
    public static GridInfo getGridInfo(String latitude, String longitude) {
        String url = "https://api.weather.gov/points/" + latitude + "," + longitude;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "MyWeatherApp (your_email@example.com)") // NWS requires a User-Agent
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            PointsResponse pointsResponse = mapper.readValue(response.body(), PointsResponse.class);
            return pointsResponse.properties;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PointsResponse {
    public GridInfo properties;
}
@JsonIgnoreProperties(ignoreUnknown = true)
class GridInfo {
    public String gridId;
    public int gridX;
    public int gridY;
}

