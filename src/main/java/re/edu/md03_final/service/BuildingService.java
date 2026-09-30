package re.edu.md03_final.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import re.edu.md03_final.dto.request.BuildingCreateDTO;
import re.edu.md03_final.dto.request.BuildingStatus;
import re.edu.md03_final.dto.request.BuildingUpdateDTO;
import re.edu.md03_final.dto.response.BuildingResponseDTO;
import re.edu.md03_final.dto.response.PageResponse;
import re.edu.md03_final.entity.Buildings;
import re.edu.md03_final.exception.ComplieStatus;
import re.edu.md03_final.exception.DuplicateBuildingNameException;
import re.edu.md03_final.exception.ResourceNotFoundException;
import re.edu.md03_final.repository.BuildingRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private static final String UPLOAD_DIR = "C:\\Users\\ldh2k\\Desktop\\uploads\\";

    public PageResponse<BuildingResponseDTO> getPage(
            int page,
            int size,
            String sortBy,
            Sort.Direction sortDir,
            String buildingName,
            Short status
    ) {

        if (page < 0) {
            page = 0;
        }

        if (size <= 0) {
            size = 5;
        }

        if (buildingName == null) {
            buildingName = "";
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDir, sortBy)
        );

        Page<BuildingResponseDTO> buldingPage =
                buildingRepository.searchAllBy(buildingName, status,
                        pageable
                );

        return new PageResponse<>(
                buldingPage.getContent(),
                buldingPage.getTotalPages(),
                buldingPage.getTotalElements(),
                buldingPage.getSize(),
                buldingPage.getNumber()
        );
    }

    public BuildingResponseDTO create(BuildingCreateDTO request) throws DuplicateBuildingNameException, IOException {
        if (buildingRepository.existsByBuildingName(request.getBuilding_name())) {
            throw new DuplicateBuildingNameException("Ten toa nha khong duoc trung");
        }

        String coverUrl = null;
        MultipartFile file = request.getDesign();

        if (file != null && !file.isEmpty()) {
            File uploadFolder = new File(UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            LocalDateTime now = LocalDateTime.now();
            String dateString = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filePath = uploadFolder.getAbsolutePath() + File.separator + dateString + "_" + file.getOriginalFilename();

            file.transferTo(new File(filePath));
            coverUrl = filePath;
        }

        Buildings buildings = Buildings.builder()
                .building_name(request.getBuilding_name())
                .building_area(request.getBuilding_area())
                .area_unit(request.getArea_unit())
                .content(request.getContent())
                .design(coverUrl)
                .start_date(request.getStart_date())
                .time(request.getTime())
                .time_unit(request.getTime_unit())
                .status(request.getStatus())
                .build();

        buildingRepository.save(buildings);

        return BuildingResponseDTO.builder()
                .building_name(buildings.getBuilding_name())
                .building_area(buildings.getBuilding_area())
                .area_unit(buildings.getArea_unit())
                .content(buildings.getContent())
                .design(buildings.getDesign())
                .start_date(buildings.getStart_date())
                .time(buildings.getTime())
                .time_unit(buildings.getTime_unit())
                .status(buildings.getStatus())
                .build();
    }

    public BuildingResponseDTO update(BuildingUpdateDTO request, Long id) throws ResourceNotFoundException, IOException, DuplicateBuildingNameException, ComplieStatus {


        if (buildingRepository.existsByBuildingName(request.getBuilding_name())) {
            throw new DuplicateBuildingNameException("Building name bi trung");
        }

        Buildings buildings = buildingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Building not found with id: " + id)
        );
        if (buildings.getStatus() == 2) {
            throw new ComplieStatus(" Trang thai Building đã hoàn thành khong the thay doi");
        }

        String coverUrl = null;
        MultipartFile file = request.getDesign();

        if (file != null && !file.isEmpty()) {
            File uploadFolder = new File(UPLOAD_DIR);

            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            LocalDateTime now = LocalDateTime.now();
            String dateString = now.format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
            );

            String filePath = uploadFolder.getAbsolutePath()
                    + File.separator
                    + dateString + "_" + file.getOriginalFilename();

            file.transferTo(new File(filePath));

            buildings.setDesign(filePath);
        }

        buildings.setBuilding_name(request.getBuilding_name());
        buildings.setBuilding_area(request.getBuilding_area());
        buildings.setArea_unit(request.getArea_unit());
        buildings.setDesign(coverUrl);
        buildings.setStatus(request.getStatus());
        buildings.setStart_date(request.getStart_date());
        buildings.setTime(request.getTime());
        buildings.setTime_unit(request.getTime_unit());
        buildings.setContent(request.getContent());
        buildingRepository.save(buildings);

        return BuildingResponseDTO.builder()
                .building_name(buildings.getBuilding_name())
                .building_area(buildings.getBuilding_area())
                .start_date(buildings.getStart_date())
                .time(buildings.getTime())
                .time_unit(buildings.getTime_unit())
                .design(buildings.getDesign())
                .area_unit(buildings.getArea_unit())
                .status(buildings.getStatus())
                .build();
    }

    public BuildingResponseDTO updateStatus(BuildingStatus request, Long id) throws ResourceNotFoundException, ComplieStatus {
        Buildings buildings = buildingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Building not found with id: " + id)
        );

        if (buildings.getStatus() == 2) {
            throw new ComplieStatus(" Trang thai Building đã hoàn thành khong the thay doi");
        }

        buildings.setStatus(request.getStatus());
        buildingRepository.save(buildings);
        return BuildingResponseDTO.builder()
                .building_name(buildings.getBuilding_name())
                .building_area(buildings.getBuilding_area())
                .start_date(buildings.getStart_date())
                .time(buildings.getTime())
                .time_unit(buildings.getTime_unit())
                .design(buildings.getDesign())
                .area_unit(buildings.getArea_unit())
                .status(buildings.getStatus())
                .build();
    }


}
