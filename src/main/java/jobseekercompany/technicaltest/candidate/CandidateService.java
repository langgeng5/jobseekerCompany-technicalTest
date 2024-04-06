package jobseekercompany.technicaltest.candidate;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jobseekercompany.technicaltest.candidate.dto.CandidateRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateService {
    
    private final CandidateRepository candidateRepository;
    private final Candidate candidate;

    public ResponseEntity<List<Candidate>> getCandidateList(String keyword, String sortBy, String direction){
        
        List<Candidate> response = candidate.findAllWithKeywordAndSort(keyword, sortBy, direction);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Candidate> insertCandidate(CandidateRequest request){

        Candidate candidate = new Candidate();
        candidate.setFullName(request.getFullName());
        candidate.setDob(request.getDob());
        candidate.setGender(request.getGender());

        try {
            candidate = candidateRepository.save(candidate);
            return ResponseEntity.status(HttpStatus.OK).body(candidate);
        } catch (DuplicateKeyException e) { // check duplicate exception
            throw new RuntimeException("FullName already exists");
        }
    }

    public ResponseEntity<Candidate> updateCandidate(String id, CandidateRequest request){

        // check candidate data
        Candidate candidate = candidateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Data not found"));

        candidate.setFullName(request.getFullName());
        candidate.setDob(request.getDob());
        candidate.setGender(request.getGender());

        try {
            candidate = candidateRepository.save(candidate);
            return ResponseEntity.status(HttpStatus.OK).body(candidate);
        } catch (DuplicateKeyException e) { // check duplicate exception
            throw new RuntimeException("FullName already exists");
        }
    }

    public ResponseEntity<Boolean> deleteCandidate(String id){

        // check candidate data
        Candidate candidate = candidateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Data not found"));

        try {
            candidateRepository.delete(candidate); 
            return ResponseEntity.ok().body(true);
        } catch (Exception e) { // check if delete fail
            throw new RuntimeException("Delete fail!");
        }
    }
    
}
