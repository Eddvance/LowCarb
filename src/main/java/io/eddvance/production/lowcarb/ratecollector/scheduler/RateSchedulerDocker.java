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
@Profile("docker")
public class RateSchedulerDocker {
    private static final Logger log = LoggerFactory.getLogger(RateSchedulerDocker.class);
    private final RateHistoryService rateHistoryService;
    private final RateMetricsExporter metricsExporter;

    public RateSchedulerDocker(RateHistoryService rateHistoryService,
                               RateMetricsExporter metricsExporter) {
        this.rateHistoryService = rateHistoryService;
        this.metricsExporter = metricsExporter;
    }

    @Scheduled(fixedRate = 30000)
    public void collectRate() {
        log.info("🐳 Scheduler DOCKER déclenché à {}", LocalDateTime.now());

        try {
            rateHistoryService.saveCurrentRate()
                    .doOnNext(rate -> {
                        log.info("✅ Tarif sauvegardé: {}", rate.getRate());
                        metricsExporter.updateMetrics();
                    })
                    .doOnError(error -> log.error("❌ Erreur lors de la collecte: ", error))
                    .block();
        } catch (Exception e) {
            log.error("💥 Exception dans le scheduler Docker: ", e);
        }
    }
}
