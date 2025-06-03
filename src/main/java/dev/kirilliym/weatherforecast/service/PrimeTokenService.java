package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.exception.NoTokenOperationsException;
import dev.kirilliym.weatherforecast.exception.TokenNotFoundException;
import dev.kirilliym.weatherforecast.mapper.PrimeTokenMapper;
import dev.kirilliym.weatherforecast.model.dto.PrimeTokenDTO;
import dev.kirilliym.weatherforecast.model.entity.PrimeToken;
import dev.kirilliym.weatherforecast.repository.PrimeTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrimeTokenService {
    private final PrimeTokenRepository primeTokenRepository;
    private final PrimeTokenMapper primeTokenMapper;

    public void createToken(String token, int remainOperations) {
        PrimeToken primeToken = new PrimeToken();
        primeToken.setToken(token);
        primeToken.setRemainOperations(remainOperations);
        primeTokenRepository.save(primeToken);
    }

    @Transactional
    public void useToken(String token) {
        PrimeToken primeToken = primeTokenRepository.findByToken(token)
                .orElseThrow(TokenNotFoundException::new);

        int currentOps = primeToken.getRemainOperations();
        if (currentOps <= 0) {
            throw new NoTokenOperationsException();
        }

        primeToken.setRemainOperations(currentOps - 1);
        primeTokenRepository.save(primeToken);
    }

    public List<PrimeTokenDTO> getAllTokens() {
        return primeTokenRepository.findAll().stream().map(primeTokenMapper::mapToDTO).collect(Collectors.toList());
    }
}
