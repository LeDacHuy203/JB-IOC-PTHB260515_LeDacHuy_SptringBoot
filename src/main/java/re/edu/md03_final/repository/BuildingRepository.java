package re.edu.md03_final.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.edu.md03_final.dto.response.BuildingResponseDTO;
import re.edu.md03_final.dto.response.PageResponse;
import re.edu.md03_final.entity.Buildings;


@Repository
public interface BuildingRepository extends JpaRepository<Buildings, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END
            FROM Buildings b
            WHERE b.building_name = :buildingName
            """)
    boolean existsByBuildingName(@Param("buildingName") String buildingName);

    @Query("""
                SELECT new re.edu.md03_final.dto.response.BuildingResponseDTO(
                    b.building_name,
                    b.building_area,
                    b.area_unit,
                    b.start_date,
                    b.time,
                    b.time_unit,
                    b.design,
                    b.content,
                    b.status
                )
                FROM Buildings b
                WHERE (:buildingName IS NULL OR :buildingName = '' OR LOWER(b.building_name) LIKE LOWER(CONCAT('%', :buildingName, '%')))
                  AND (:status IS NULL OR b.status = :status)
                ORDER BY b.id
            """)
    Page<BuildingResponseDTO> searchAllBy(
            @Param("buildingName") String buildingName,
            @Param("status") Short status,
            Pageable pageable
    );



}

