package ch.lorele.newspringtest.service.impl;

import ch.lorele.newspringtest.model.dto.AuthenticationResponse;
import ch.lorele.newspringtest.model.dto.SignInRequest;
import ch.lorele.newspringtest.model.dto.SignUpRequest;
import ch.lorele.newspringtest.model.entity.Role;
import ch.lorele.newspringtest.model.entity.User;
import ch.lorele.newspringtest.repo.RefreshTokenRepository;
import ch.lorele.newspringtest.service.AuthenticationService;
import ch.lorele.newspringtest.service.JwtService;
import ch.lorele.newspringtest.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepo;

    @Override
    public AuthenticationResponse signup(SignUpRequest request) {
        User user = User.builder()
                .fName(request.getFName())
                .lName(request.getLName())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.builder().name("USER").build()))
                .build();

        user = this.userService.createUser(user);
        log.info("[Auth Successful] - User {} successfully created", user.getEmail());

        return AuthenticationResponse.builder()
                .accessToken(this.jwtService.generateAccessToken(user))
                .refreshToken(this.jwtService.generateRefreshToken(user))
                .build();
    }

    @Override
    public AuthenticationResponse signin(SignInRequest request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = this.userService.fetchByEmail(request.getEmail());

        return AuthenticationResponse.builder()
                .accessToken(this.jwtService.generateAccessToken(user))
                .refreshToken(this.jwtService.generateRefreshToken(user))
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse tryRefresh(String refreshToken) {
        if (this.jwtService.isRefreshTokenValid(refreshToken)) {
            String email = this.jwtService.extractEmail(refreshToken);
            User user = this.userService.fetchByEmail(email);

            this.refreshTokenRepo.deleteByName(refreshToken);

            return AuthenticationResponse.builder()
                    .accessToken(this.jwtService.generateAccessToken(user))
                    .refreshToken(this.jwtService.generateRefreshToken(user))
                    .build();
        }

        return null;
    }

    @Override
    public List<String> externalAuthorization() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getRoles().stream().map(Role::getName).toList();
    }
}
