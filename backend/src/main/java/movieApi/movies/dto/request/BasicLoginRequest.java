package movieApi.movies.dto.request;

public record BasicLoginRequest(
        String username,
        String password
) {
}
