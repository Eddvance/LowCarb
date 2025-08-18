package io.eddvance.production.lowcarb.ratecollector.repository;

import io.eddvance.production.lowcarb.ratecollector.model.RateRecord;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface RateHistoryRepository extends ReactiveCrudRepository<RateRecord, Long> {

    Mono<RateRecord> findTopByOrderByRateTimeDesc();

    Flux<RateRecord> findByRateTimeAfterOrderByRateTimeAsc(LocalDateTime after);
}
