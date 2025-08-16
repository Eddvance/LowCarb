package io.eddvance.production.lowcarb.consumption;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class ConsumptionController {

    private final ConsumptionService consumptionService;
    private final Counter requestCounter;

    public ConsumptionController(ConsumptionService consumptionService, MeterRegistry meterRegistry) {
        this.consumptionService = consumptionService;
        // Créer le counter directement ici
        this.requestCounter = Counter.builder("lowcarb.requests.total")
                .description("Total number of requests to /low-carb endpoint")
                .register(meterRegistry);
    }

    @PostMapping("/low-carb")
    public Mono<ConsumptionResponse> postConsumptionEstimate(
            @RequestBody ConsumptionRequest request) {
        requestCounter.increment();
        return consumptionService.calculateConsumption(
                request.getEmail(),
                request.getRatingRequest()
        );
    }
}