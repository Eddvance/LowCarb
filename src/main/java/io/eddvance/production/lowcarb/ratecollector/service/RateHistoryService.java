package io.eddvance.production.lowcarb.ratecollector.service;


import io.eddvance.production.lowcarb.ratecollector.model.RateRecord;
import io.eddvance.production.lowcarb.ratecollector.repository.RateHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class RateHistoryService {

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

    public Mono<Double> getAverageRateSince(LocalDateTime since) {
        return rateHistoryRepository.findByRateTimeAfterOrderByRateTimeAsc(since)
                .map(RateRecord::getRate)
                .reduce(new double[]{0.0, 0.0}, (acc, rate) -> {
                    acc[0] += rate;
                    acc[1] += 1;
                    return acc;
                })
                .map(acc -> acc[1] > 0 ? acc[0] / acc[1] : 0.0)
                .doOnNext(avg -> log.info("📊 Moyenne depuis {} : {} €/kWh", since, avg));
    }

    public Mono<Double> getLastHourAverage() {
        return getAverageRateSince(LocalDateTime.now().minusHours(1));
    }

    public Mono<Double> getLastDayAverage() {
        return getAverageRateSince(LocalDateTime.now().minusDays(1));
    }

    public Flux<RateRecord> getRateHistory(LocalDateTime from, LocalDateTime to) {
        return rateHistoryRepository.findByRateTimeAfterOrderByRateTimeAsc(from)
                .filter(rate -> rate.getRateTime().isBefore(to));
    }
}
