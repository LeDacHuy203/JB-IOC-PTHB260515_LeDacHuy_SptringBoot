package re.edu.md03_final.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "buildings")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Buildings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String building_name;

    @Column(nullable = false)
    private BigDecimal building_area;

    @Column(length = 10, nullable = false)
    private String area_unit;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private Integer time;

    @Column(nullable = false)
    private  String time_unit;

    @Column(nullable = false)
    private String design;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Short status = 1;

}
