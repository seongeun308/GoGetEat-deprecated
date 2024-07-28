package com.gogeteat.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gogeteat.dto.StoreRequestBodyDto;
import com.gogeteat.service.StoreApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class homeController {

    private final StoreApiService storeAPIService;

    @PostMapping
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
