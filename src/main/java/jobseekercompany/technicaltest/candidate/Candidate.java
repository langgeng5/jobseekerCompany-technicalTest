package jobseekercompany.technicaltest.candidate;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import jobseekercompany.technicaltest.enumData.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// @Setter
// @Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Document(collection = "candidate")
public class Candidate {
    private static MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        Candidate.mongoTemplate = mongoTemplate;
    }
    
    @Id
    private String candidateId;
    @Indexed(unique = true)
    private String fullName;
    private LocalDate dob;
    private GenderEnum gender;
    
    // getter
    public String getCandidateId() {
        return candidateId;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public Integer getAge(){
        return Period.between(dob, LocalDate.now()).getYears();
    }

    // setter
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    // methods
    public List<Candidate> findAll(){
        return mongoTemplate.find(new Query(), Candidate.class);
    }

    public List<Candidate> findAllWithKeywordAndSort(String keyword, String sortBy, String direction){
        Query query = new Query();
        if (keyword != null && !keyword.isEmpty()) {
            query.addCriteria(Criteria.where("fullName").regex(keyword));
        }

        Sort sort = Sort.by(sortBy).ascending();
        if (direction.equalsIgnoreCase("DESC")) {
            sort = Sort.by(sortBy).descending();
        }
        query.with(sort);

        return mongoTemplate.find(query, Candidate.class);
    }

    
    
}
