package jobseekercompany.technicaltest.vacancy;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jobseekercompany.technicaltest.vacancy.dto.VancancyRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VacancyService {
    
    private final VacancyRepository vacancyRepository;
    private final Vacancy vacancy;

    public ResponseEntity<List<Vacancy>> getVacancyList(String keyword, String sortBy, String direction){

        List<Vacancy> response =vacancy.findAllWithKeywordAndSort(keyword, sortBy, direction);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Vacancy> insertVacancy(VancancyRequest request){

        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyName(request.getVacancyName());
        vacancy.setMinAge(request.getMinAge());
        vacancy.setMaxAge(request.getMaxAge());
        vacancy.setRequirementGender(request.getRequirementGender());
        vacancy.setExpiredDate(request.getExpiredDate());
        vacancy.setcreatedDate(LocalDateTime.now());

        try {
            vacancy = vacancyRepository.save(vacancy);
            return ResponseEntity.status(HttpStatus.OK).body(vacancy);
        } catch (DuplicateKeyException e) { // check duplicate exception
            throw new RuntimeException("VacancyName already exists");
        }
    }

    public ResponseEntity<Vacancy> updateVacancy(String id, VancancyRequest request){

        // check vacancy data
        Vacancy vacancy = vacancyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Data not found"));

        vacancy.setVacancyName(request.getVacancyName());
        vacancy.setMinAge(request.getMinAge());
        vacancy.setMaxAge(request.getMaxAge());
        vacancy.setRequirementGender(request.getRequirementGender());
        vacancy.setExpiredDate(request.getExpiredDate());

        try {
            vacancy = vacancyRepository.save(vacancy);
            return ResponseEntity.status(HttpStatus.OK).body(vacancy);
        } catch (DuplicateKeyException e) { // check duplicate exception
            throw new RuntimeException("VacancyName already exists");
        }
    }

    public ResponseEntity<Boolean> deleteVacancy(String id){
        
        // check vacancy data
        Vacancy vacancy = vacancyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Data not found"));

        try {
            vacancyRepository.delete(vacancy); 
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {// check if delete fail
            throw new RuntimeException("Delete fail!");
        }
    }
    
}
