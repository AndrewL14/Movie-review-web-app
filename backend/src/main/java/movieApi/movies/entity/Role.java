package movieApi.movies.entity;

import lombok.Data;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Data
@Document(collection = "roles")
public class Role implements GrantedAuthority {
    @Id
    private ObjectId roleId;
    private String authority;

    public Role() {
        super();
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
