package io.eddvance.production.low_carb.client.coalfired_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="coalfired")
public interface CoalFiredClient {
    @GetMapping("/coalfired")
    String getGreenEnergyRate();//
}