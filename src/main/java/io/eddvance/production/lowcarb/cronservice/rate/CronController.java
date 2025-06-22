package io.eddvance.production.lowcarb.cronservice.rate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/rates")
public class CronController {

    private final RateHistoryService rateHistoryService;

    public CronController(RateHistoryService rateHistoryService) {
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