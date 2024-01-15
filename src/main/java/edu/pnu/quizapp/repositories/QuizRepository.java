package edu.pnu.quizapp.repositories;

import edu.pnu.quizapp.entities.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long>, CrudRepository<Quiz, Long> {

    Page<Quiz> findByEmail(String email, Pageable pageable);
}
