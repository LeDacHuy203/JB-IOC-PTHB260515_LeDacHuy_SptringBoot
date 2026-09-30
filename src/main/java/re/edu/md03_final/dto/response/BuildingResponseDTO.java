package re.edu.md03_final.dto.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BuildingResponseDTO {

    private String building_name;

    private BigDecimal building_area;

    private String area_unit;

    private LocalDate start_date;

    private Integer time;

    private String time_unit;

    private String design;

    private String content;

    private Short status ;
}
