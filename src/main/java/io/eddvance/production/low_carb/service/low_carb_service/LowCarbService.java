package io.eddvance.production.low_carb.service.low_carb_service;

import io.eddvance.production.low_carb.dto.user_response_dto.UserResponseDto;
import io.eddvance.production.low_carb.service.get_rate_service.GetRateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LowCarbService {

    private final GetRateService getRateService;

    public LowCarbService(GetRateService getRateService) {
        this.getRateService = getRateService;
    }

    private static final double CARBON_PART = 0.19;
    private static final double GREEN_PART = 0.81;

    public Mono<UserResponseDto> calculateConsumption(String email, Double consumption) {
        return getRateService.getAllRates()
                .map(rates -> {
                    Double carbonRate = rates.getT1();
                    Double greenRate = rates.getT2();
                    double carbonConsumption = CARBON_PART * consumption;
                    double greenConsumption = GREEN_PART * consumption;
                    double finalCost = (carbonConsumption * carbonRate) + (greenConsumption * greenRate);

                    UserResponseDto response = new UserResponseDto();
                    response.setEmail(email);
                    response.setConsummation(String.format("%.2f kWh (%.2f kWh carbone @ %.2f€/kWh, %.2f kWh vert @ %.2f€/kWh)", consumption, carbonConsumption, carbonRate, greenConsumption, greenRate));
                    response.setRate(String.format("%.2f €", finalCost));
                    return response;
                });
    }
}