package eu.randombytes.moviecatalog.service.resources;

import eu.randombytes.moviecatalog.service.models.CatalogItem;
import eu.randombytes.moviecatalog.service.models.Movie;
import eu.randombytes.moviecatalog.service.models.Rating;
import eu.randombytes.moviecatalog.service.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/catalog")
public class MovieCatatlogResource {

    @Autowired // is a consumer annotation, spring will look to consume RestTemplate from @Bean annotation elsewhere
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalogRestTemplate(@PathVariable String userId) {
        // get all rated movie IDs
        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);

         return ratings.getRatingList().stream().map(rating -> {
             // for each movie id, call movie info service and get details
             Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
                // put them together
                return new CatalogItem(movie.getName(), "desc", rating.getRating());
        })
                 .collect(Collectors.toList());
    }

/*    @RequestMapping("/webclient/{userId}")
    public List<CatalogItem> getCatalogWebClient(@PathVariable String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 5),
                new Rating("2345", 6)
        );

        return ratings.stream().map(rating -> {
            // build an instance of webclient
            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
            return new CatalogItem(movie.getName(), "Desc", rating.getRating());
        });
    }*/
}
