package re.edu.md03_final.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingCreateDTO {
    @NotBlank(message = "Building Name khong duoc de trong")
    @Size(max = 100, message = "Building Name khong duoc vuot qua 100 ky tu")
    private String building_name;

    @NotNull(message = "Building Area khong duoc de trong")
    @Positive(message = "Building Area phai lon hon 0")
    private BigDecimal building_area;

    @NotBlank(message = "Area Unit khong duoc de trong")
    @Size(max = 10, message = "Area Unit khong duoc vuot qua 10 ky tu")
    private String area_unit;

    @NotNull(message = "Start Date khong duoc de trong")
    private LocalDate start_date;

    @NotNull(message = "Ngay khoi cong khong duoc de trong")
    @Positive(message = "Ngay khoi cong phai lon hon 0")
    private Integer time;

    @NotBlank(message = "Time Unit khong duoc de trong")
    private String time_unit;

    private MultipartFile design;

    @NotBlank(message = "Content khong duoc de trong")
    private String content;

    private Short status;
}
