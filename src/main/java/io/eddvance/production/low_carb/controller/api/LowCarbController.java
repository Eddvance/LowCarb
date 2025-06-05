package io.eddvance.production.low_carb.controller.api;

import io.eddvance.production.low_carb.dto.user_response_dto.UserResponseDto;
import io.eddvance.production.low_carb.service.LowCarbService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
public class LowCarbController {

    private final LowCarbService lowCarbService;

    public LowCarbController(LowCarbService lowCarbService) {
        this.lowCarbService = lowCarbService;
    }

    @GetMapping("/low-carb")
    public Mono<UserResponseDto> getConsumptionEstimate(
            @RequestParam("email") String email,
            @RequestParam("ratingRequest") Double ratingRequest) {

        return lowCarbService.calculateConsumption(email, ratingRequest);
    }
}