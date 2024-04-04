package movieApi.movies.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest {
    private String reviewBody;
    private double rating;
    private String username;
    private String movieImdbId;
    private String userImdbId;
}
