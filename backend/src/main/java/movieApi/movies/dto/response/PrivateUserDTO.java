package movieApi.movies.dto.response;

import lombok.Builder;
import movieApi.movies.entity.Review;

import java.util.List;
@Builder
public record PrivateUserDTO(
        String imdbId,
        String firstName,
        String lastName,
        String username,
        String email,
        String jwt,
        List<Review> userReviews
) {
}
