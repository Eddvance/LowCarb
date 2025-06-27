package io.eddvance.production.lowcarb.consumption;

import io.eddvance.production.lowcarb.pricing.service.RateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ConsumptionService {

    private final RateService rateService;
    private final ConsumptionHistoryRepository consumptionHistoryRepository;

    public ConsumptionService(RateService rateService, ConsumptionHistoryRepository consumptionHistoryRepository) {
        this.rateService = rateService;
        this.consumptionHistoryRepository = consumptionHistoryRepository;
    }

    private static final double CARBON_PART = 0.19;
    private static final double GREEN_PART = 0.81;

    public Mono<ConsumptionResponse> calculateConsumption(String email, Double consumption) {
        return rateService.getAllRates()
                .flatMap(rates -> {
                    Double carbonRate = rates.getT1();
                    Double greenRate = rates.getT2();
                    Double carbonConsumption = CARBON_PART * consumption;
                    Double greenConsumption = GREEN_PART * consumption;
                    Double finalCost = (carbonConsumption * carbonRate) + (greenConsumption * greenRate);


                    ConsumptionHistory history = new ConsumptionHistory(email, consumption, carbonRate, greenRate, finalCost);

                    return consumptionHistoryRepository.save(history)
                            .map(saved -> {ConsumptionResponse response = new ConsumptionResponse();
                                response.setEmail(email);
                                response.setRatingRequest(String.valueOf(consumption));
                                response.setConsummation(String.format("%.2f kWh (%.2f kWh carbone @ %.2f€/kWh, %.2f kWh vert @ %.2f€/kWh)",
                                        consumption, carbonConsumption, carbonRate, greenConsumption, greenRate));
                                response.setRate(String.format("%.2f €", finalCost));
                                return response;
                            });
                });
    }
}