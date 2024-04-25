package movieApi.movies.contoller;

import movieApi.movies.dto.request.BasicLoginRequest;
import movieApi.movies.dto.request.CreateUserRequest;
import movieApi.movies.dto.response.PrivateUserDTO;
import movieApi.movies.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @GetMapping("/login")
    public ResponseEntity<PrivateUserDTO> login(@RequestBody BasicLoginRequest request) {
        return new ResponseEntity<PrivateUserDTO>(
                service.loginWithUsername(request), HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<PrivateUserDTO> register(@RequestBody CreateUserRequest request) {
        System.out.println("made it here");
        return new ResponseEntity<PrivateUserDTO>(
                service.registerUser(request), HttpStatus.OK
        );
    }
}
