package io.eddvance.production.low_carb.service.low_carb_service;

import feign.FeignException;
import io.eddvance.production.low_carb.client.coalfired_client.CoalFiredClient;
import io.eddvance.production.low_carb.client.low_carb_power_client.LowCarbPowerClient;
import io.eddvance.production.low_carb.dto.user_response_dto.UserResponseDto;
import io.eddvance.production.low_carb.service.get_rate_service.GetRateService;
import org.springframework.stereotype.Service;

@Service
public class LowCarbService {

   private final GetRateService getRateService;
    public LowCarbService(GetRateService getRateService) {
        this.getRateService = getRateService;
    }

    private static final double CARBON_PART = 0.19;
    private static final double GREEN_PART = 0.81;


    public UserResponseDto calculateConsumption(String email, Double consumption) {
        UserResponseDto response = new UserResponseDto();
        double carbonRate = getRateService.getCarbonRate();
        double greenRate = getRateService.getGreenRate();
        double finalCost = ((CARBON_PART * consumption) * carbonRate) + ((GREEN_PART * consumption) * greenRate);

        response.setEmail(email);
        response.setRate(String.format("%.2f €", finalCost));
        return response;
    }
}