package movieApi.movies.contoller;

import movieApi.movies.dto.request.CreateReviewRequest;
import movieApi.movies.dto.response.ReviewDTO;
import movieApi.movies.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService service;

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody CreateReviewRequest request){
        return new ResponseEntity<ReviewDTO>(service.
                createReview(request), HttpStatus.CREATED);
    }

}
