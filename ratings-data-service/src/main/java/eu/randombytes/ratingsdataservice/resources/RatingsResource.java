package eu.randombytes.ratingsdataservice.resources;

import eu.randombytes.ratingsdataservice.models.Rating;
import eu.randombytes.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping("/users/{userId}")
    public UserRating getUserRatings(@PathVariable String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 5),
                new Rating("2345", 6)
        );
        UserRating userRating = new UserRating();
        userRating.setRatingList(ratings);
        return userRating;
    }
}
