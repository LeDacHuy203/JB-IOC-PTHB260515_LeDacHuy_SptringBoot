package re.edu.md03_final.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re.edu.md03_final.dto.request.BuildingCreateDTO;
import re.edu.md03_final.dto.request.BuildingStatus;
import re.edu.md03_final.dto.request.BuildingUpdateDTO;
import re.edu.md03_final.dto.response.ApiResponse;
import re.edu.md03_final.dto.response.BuildingResponseDTO;
import re.edu.md03_final.dto.response.PageResponse;
import re.edu.md03_final.exception.ComplieStatus;
import re.edu.md03_final.exception.DuplicateBuildingNameException;
import re.edu.md03_final.exception.ResourceNotFoundException;
import re.edu.md03_final.service.BuildingService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BuildingResponseDTO>>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) Short status
    ) {
        PageResponse<BuildingResponseDTO> result = buildingService.getPage(page, size, sortBy, sortDir, buildingName, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Lay du lieu thanh cong", result, null, 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BuildingResponseDTO>> createBuilding(
            @Valid @ModelAttribute BuildingCreateDTO request
    ) throws DuplicateBuildingNameException, IOException {

        BuildingResponseDTO result = buildingService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Luu du lieu thanh cong", result, null, HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BuildingResponseDTO>> updateBuilding(
            @PathVariable Long id, @Valid @ModelAttribute BuildingUpdateDTO request
    ) throws DuplicateBuildingNameException, IOException, ResourceNotFoundException, ComplieStatus {

        BuildingResponseDTO result = buildingService.update(request, id);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Luu du lieu thanh cong", result, null, HttpStatus.CREATED.value()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<BuildingResponseDTO>> patchBuilding(
            @PathVariable Long id, @Valid @RequestBody BuildingStatus request
    ) throws ResourceNotFoundException, ComplieStatus {

        BuildingResponseDTO result = buildingService.updateStatus(request, id);

        return ResponseEntity
                .ok(new ApiResponse<>(true, "Luu du lieu thanh cong", result, null, HttpStatus.CREATED.value()));
    }


//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<PageResponse<BuildingResponseDTO>>> findBuildingName(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "ASC")
//            Sort.Direction sortDir,
//            @RequestParam(required = false)
//            String building_name
//    ) {
//        PageResponse<BuildingResponseDTO> result = buildingService.getBuildingByName(page, size, sortBy, sortDir, building_name);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Lay du lieu thanh cong", result, null, 200));
//    }
}
