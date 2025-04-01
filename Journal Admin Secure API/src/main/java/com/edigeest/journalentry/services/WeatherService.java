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

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get(city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
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

            if(response.getBody() != null) {
                redisService.set("weather_of "+ city, response.getBody(), 300l);
            }
            return response.getBody();
        }

    }
}
