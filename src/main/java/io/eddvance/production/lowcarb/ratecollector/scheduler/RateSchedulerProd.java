package io.eddvance.production.lowcarb.ratecollector.scheduler;

import io.eddvance.production.lowcarb.ratecollector.service.RateHistoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("prod")
public class RateSchedulerProd {

    private final RateHistoryService rateHistoryService;

    public RateSchedulerProd(RateHistoryService rateHistoryService) {
        this.rateHistoryService = rateHistoryService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void collectHourlyRate() {
        System.out.println("🔄 Scheduler PROD déclenché à " + LocalDateTime.now());
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
