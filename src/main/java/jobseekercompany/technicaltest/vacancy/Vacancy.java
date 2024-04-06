package jobseekercompany.technicaltest.vacancy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import jobseekercompany.technicaltest.enumData.RequirementGenderEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


// @Getter
// @Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Document(collection = "vacancy")
public class Vacancy {
    private static MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        Vacancy.mongoTemplate = mongoTemplate;
    }
    
    @Id
    private String vacancyId;
    @Indexed(unique = true)
    private String vacancyName;
    private Integer minAge;
    private Integer maxAge;
    private RequirementGenderEnum requirementGender;
    private LocalDateTime createdDate;
    private LocalDate expiredDate;
    
    //getter
    public String getVacancyId() {
        return vacancyId;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public RequirementGenderEnum getRequirementGender() {
        return requirementGender;
    }

    public LocalDateTime getcreatedDate() {
        return createdDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    // setter
    public void setVacancyId(String vacancyId) {
        this.vacancyId = vacancyId;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public void setRequirementGender(RequirementGenderEnum requirementGender) {
        this.requirementGender = requirementGender;
    }

    public void setcreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    // methods
    public List<Vacancy> findAll(){
        return mongoTemplate.find(new Query(), Vacancy.class);
    }

    public List<Vacancy> findAllWithKeywordAndSort(String keyword, String sortBy, String direction){
        Query query = new Query();
        if (keyword != null && !keyword.isEmpty()) {
            query.addCriteria(Criteria.where("vacancyName").regex(keyword));
        }

        Sort sort = Sort.by(sortBy).ascending();
        if (direction.equalsIgnoreCase("DESC")) {
            sort = Sort.by(sortBy).descending();
        }
        query.with(sort);

        return mongoTemplate.find(query, Vacancy.class);
    }
}
