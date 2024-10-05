package ar.edu.utn.frc.tup.lciii.dtos.common;


import jdk.jfr.StackTrace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CountryDTO {

    private String name;
    private String code;

    public CountryDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
