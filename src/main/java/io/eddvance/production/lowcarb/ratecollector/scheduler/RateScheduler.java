package io.eddvance.production.lowcarb.ratecollector.scheduler;

import io.eddvance.production.lowcarb.ratecollector.service.RateHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateScheduler {

    private final RateHistoryService rateHistoryService;

    public RateScheduler(RateHistoryService rateHistoryService) {
        this.rateHistoryService = rateHistoryService;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void collectHourlyRate() {
        rateHistoryService.saveCurrentRate().subscribe(
                rate -> System.out.println("✅ Tarif: " + rate.getRate()),
                error -> System.err.println("❌ Erreur: " + error.getMessage())
        );
    }
}
