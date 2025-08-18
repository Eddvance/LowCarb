package io.eddvance.production.lowcarb.ratecollector.metrics;

import com.google.common.util.concurrent.AtomicDouble;
import io.eddvance.production.lowcarb.ratecollector.service.RateHistoryService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RateMetricsExporter {
    private final RateHistoryService rateHistoryService;
    private final MeterRegistry meterRegistry;

    // Valeurs atomiques pour les gauges
    private final AtomicDouble currentRate = new AtomicDouble(0.0);
    private final AtomicDouble hourlyAverage = new AtomicDouble(0.0);
    private final AtomicDouble dailyAverage = new AtomicDouble(0.0);

    public RateMetricsExporter(RateHistoryService rateHistoryService, MeterRegistry meterRegistry) {
        this.rateHistoryService = rateHistoryService;
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void initMetrics() {
        // Métrique pour le tarif actuel
        Gauge.builder("lowcarb_power_rate_current", currentRate, AtomicDouble::get)
                .description("Current electricity rate in EUR/kWh")
                .baseUnit("EUR_per_kWh")
                .register(meterRegistry);

        // Métrique pour la moyenne horaire
        Gauge.builder("lowcarb_power_rate_hourly_avg", hourlyAverage, AtomicDouble::get)
                .description("Hourly average electricity rate in EUR/kWh")
                .baseUnit("EUR_per_kWh")
                .register(meterRegistry);

        // Métrique pour la moyenne journalière
        Gauge.builder("lowcarb_power_rate_daily_avg", dailyAverage, AtomicDouble::get)
                .description("Daily average electricity rate in EUR/kWh")
                .baseUnit("EUR_per_kWh")
                .register(meterRegistry);

        // Mettre à jour les valeurs immédiatement
        updateMetrics();
    }

    // Cette méthode est appelée par le scheduler pour mettre à jour les métriques
    public void updateMetrics() {
        // Récupérer et mettre à jour le tarif actuel
        rateHistoryService.getLatestRate()
                .subscribe(rate -> {
                    if (rate != null) {
                        currentRate.set(rate.getRate());
                    }
                });

        // Récupérer et mettre à jour la moyenne horaire
        rateHistoryService.getLastHourAverage()
                .subscribe(avg -> {
                    if (avg != null) {
                        hourlyAverage.set(avg);
                    }
                });

        // Récupérer et mettre à jour la moyenne journalière
        rateHistoryService.getLastDayAverage()
                .subscribe(avg -> {
                    if (avg != null) {
                        dailyAverage.set(avg);
                    }
                });
    }
}
