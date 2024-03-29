package edu.pnu.quizapp.repositories;

import edu.pnu.quizapp.entities.QuizCompleted;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletedRepository extends PagingAndSortingRepository<QuizCompleted, Long>,
        CrudRepository<QuizCompleted, Long> {

    @Query("SELECT quiz FROM QuizCompleted quiz WHERE quiz.email = :email ORDER BY quiz.completedAt DESC")
    Page<QuizCompleted> findAllCompletedQuizzes(String email, Pageable pageable);

}
