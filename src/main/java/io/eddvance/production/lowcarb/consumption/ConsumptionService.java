package io.eddvance.production.lowcarb.consumption;

import io.eddvance.production.lowcarb.consumption.UserResponseDto;
import io.eddvance.production.lowcarb.rating.RateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ConsumptionService {

    private final RateService rateService;

    public ConsumptionService(RateService rateService) {
        this.rateService = rateService;
    }

    private static final double CARBON_PART = 0.19;
    private static final double GREEN_PART = 0.81;

    public Mono<UserResponseDto> calculateConsumption(String email, Double consumption) {
        return rateService.getAllRates()
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