package jobseekercompany.technicaltest.vacancy.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jobseekercompany.technicaltest.enumData.RequirementGenderEnum;
import lombok.Data;

@Data
public class VancancyRequest {

    @NotBlank(message = "Vacancy Name must not be blank")
    private String vacancyName;
    @NotNull(message = "Min Age must not be blank")
    private Integer minAge;
    @NotNull(message = "Max Age must not be blank")
    private Integer maxAge;
    @NotNull(message = "Required Gender must not be blank")
    private RequirementGenderEnum requirementGender;
    @NotNull(message = "Expired Date must not be blank")
    private LocalDate expiredDate;
}
