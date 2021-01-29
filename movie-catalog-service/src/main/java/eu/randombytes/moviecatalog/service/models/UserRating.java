package eu.randombytes.moviecatalog.service.models;

import java.util.List;

public class UserRating {
    
    private List<Rating> ratingList;

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
}
