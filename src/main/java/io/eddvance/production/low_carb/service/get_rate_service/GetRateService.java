package io.eddvance.production.low_carb.service.get_rate_service;

import io.eddvance.production.low_carb.exception.GetRateException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class GetRateService {

    private final WebClient coalFiredWebClient;
    private final WebClient lowCarbPowerWebClient;

    public GetRateService(WebClient coalFiredWebClient, WebClient lowCarbPowerWebClient) {
        this.coalFiredWebClient = coalFiredWebClient;
        this.lowCarbPowerWebClient = lowCarbPowerWebClient;
    }

    public Mono<Double> getCarbonRate() {
        return coalFiredWebClient
                .get()
                .uri("/coal-fired")
                .retrieve()
                .bodyToMono(String.class)
                .map(rateString -> {
                    try {
                        Double rate = Double.parseDouble(rateString);
                        System.out.println("✓ Tarif carbone récupéré : " + rate + " €/kWh");
                        return rate;
                    } catch (NumberFormatException e) {
                        throw new GetRateException("Tarif carbone invalide: " + rateString, e);
                    }
                })
                .onErrorMap(WebClientResponseException.class, ex ->
                        new GetRateException("Service CoalFired indisponible: " + ex.getMessage(), ex))
                .onErrorMap(Exception.class, ex -> {
                    if (!(ex instanceof GetRateException)) {
                        return new GetRateException("Erreur récupération tarif carbone", ex);
                    }
                    return ex;
                });
    }

    public Mono<Double> getGreenRate() {
        return lowCarbPowerWebClient
                .get()
                .uri("/low-carb-power/rate")
                .retrieve()
                .bodyToMono(String.class)
                .map(rateString -> {
                    try {
                        Double rate = Double.parseDouble(rateString);
                        System.out.println("✓ Tarif vert récupéré : " + rateString + " €/kWh");
                        return rate;
                    } catch (NumberFormatException e) {
                        throw new GetRateException("Tarif vert invalide: " + rateString, e);
                    }
                })
                .onErrorMap(WebClientResponseException.class, ex ->
                        new GetRateException("Service LowCarbPower indisponible: " + ex.getMessage(), ex))
                .onErrorMap(Exception.class, ex -> {
                    if (!(ex instanceof GetRateException)) {
                        return new GetRateException("Erreur récupération tarif vert", ex);
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