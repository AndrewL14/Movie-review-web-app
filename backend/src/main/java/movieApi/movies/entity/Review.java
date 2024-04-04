package movieApi.movies.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private ObjectId id;
    private String body;

    private String username;
    private double rating;

    public Review(String body) {
        this.body = body;
    }

    public Review(String body , String username , double rating) {
        this.body = body;
        this.username = username;
        this.rating = rating;
    }
}
