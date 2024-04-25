package movieApi.movies.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private ObjectId id;
    private String imdbId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    @DocumentReference
    private List<Review> userReviews;

    @DocumentReference
    private Set<Role> authorities;

    public User(String imdbId , String firstName , String lastName ,
                String username , String password , String email , List<Review> userReviews) {
        this.imdbId = imdbId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userReviews = userReviews;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
