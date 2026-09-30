package re.edu.md03_final.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private T data;
    private Object error;
    private int httpStatus;
}
