package io.eddvance.production.lowcarb.ratecollector.scheduler;

import io.eddvance.production.lowcarb.ratecollector.metrics.RateMetricsExporter;
import io.eddvance.production.lowcarb.ratecollector.service.RateHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Profile("dev")
public class RateSchedulerDev {

    private static final Logger log = LoggerFactory.getLogger(RateSchedulerDev.class);
    private final RateHistoryService rateHistoryService;
    private final RateMetricsExporter metricsExporter;

    public RateSchedulerDev(RateHistoryService rateHistoryService,
                            RateMetricsExporter metricsExporter) {
        this.rateHistoryService = rateHistoryService;
        this.metricsExporter = metricsExporter;
    }

    @Scheduled(fixedRate = 60000) // 60000 ms = 1 minute
    public void collectHourlyRate() {
        log.info("🔄 Scheduler DEV déclenché à {}", LocalDateTime.now());

        try {
            rateHistoryService.saveCurrentRate()
                    .doOnNext(rate -> {
                        log.info("✅ Tarif: {}", rate.getRate());
                        // Mettre à jour les métriques Prometheus après chaque save
                        metricsExporter.updateMetrics();
                    })
                    .doOnError(error -> log.error("❌ Erreur: ", error))
                    .block();
        } catch (Exception e) {
            log.error("💥 Exception dans le scheduler: ", e);
        }
    }
}