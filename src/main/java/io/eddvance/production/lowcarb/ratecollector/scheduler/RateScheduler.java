package io.eddvance.production.lowcarb.ratecollector.scheduler;

import io.eddvance.production.lowcarb.ratecollector.service.RateHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RateScheduler {

    private final RateHistoryService rateHistoryService;

    public RateScheduler(RateHistoryService rateHistoryService) {
        this.rateHistoryService = rateHistoryService;
    }

    @Scheduled(cron = "${scheduler.rate.cron}")
    public void collectHourlyRate() {
        System.out.println("🔄 Scheduler déclenché à " + LocalDateTime.now());
        try {
            rateHistoryService.saveCurrentRate()
                    .doOnNext(rate -> System.out.println("✅ Tarif: " + rate.getRate()))
                    .doOnError(error -> {
                        System.err.println("❌ Erreur: " + error.getMessage());
                        error.printStackTrace();
                    })
                    .block();
        } catch (Exception e) {
            System.err.println("💥 Exception dans le scheduler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}