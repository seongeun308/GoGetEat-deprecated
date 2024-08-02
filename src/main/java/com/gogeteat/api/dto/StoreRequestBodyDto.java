package com.gogeteat.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class StoreRequestBodyDto {
    private String x;
    private String y;
    private int radius = 1000;
}
