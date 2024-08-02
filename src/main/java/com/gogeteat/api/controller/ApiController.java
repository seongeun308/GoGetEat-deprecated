package com.gogeteat.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gogeteat.api.dto.StoreRequestBodyDto;
import com.gogeteat.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ApiService storeAPIService;

    @PostMapping("/store")
    public ArrayNode getStoreInfo(@RequestBody StoreRequestBodyDto bodyDto) {
        boolean is_end;
        int page = 1;
        ArrayNode totalStoreInfo = new ObjectMapper().createArrayNode();
        do{
            JsonNode storeData = storeAPIService.requestStoreAPI(bodyDto.getX(), bodyDto.getY(), bodyDto.getRadius(), page);
            JsonNode storeInfoMetaData = storeAPIService.getStoreInfoMetaData(storeData);

            JsonNode storeInfoData = storeAPIService.getStoreInfoData(storeData);
            storeInfoData.forEach(totalStoreInfo::add);

            is_end = storeInfoMetaData.get("is_end").asBoolean();
            page++;
        } while(!is_end);
        return totalStoreInfo;
    }

}
