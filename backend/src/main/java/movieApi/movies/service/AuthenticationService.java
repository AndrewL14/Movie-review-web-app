package movieApi.movies.service;

import movieApi.movies.dto.request.BasicLoginRequest;
import movieApi.movies.dto.request.CreateUserRequest;
import movieApi.movies.dto.response.PrivateUserDTO;
import movieApi.movies.entity.Role;
import movieApi.movies.entity.User;
import movieApi.movies.repository.RoleRepository;
import movieApi.movies.repository.UserRepository;
import movieApi.movies.utils.CustomIdMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public PrivateUserDTO registerUser(CreateUserRequest request) {
        String[] requestInfo = new String[5];
        requestInfo[0] = request.firstName();
        requestInfo[1] = request.lastName();
        requestInfo[2] = request.username();
        requestInfo[3] = request.password();
        requestInfo[4] = request.email();

        String encodedPassword = passwordEncoder.encode(requestInfo[3]);
        String imdbId = CustomIdMaker.generateRandomNumberIdentifier();
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        User user = User.builder()
                .imdbId(imdbId)
                .firstName(requestInfo[0])
                .lastName(requestInfo[1])
                .username(requestInfo[2])
                .password(encodedPassword)
                .email(requestInfo[4])
                .authorities(authorities)
                .build();
        userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestInfo[2], requestInfo[3])
        );
        String jwt = tokenService.generateJwt(authentication);
        return PrivateUserDTO.builder()
                .imdbId(imdbId)
                .firstName(requestInfo[0])
                .lastName(requestInfo[1])
                .username(requestInfo[2])
                .email(requestInfo[4])
                .jwt(jwt)
                .userReviews(new ArrayList<>())
                .build();
    }


    public PrivateUserDTO loginWithUsername(BasicLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        String jwt = tokenService.generateJwt(authentication);
        User user = (User) userService.loadUserByUsername(request.username());
        return PrivateUserDTO.builder()
                .imdbId(user.getImdbId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(request.username())
                .email(user.getEmail())
                .jwt(jwt)
                .userReviews(user.getUserReviews())
                .build();
    }
}
