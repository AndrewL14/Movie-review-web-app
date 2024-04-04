package movieApi.movies.service;

import com.mongodb.client.result.UpdateResult;
import movieApi.movies.converter.Converter;
import movieApi.movies.dto.request.CreateReviewRequest;
import movieApi.movies.dto.response.ReviewDTO;
import movieApi.movies.entity.Movie;
import movieApi.movies.entity.Review;
import movieApi.movies.entity.User;
import movieApi.movies.exception.MovieNotFoundException;
import movieApi.movies.exception.UserNotFoundException;
import movieApi.movies.repository.MovieRepository;
import movieApi.movies.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Creates a new Review objects, saves it to database, returns DTO.
     * If movie is not found throws MovieNotFoundException, If User is not found throws
     * UserNotFoundException.
     * @param request a request to create a new review using the movie / user's imdbId.
     * @return A Review DTO object
     */
    public ReviewDTO createReview(CreateReviewRequest request) {
        String userImdbId = request.getUserImdbId();
        String movieImdbId = request.getMovieImdbId();
        if (!movieImdbId.startsWith("tt") || !userImdbId.startsWith("tt")) {
            throw new IllegalArgumentException("user or movie imdbId invalid format");
        }


        Review review = reviewRepository.insert(new Review(request.getReviewBody(), request.getUsername() , request.getRating()));
        try {
            Movie movieToUpdate = movieRepository.findMovieByImdbId(movieImdbId)
                    .orElseThrow(MovieNotFoundException::new);
            List<Review> reviewsToUpdate = movieToUpdate.getReviewIds();
            reviewsToUpdate.add(review);
            updateAverageRating(movieToUpdate, request.getRating());
            movieToUpdate.setReviewIds(reviewsToUpdate);
            movieRepository.save(movieToUpdate);
        } catch (Exception e) {
            throw new MovieNotFoundException("Invalid Movie imdbId: " + movieImdbId);
        }

        try {
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("imdbId").is(userImdbId)),
                    new Update().push("userReviews").value(review),
                    User.class
            );
        } catch (Exception e) {
            throw new UserNotFoundException("Invalid User imdbId: " + userImdbId);
        }

        return Converter.reviewToDTO(review);
    }

    private void updateAverageRating(Movie movieToUpdate, double newRating) {
        int numberOfRatings = movieToUpdate.getNumberOfRatings();
        if (numberOfRatings != 0) {
            movieToUpdate.setAverageRating(
                    ((movieToUpdate.getAverageRating() * numberOfRatings) + newRating) / (numberOfRatings + 1)
            );
            movieToUpdate.setNumberOfRatings(numberOfRatings++);
        } else {
            movieToUpdate.setAverageRating(newRating);
            movieToUpdate.setNumberOfRatings(1);
        }
    }
}
