package ch.lorele.newspringtest.controller;

import ch.lorele.newspringtest.model.dto.AuthenticationResponse;
import ch.lorele.newspringtest.model.dto.SignInRequest;
import ch.lorele.newspringtest.model.dto.SignUpRequest;
import ch.lorele.newspringtest.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        AuthenticationResponse response = this.authenticationService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest request) {
        AuthenticationResponse response = this.authenticationService.signin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestHeader String authorization) {
        AuthenticationResponse response = this.authenticationService.tryRefresh(authorization);
        if (response != null) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(AuthenticationResponse.builder().errorMessage("refresh expired").build());
    }

    @GetMapping("/external")
    public ResponseEntity<List<String>> external() {
        List<String> roles = this.authenticationService.externalAuthorization();
        return ResponseEntity.ok(roles);
    }
}
