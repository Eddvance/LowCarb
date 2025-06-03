package io.eddvance.production.low_carb.service;

import feign.FeignException;
import io.eddvance.production.low_carb.client.coalfired_client.CoalFiredClient;
import io.eddvance.production.low_carb.client.low_carb_power_client.LowCarbPowerClient;
import io.eddvance.production.low_carb.dto.user_response_dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public class LowCarbService {

    private LowCarbPowerClient lowCarbPowerClient;
    private CoalFiredClient coalFiredClient;

    public LowCarbService(LowCarbPowerClient lowCarbPowerClient, CoalFiredClient coalFiredClient) {
        this.lowCarbPowerClient = lowCarbPowerClient;
        this.coalFiredClient = coalFiredClient;
    }

    private static final double carbonPart = 0.19;
    private static final double greenPart = 0.81;

    public UserResponseDto calculateConsumption(String email, Double consumption) {
        UserResponseDto response = new UserResponseDto();
        double carbonRate = getCarbonRate();
        double greenRate = getGreenRate();
        double finalCost = ((carbonPart * consumption) * carbonRate) + ((greenPart * consumption) * greenRate);

        response.setEmail(email);
        response.setRate(String.format("%.2f €", finalCost));
        return response;
    }

    private Double getCarbonRate() {
        try {
            String rateStr = coalFiredClient.getCarbonEnergyRate();
            return Double.parseDouble(rateStr);
        } catch (FeignException e) {
            throw new RuntimeException("Service CoalFired indisponible", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Tarif carbone invalide", e);
        }
    }

    private Double getGreenRate() {
        try {
            String rateStr = lowCarbPowerClient.getGreenEnergyRate();
            return Double.parseDouble(rateStr);
        } catch (FeignException e) {
            throw new RuntimeException("Service LowCarbPower indisponible", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Tarif vert invalide", e);
        }
    }
}
