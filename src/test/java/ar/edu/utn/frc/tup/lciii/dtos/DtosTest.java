package ar.edu.utn.frc.tup.lciii.dtos;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ErrorApi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DtosTest {

    @Test
    void testCountryDTOConstructorAndGetters() {
        // Arrange
        String expectedName = "France";
        String expectedCode = "FR";

        // Act
        CountryDTO countryDTO = new CountryDTO(expectedName, expectedCode);

        // Assert
        assertEquals(expectedName, countryDTO.getName());
        assertEquals(expectedCode, countryDTO.getCode());
    }

    @Test
    void testCountryDTOSetters() {
        // Arrange
        CountryDTO countryDTO = new CountryDTO();
        String expectedName = "Germany";
        String expectedCode = "DE";

        // Act
        countryDTO.setName(expectedName);
        countryDTO.setCode(expectedCode);

        // Assert
        assertEquals(expectedName, countryDTO.getName());
        assertEquals(expectedCode, countryDTO.getCode());
    }

    @Test
    void testCountryRequestDTOConstructorAndGetter() {
        // Arrange
        Integer expectedAmount = 5;

        // Act
        CountryRequestDTO countryRequestDTO = new CountryRequestDTO();
        countryRequestDTO.setAmountOfCountryToSave(expectedAmount);

        // Assert
        assertEquals(expectedAmount, countryRequestDTO.getAmountOfCountryToSave());
    }

    @Test
    void testCountryRequestDTOSetter() {
        // Arrange
        CountryRequestDTO countryRequestDTO = new CountryRequestDTO();
        Integer expectedAmount = 10;

        // Act
        countryRequestDTO.setAmountOfCountryToSave(expectedAmount);

        // Assert
        assertEquals(expectedAmount, countryRequestDTO.getAmountOfCountryToSave());
    }

    @Test
    void testErrorApiConstructorAndGetters() {
        // Arrange
        String expectedTimestamp = "2024-10-05T10:00:00Z";
        Integer expectedStatus = 404;
        String expectedError = "Not Found";
        String expectedMessage = "The requested resource was not found.";

        // Act
        ErrorApi errorApi = new ErrorApi(expectedTimestamp, expectedStatus, expectedError, expectedMessage);

        // Assert
        assertEquals(expectedTimestamp, errorApi.getTimestamp());
        assertEquals(expectedStatus, errorApi.getStatus());
        assertEquals(expectedError, errorApi.getError());
        assertEquals(expectedMessage, errorApi.getMessage());
    }

    @Test
    void testErrorApiSetters() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();
        String expectedTimestamp = "2024-10-05T10:00:00Z";
        Integer expectedStatus = 500;
        String expectedError = "Internal Server Error";
        String expectedMessage = "An unexpected error occurred.";

        // Act
        errorApi.setTimestamp(expectedTimestamp);
        errorApi.setStatus(expectedStatus);
        errorApi.setError(expectedError);
        errorApi.setMessage(expectedMessage);

        // Assert
        assertEquals(expectedTimestamp, errorApi.getTimestamp());
        assertEquals(expectedStatus, errorApi.getStatus());
        assertEquals(expectedError, errorApi.getError());
        assertEquals(expectedMessage, errorApi.getMessage());
    }

    @Test
    void testErrorApiBuilder() {
        // Arrange
        String expectedTimestamp = "2024-10-05T10:00:00Z";
        Integer expectedStatus = 400;
        String expectedError = "Bad Request";
        String expectedMessage = "Invalid request format.";

        // Act
        ErrorApi errorApi = ErrorApi.builder()
                .timestamp(expectedTimestamp)
                .status(expectedStatus)
                .error(expectedError)
                .message(expectedMessage)
                .build();

        // Assert
        assertEquals(expectedTimestamp, errorApi.getTimestamp());
        assertEquals(expectedStatus, errorApi.getStatus());
        assertEquals(expectedError, errorApi.getError());
        assertEquals(expectedMessage, errorApi.getMessage());
    }
}
