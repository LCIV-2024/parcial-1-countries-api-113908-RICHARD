package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    @GetMapping("/api/countries")

    public ResponseEntity<List<CountryDTO>> getAllCountries(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {

        List<CountryDTO> countries = countryService.getAllCountriesDTO();

        if (name != null) {
            countries = countries.stream()
                    .filter(country -> country.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
        }

        if (code != null) {
            countries = countries.stream()
                    .filter(country -> country.getCode().equalsIgnoreCase(code))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(countries);
    }

    @GetMapping("/api/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(@PathVariable String continent) {

        List<String> validContinents = Arrays.asList("Africa", "Americas", "Asia", "Europe", "Oceania", "Antarctic");


        if (!validContinents.contains(continent)) {
            return ResponseEntity.badRequest().body(null);
        }

        List<CountryDTO> countries = countryService.getCountriesByContinent(continent);
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/api/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(@PathVariable String language) {

        List<String> validLanguages = Arrays.asList("English", "Spanish", "French", "German",
                "Portuguese", "Chinese", "Arabic",
                "Russian", "Hindi", "Swahili");


        if (!validLanguages.contains(language)) {
            return ResponseEntity.badRequest().body(null);
        }

        List<CountryDTO> countries = countryService.getCountriesByLanguage(language);
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/api/countries/most-borders")
    public ResponseEntity<CountryDTO> getMostBorderCountries(){
        return ResponseEntity.ok(countryService.getCountryWithMostBorders());
    }

    @PostMapping("/api/countries")
    public ResponseEntity< List<CountryDTO>> saveCountries(@RequestBody CountryRequestDTO countryRequestDTO) {

        return ResponseEntity.ok(countryService.saveCountries(countryRequestDTO));
    }
}