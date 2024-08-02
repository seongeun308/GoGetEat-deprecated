package com.gogeteat.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {

    @Value("${kakao-api.rest-api-key}")
    private String apiKey;
    @Value("${kakao-api.store-search-uri}")
    private String path;
    @Value("${crawl-api.uri}")
    private String crawlUri;
    private final RestClient restClient;

    public JsonNode requestStoreAPI(String x, String y, int radius, int page){
        URI uri = UriComponentsBuilder.fromUriString(path)
                .queryParam("category_group_code", "FD6")    // FD6 : 음식점
                .queryParam("x", x)
                .queryParam("y", y)
                .queryParam("radius", radius)
                .queryParam("page", page)
                .queryParam("size",15)
                .build().toUri();

        log.info(uri.toString());

        return restClient.get()
                .uri(uri)
                .header("Authorization", "KakaoAK " + apiKey)
                .retrieve()
                .body(JsonNode.class);
    }

    public JsonNode getStoreInfoMetaData(JsonNode storeData){
        return storeData.get("meta");
    }

    public JsonNode getStoreInfoData(JsonNode storeData){
        return storeData.get("documents");
    }

    public ArrayNode postToCrawlingServer(List<String> urlList){
        return restClient.post().uri(crawlUri).body(urlList).retrieve().body(ArrayNode.class);
    }


}
