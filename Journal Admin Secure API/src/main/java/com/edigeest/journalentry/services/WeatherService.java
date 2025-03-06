package com.edigeest.journalentry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edigeest.journalentry.api.WeatherResponse;
import com.edigeest.journalentry.cache.AppCache;
import com.edigeest.journalentry.constants.Placeholder;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.appCache
                .get(AppCache.keys.WEATHER_API.toString())
                .replace(Placeholder.CITY, city)
                .replace(Placeholder.API_KEY, apiKEY);

        ResponseEntity<WeatherResponse> response = restTemplate
                .exchange(
                        finalAPI,
                        HttpMethod.GET,
                        null,
                        WeatherResponse.class);

        return response.getBody();
    }
}
