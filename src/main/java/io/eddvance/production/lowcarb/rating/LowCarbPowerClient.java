package io.eddvance.production.lowcarb.rating;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="low-carb-power")
public interface LowCarbPowerClient {
    @GetMapping("/low-carb-power")
    String getGreenEnergyRate();
}