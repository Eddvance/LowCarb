package io.eddvance.production.lowcarb.rating;

import io.eddvance.production.lowcarb.rating.dto.GreenRateResponse;
import io.eddvance.production.lowcarb.rating.dto.ProductOfferingPriceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class RateService {

    private final WebClient coalFiredWebClient;
    private final WebClient serviceCronWebClient;

    public RateService(WebClient coalFiredWebClient, WebClient serviceCronWebClient) {
        this.coalFiredWebClient = coalFiredWebClient;
        this.serviceCronWebClient = serviceCronWebClient;
    }

    public Mono<Double> getCarbonRate() {
        return coalFiredWebClient
                .get()
                .uri("/productOfferingPrice/block-256-offer-price")
                .retrieve()
                .bodyToMono(ProductOfferingPriceResponse.class)
                .<Double>handle((response, sink) -> {
                    try {
                        Double rate = response.getPrice().getValue();
                        Double kwh = response.getUnitOfMeasure().getAmount();
                        double finalePrice = rate / kwh;

                        sink.next(finalePrice);
                    } catch (Exception e) {
                        sink.error(new RateException("Erreur calcul tarif carbone: " + e.getMessage(), e));//throw
                    }
                })
                .onErrorMap(WebClientResponseException.class, ex ->
                        new RateException("Service CoalFired indisponible: " + ex.getMessage(), ex))
                .onErrorMap(Exception.class, ex -> {
                    if (!(ex instanceof RateException)) {
                        return new RateException("Erreur récupération tarif carbone", ex);
                    }
                    return ex;
                });
    }

    public Mono<Double> getGreenRate() {
        return serviceCronWebClient
                .get()
                .uri("/api/rates/last-rate")
                .retrieve()
                .bodyToMono(GreenRateResponse.class)
                .flatMap(response -> {
                    if (response == null || response.getRate() == null) {
                        return Mono.error(new RateException("Réponse vide ou nulle pour le tarif vert."));
                    }

                    try {
                        Double rate = response.getRate();
                        System.out.println("✓ Tarif vert récupéré : " + rate + " €/kWh (à " + response.getRateTime() + ")");
                        return Mono.just(rate);
                    } catch (NumberFormatException e) {
                        return Mono.error(new RateException("Tarif vert invalide ", e));
                    }
                })
                .onErrorMap(WebClientResponseException.class, ex ->
                        new RateException("Service tarif vert indisponible: " + ex.getMessage(), ex))
                .onErrorMap(Exception.class, ex -> {
                    if (!(ex instanceof RateException)) {
                        return new RateException("Erreur récupération tarif vert", ex);
                    }
                    return ex;
                });
    }

    public Mono<Tuple2<Double, Double>> getAllRates() {
        return Mono.zip(
                getCarbonRate(),
                getGreenRate()
        ).doOnSuccess(tuple ->
                System.out.println("✅ Tarifs récupérés - Carbon: " + tuple.getT1() +
                        ", Green: " + tuple.getT2())
        );
    }
}