# Sample Endpoints
In this doc you will find all sample endpoints including sample data to plug in.
The sample format is in yaml format.

The program is setup where the user will have to login first using the authentication request. 
So every other request uses the given JWT access token to verify user request.

## Authentication request
``` yaml
login_endpoint:
  method: GET
  url: HTTP://localhost:8080/api/v1/auth/login
  request_body:
    type: BasicLoginRequest
    attributes:
      username: test
      password: test
## test is the sample user.
register_endpoint:
  method: POST
  url: HTTP://localhost:8080/api/v1/auth/register
  request_body:
    type: CreateUserRequest
    attributes:
      firstName: YOUR_FIRST_NAME
      lastName: YOUR_LAST_NAME
      username: YOUR_USERNAME
      password: YOUR_PASSWORD
      email: YOUR_EMAIL
```

## Movie request

```yaml
endpoints:
  all_movies:
    method: GET
    url: http://localhost:8080/api/v1/movies
    response_type: List<MovieDTO>

  single_movie:
    method: GET
    url: http://localhost:8080/api/v1/movies/{imdbId}
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    response_type: Optional<MovieDTO>

  upload_movie:
    method: PUT
    url: http://localhost:8080/api/v1/movies/upload
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    request_body:
      type: CreateMovieRequest
      attributes:
        title: "Your Title"
        releaseDate: "Release Date"
        trailerLink: "Trailer Link"
        poster: "Poster"
        genres: ["Genre1", "Genre2"]
        backDrop: ["Backdrop1", "Backdrop2"]
    response_type: MovieDTO
```

## User request

```yaml
endpoints:
  all_public_users:
    method: GET
    url: http://localhost:8080/api/v1/users/
    response_type: List<PublicUserDTO>

  public_user_by_imdbId:
    method: GET
    url: http://localhost:8080/api/v1/users/public/{tt8369545} (imdbId)
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    response_type: Optional<PublicUserDTO>

  public_user_by_username:
    method: GET
    url: http://localhost:8080/api/v1/users/public/{test} (username)
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    response_type: PublicUserDTO

  public_user_by_email:
    method: GET
    url: http://localhost:8080/api/v1/users/public/{email}
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    response_type: PublicUserDTO

  private_user_by_imdbId:
    method: GET
    url: http://localhost:8080/api/v1/users/private/{tt8369545} (imdbId)
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    response_type: Optional<PrivateUserDTO>

  create_user:
    method: POST
    url: http://localhost:8080/api/v1/users/create
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    request_body:
      type: CreateUserRequest
      attributes:
        firstName: "First Name"
        lastName: "Last Name"
        username: "Username"
        password: "Password"
        email: "Email"
    response_type: PrivateUserDTO

  update_user:
    method: PUT
    url: http://localhost:8080/api/v1/users/update
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    request_body:
      type: UpdateUserRequest
      attributes:
        imdbId: "IMDb ID"
        firstName: "First Name"
        lastName: "Last Name"
        username: "Username"
        password: "Password"
        email: "Email"
    response_type: PrivateUserDTO

  delete_user:
    method: DELETE
    url: http://localhost:8080/api/v1/users/delete/{imdbId}
    response_type: null
```

## Review request

```yaml
endpoints:
  create_review:
    method: POST
    url: http://localhost:8080/api/v1/reviews
    headers:
    Authorization: "Bearer YOUR_ACCESS_TOKEN"
    request_body:
      type: CreateReviewRequest
      attributes:
        reviewBody: "Review Body"
        rating: 5.0
        username: "Username" (test)
        movieImdbId: "Movie IMDb ID"
        userImdbId: "User IMDb ID" (tt8369545)
    response_type: ReviewDTO
```