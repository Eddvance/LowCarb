package io.eddvance.production.low_carb.service;

import io.eddvance.production.low_carb.client.coalfired_client.CoalFiredClient;
import io.eddvance.production.low_carb.client.low_carb_power_client.LowCarbPowerClient;
import org.springframework.stereotype.Service;

@Service
public class LowCarbService {

    private LowCarbPowerClient lowCarbPowerClient;
    private CoalFiredClient coalFiredClient;

    public LowCarbService(LowCarbPowerClient lowCarbPowerClient, CoalFiredClient coalFiredClient) {
        this.lowCarbPowerClient = lowCarbPowerClient;
        this.coalFiredClient = coalFiredClient;
    }


}
