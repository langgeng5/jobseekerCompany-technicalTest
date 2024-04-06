package jobseekercompany.technicaltest.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jobseekercompany.technicaltest.application.dto.ApplicationRequest;
import jobseekercompany.technicaltest.candidate.Candidate;
import jobseekercompany.technicaltest.candidate.CandidateRepository;
import jobseekercompany.technicaltest.enumData.RequirementGenderEnum;
import jobseekercompany.technicaltest.vacancy.Vacancy;
import jobseekercompany.technicaltest.vacancy.VacancyRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final Application application;

    public ResponseEntity<List<Application>> getApplicationList(String keyword, String sortBy, String direction){

        List<Application> response =application.findAllWithKeywordAndSort(keyword, sortBy, direction);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Application> insertApplication(ApplicationRequest request){

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
            .orElseThrow(() -> new RuntimeException("Data candidate not found"));

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
            .orElseThrow(() -> new RuntimeException("Data vacancy not found"));

        Optional<Application> applicationCheck = applicationRepository.findByCandidateCandidateIdAndVacancyVacancyId(request.getCandidateId(), request.getVacancyId());
        if (applicationCheck.isPresent()) {
            throw new RuntimeException("Candidate already apply for this vancancy");
        }

        if (request.getApplyDate().compareTo(vacancy.getExpiredDate()) > 0) {
            throw new RuntimeException("Vacancy has expired");
        }
        
        if (candidate.getAge() < vacancy.getMinAge() || candidate.getAge() > vacancy.getMaxAge()) {
            throw new RuntimeException("Candidate's age does not meet the requirements");
        }

        if (vacancy.getRequirementGender() != RequirementGenderEnum.All && candidate.getGender().toString() != vacancy.getRequirementGender().toString()) {
            throw new RuntimeException("Candidate's gender does not meet the requirements");
        }

        Application application = new Application();
        application.setCandidate(candidate);
        application.setVacancy(vacancy);
        application.setApplyDate(request.getApplyDate());
        application.setCreatedDate(LocalDateTime.now());

        try {
            application = applicationRepository.save(application);
            return ResponseEntity.status(HttpStatus.OK).body(application);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add data");
        }
    }

    public ResponseEntity<Application> updateApplication(String id, ApplicationRequest request){

        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Data application not found"));

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
            .orElseThrow(() -> new RuntimeException("Data candidate not found"));

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
            .orElseThrow(() -> new RuntimeException("Data vacancy not found"));

        Optional<Application> applicationCheck = applicationRepository.findByCandidateCandidateIdAndVacancyVacancyId(request.getCandidateId(), request.getVacancyId());
        if (applicationCheck.isPresent() && !applicationCheck.get().getApplyId().equals(id)) {
            throw new RuntimeException("Candidate already apply for this vancancy");
        }

        if (request.getApplyDate().compareTo(vacancy.getExpiredDate()) > 0) {
            throw new RuntimeException("Vacancy has expired");
        }
        
        if (candidate.getAge() < vacancy.getMinAge() || candidate.getAge() > vacancy.getMaxAge()) {
            throw new RuntimeException("Candidate's age does not meet the requirements");
        }

        if (vacancy.getRequirementGender() != RequirementGenderEnum.All && candidate.getGender().toString() != vacancy.getRequirementGender().toString()) {
            throw new RuntimeException("Candidate's gender does not meet the requirements");
        }

        application.setCandidate(candidate);
        application.setVacancy(vacancy);
        application.setApplyDate(request.getApplyDate());
        application.setCreatedDate(LocalDateTime.now());

        try {
            application = applicationRepository.save(application);
            return ResponseEntity.status(HttpStatus.OK).body(application);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add data");
        }
    }

    public ResponseEntity<Boolean> deleteApplication(String id){

        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Data application not found"));

        try {
            applicationRepository.delete(application); 
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            throw new RuntimeException("Delete fail!");
        }
    }
    
}
