package jobseekercompany.technicaltest.vacancy;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VacancyRepository extends MongoRepository<Vacancy, String> {
    
}
