package io.eddvance.production.low_carb.service.get_rate_service;

import feign.FeignException;
import io.eddvance.production.low_carb.client.coalfired_client.CoalFiredClient;
import io.eddvance.production.low_carb.client.low_carb_power_client.LowCarbPowerClient;
import io.eddvance.production.low_carb.exception.GetRateException;

public class GetRateService {

    private final LowCarbPowerClient lowCarbPowerClient;
    private final CoalFiredClient coalFiredClient;
    public GetRateService(LowCarbPowerClient lowCarbPowerClient, CoalFiredClient coalFiredClient) {
        this.lowCarbPowerClient = lowCarbPowerClient;
        this.coalFiredClient = coalFiredClient;
    }

    public Double getCarbonRate() {
        try {
            String rateString = coalFiredClient.getCarbonEnergyRate();
            return Double.parseDouble(rateString);
        } catch (FeignException e) {
            throw new GetRateException("Service CoalFired indisponible",e);
        } catch (NumberFormatException e) {
            throw new GetRateException("Tarif carbone invalide", e);
        }
    }

    public Double getGreenRate() {
        try {
            String rateString = lowCarbPowerClient.getGreenEnergyRate();
            return Double.parseDouble(rateString);
        } catch (FeignException e) {
            throw new GetRateException("Service LowCarbPower indisponible", e);
        } catch (NumberFormatException e) {
            throw new GetRateException("Tarif vert invalide", e);
        }
    }
}