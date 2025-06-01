package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.mapper.TypoMapper;
import dev.kirilliym.weatherforecast.model.dto.TypoDTO;
import dev.kirilliym.weatherforecast.repository.TypoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypoService {
    private final TypoRepository typoRepository;
    private final TypoMapper typoMapper;

    public TypoDTO getTypo(String place) {
        return typoRepository.findByWrong(place).map(typoMapper::mapToDTO).get();
    }
}
