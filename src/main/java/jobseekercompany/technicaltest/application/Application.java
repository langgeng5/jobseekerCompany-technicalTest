package jobseekercompany.technicaltest.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import jobseekercompany.technicaltest.candidate.Candidate;
import jobseekercompany.technicaltest.vacancy.Vacancy;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


// @Getter
// @Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Document(collection = "candidate_apply")
public class Application {
    private static MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        Application.mongoTemplate = mongoTemplate;
    }
    
    @Id
    private String applyId;
    @DBRef
    private Candidate candidate;
    @DBRef
    private Vacancy vacancy;
    private LocalDate applyDate;
    private LocalDateTime createdDate;

    // getter
    public String getApplyId() {
        return applyId;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    // setter
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    // methods
    public List<Application> findAll(){
        return mongoTemplate.find(new Query(), Application.class);
    }

    public List<Application> findAllWithKeywordAndSort(String keyword, String sortBy, String direction){
        Criteria criteria = new Criteria();
        if (keyword != null && !keyword.isEmpty()) {
            criteria.orOperator(
                Criteria.where("candidate.candidateId").regex(keyword),
                Criteria.where("vacancy.vacancyId").regex(keyword)
            );
        }
        
        Query query = new Query(criteria);
        Sort sort = Sort.by(sortBy).ascending();
        if (direction.equalsIgnoreCase("DESC")) {
            sort = Sort.by(sortBy).descending();
        }
        query.with(sort);

        return mongoTemplate.find(query, Application.class);
    }

    
}
