package io.eddvance.production.lowcarb.cronservice.rate;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RateHistoryRepository extends ReactiveCrudRepository<RateRecord, Long> {

    Mono<RateRecord> findTopByOrderByRateTimeDesc();

}
