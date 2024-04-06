package jobseekercompany.technicaltest.candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jobseekercompany.technicaltest.candidate.dto.CandidateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
@Validated
public class CandidateController {
    
    private final CandidateService candidateService;

    // get list data
    @GetMapping("/")
    public ResponseEntity<List<Candidate>> getCandidates(
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false, defaultValue = "id") String sortBy,
        @RequestParam(required = false, defaultValue = "ASC") String direction) {
        return candidateService.getCandidateList(keywords, sortBy, direction);
    }
    
    // post insert data
    @PostMapping
    public ResponseEntity<Candidate> insertCandidate(
        @RequestBody @Valid CandidateRequest request) {
        return candidateService.insertCandidate(request);
    }

    // update data by id
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(
        @PathVariable String id, 
        @RequestBody @Valid CandidateRequest request) {
        
        return candidateService.updateCandidate(id, request);
    }

    // delete data by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCandidate(
        @PathVariable String id) {
        
        return candidateService.deleteCandidate(id);
    }

    // exception handler return if any request fail
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
