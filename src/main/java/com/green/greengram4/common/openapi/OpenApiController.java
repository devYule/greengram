package com.green.greengram4.common.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.greengram4.common.openapi.model.ApartmentTransactionDetailVo;
import com.green.greengram4.common.openapi.model.ApartmentTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping("apartment")
    public List<ApartmentTransactionDetailVo> getInfo(ApartmentTransactionDto dto) throws JsonProcessingException {
        return openApiService.getApartmentTransactionList(dto);
    }
}
