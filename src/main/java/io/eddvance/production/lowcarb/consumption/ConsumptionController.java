package io.eddvance.production.lowcarb.consumption;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:80")
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @GetMapping("/low-carb")
    public Mono<UserResponseDto> getConsumptionEstimate(
            @RequestParam("email") String email,
            @RequestParam("ratingRequest") Double ratingRequest) {

        return consumptionService.calculateConsumption(email, ratingRequest);
    }

}
