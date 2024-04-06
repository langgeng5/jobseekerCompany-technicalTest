package jobseekercompany.technicaltest.application;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    
    Optional<Application> findByCandidateCandidateIdAndVacancyVacancyId(String candidateId, String vacancyId);
    
}
