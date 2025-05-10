package io.eddvance.production.low_carb.service;

public class LowCarbService {

    //calcul energie carbonnee
    public String getFinalRate() {
        Double bloc256 = 256.00;
        Double bloc256Price = 10.00;
        Double finalRatePrice;
        Double rating;
        String provisoire = "";
        Double provisoireDouble;
        provisoireDouble = Double.parseDouble(provisoire);
        rating = provisoireDouble / bloc256;
        finalRatePrice = rating * bloc256Price;

        return finalRatePrice.toString();
    }

    //calcul 19% energie carbonnee = 81% energie verte, energiem verte provient de lowarbpower
}
