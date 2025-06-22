package io.eddvance.production.lowcarb.ratecollector.api;

import io.eddvance.production.lowcarb.ratecollector.service.RateHistoryService;
import io.eddvance.production.lowcarb.ratecollector.model.RateRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/rates")
public class RateCollectorController {

    private final RateHistoryService rateHistoryService;

    public RateCollectorController(RateHistoryService rateHistoryService) {
        this.rateHistoryService = rateHistoryService;
    }

    @GetMapping("last-rate")
    public Mono<RateRecord> rateConsultRecord() {
        return rateHistoryService.getLatestRate();

    }
    @GetMapping("/collect-now")
    public void forceCollect() {
        rateHistoryService.saveCurrentRate()
                .subscribe(
                        rate -> System.out.println("✅ Collecte manuelle réussie: " + rate.getRate()),
                        error -> System.err.println("❌ Erreur collecte manuelle: " + error.getMessage())
                );
    }
}