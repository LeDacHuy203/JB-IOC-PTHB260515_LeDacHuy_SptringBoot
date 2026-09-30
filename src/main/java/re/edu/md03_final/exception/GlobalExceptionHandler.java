package re.edu.md03_final.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import re.edu.md03_final.dto.response.ApiResponse;
import re.edu.md03_final.dto.response.ErrResponse;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // =========================
    // VALIDATION ERROR
    // =========================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getFieldErrors().forEach(error ->
                errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(
                        false,
                        "Dữ liệu không hợp lệ",
                        null,
                        errors,
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    @ExceptionHandler(DuplicateBuildingNameException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrResponse> handleDuplicateBuildingNameException(DuplicateBuildingNameException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new ErrResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage()
                        )
                );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrResponse>
    handleResourceNotFoundException(
            ResourceNotFoundException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage()
                        )
                );
    }


    @ExceptionHandler(ComplieStatus.class)
    public ResponseEntity<ErrResponse>
    handleComplieStatus(
            ComplieStatus ex
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage()
                        )
                );
    }



}