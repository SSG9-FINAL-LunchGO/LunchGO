package com.example.LunchGo.map.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoGeoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 주소별 좌표를 캐싱하여 API 호출 최소화
    private final Map<String, GeoCoordinate> addressCache = new ConcurrentHashMap<>();

    @Value("${kakao.rest-api-map-key:}")
    private String kakaoRestApiKey;

    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public GeoCoordinate getCoordinateByAddress(String address) {
        if (address == null || address.isBlank()) {
            return null;
        }

        if (addressCache.containsKey(address)) {
            return addressCache.get(address);
        }

        if (kakaoRestApiKey == null || kakaoRestApiKey.isBlank()) {
            log.debug("Kakao REST API Key is missing. Skipping geocoding for: {}", address);
            return null;
        }

        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(KAKAO_API_URL)
                    .queryParam("query", address)
                    .build()
                    .encode()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode documents = root.path("documents");

            if (documents.isArray() && !documents.isEmpty()) {
                JsonNode firstMatch = documents.get(0);
                double longitude = firstMatch.path("x").asDouble();
                double latitude = firstMatch.path("y").asDouble();

                GeoCoordinate coordinate = new GeoCoordinate(latitude, longitude);
                addressCache.put(address, coordinate);
                return coordinate;
            }

        } catch (Exception e) {
            log.warn("Failed to geocode address: {} - {}", address, e.getMessage());
        }

        return null;
    }

    public record GeoCoordinate(double latitude, double longitude) {}
}
