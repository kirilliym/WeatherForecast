package dev.kirilliym.weatherforecast.controller;

import dev.kirilliym.weatherforecast.model.dto.PrimeTokenDTO;
import dev.kirilliym.weatherforecast.service.PrimeTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final PrimeTokenService primeTokenService;

    @PostMapping("/create-token")
    public ResponseEntity<String> createToken(@RequestParam String token, @RequestParam Integer ops) {
        primeTokenService.createToken(token, ops);
        return ResponseEntity.ok().body("Token created");
    }

    @GetMapping("/get-all-tokens")
    public ResponseEntity<List<PrimeTokenDTO>> getAllTokens() {
        return ResponseEntity.ok().body(primeTokenService.getAllTokens());
    }
}
