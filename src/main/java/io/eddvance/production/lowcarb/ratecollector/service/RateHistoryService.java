package io.eddvance.production.lowcarb.ratecollector.service;


import io.eddvance.production.lowcarb.ratecollector.repository.RateHistoryRepository;
import io.eddvance.production.lowcarb.ratecollector.model.RateRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class
RateHistoryService {

    private final RateHistoryRepository rateHistoryRepository;
    private final WebClient lowCarbPowerWebClient;
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RateHistoryService.class);
    public RateHistoryService(RateHistoryRepository rateHistoryRepository, WebClient lowCarbPowerWebClient) {
        this.rateHistoryRepository = rateHistoryRepository;
        this.lowCarbPowerWebClient = lowCarbPowerWebClient;
    }

    @Transactional
    public Mono<RateRecord> saveCurrentRate() {
        return lowCarbPowerWebClient
                .get()
                .uri("/low-carb-power/rate")
                .retrieve()
                .bodyToMono(String.class)
                .map(rateString -> Double.parseDouble(rateString.trim()))
                .map(rate -> new RateRecord(rate, LocalDateTime.now()))
                .flatMap(rateHistoryRepository::save)
                .doOnSuccess(record ->
                        log.info("✅ Tarif enregistré: {} €/kWh à {}",
                                record.getRate(), record.getRateTime())
                )
                .doOnError(error ->
                        log.error("❌ Erreur lors de l'enregistrement du tarif", error)
                )
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .maxBackoff(Duration.ofSeconds(10)));
    }

    public Mono<RateRecord> getLatestRate() {
        return rateHistoryRepository.findTopByOrderByRateTimeDesc();
    }
}
