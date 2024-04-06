package jobseekercompany.technicaltest.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {

    @NotBlank(message = "Candidate must not be blank")
    private String candidateId;
    @NotBlank(message = "Vancancy must not be blank")
    private String vacancyId;
    @NotNull(message = "Apply Date must not be blank")
    private LocalDate applyDate;
}
