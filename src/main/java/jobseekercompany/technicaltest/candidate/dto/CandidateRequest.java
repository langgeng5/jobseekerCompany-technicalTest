package jobseekercompany.technicaltest.candidate.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jobseekercompany.technicaltest.enumData.GenderEnum;
import lombok.Data;

@Data
public class CandidateRequest {

    @NotBlank(message = "Full Name must not be blank")
    private String fullName;
    @NotNull(message = "Date of Birth must not be blank")
    private LocalDate dob;
    @NotNull(message = "Gender must not be blank")
    private GenderEnum gender;
    
}
