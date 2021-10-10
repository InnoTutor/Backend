package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.entity.card.CardRating;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class AverageCardRating {

    private final Collection<CardRating> cardRatings;

    public Double averageRating() {
        if (cardRatings.isEmpty()) {
            return null;
        }
        double sum = 0;
        for (final CardRating rating : cardRatings) {
            sum += rating.getMark();
        }
        return sum / cardRatings.size();
    }
}
