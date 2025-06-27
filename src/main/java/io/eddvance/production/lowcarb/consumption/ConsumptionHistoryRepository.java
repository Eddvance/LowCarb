package io.eddvance.production.lowcarb.consumption;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ConsumptionHistoryRepository extends ReactiveCrudRepository<ConsumptionHistory, Long> {

    Flux<ConsumptionHistory> findByEmailOrderByConsumptionTimeDesc(String email);
}
