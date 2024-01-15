package edu.pnu.quizapp.services;

import edu.pnu.quizapp.entities.UserAnswer;
import edu.pnu.quizapp.exceptions.QuizNotFoundException;
import edu.pnu.quizapp.exceptions.UserNotAllowedException;
import edu.pnu.quizapp.entities.Quiz;
import edu.pnu.quizapp.entities.QuizCompleted;
import edu.pnu.quizapp.entities.QuizResult;
import edu.pnu.quizapp.repositories.QuizCompletedRepository;
import edu.pnu.quizapp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class QuizService {

    private static final int PAGE_SIZE = 10;

    private QuizRepository quizRepo;
    private QuizCompletedRepository quizCompletedRepo;

    @Autowired
    public QuizService(QuizRepository quizRepo, QuizCompletedRepository quizCompletedRepo) {
        this.quizRepo = quizRepo;
        this.quizCompletedRepo = quizCompletedRepo;
    }

    public Quiz createQuiz(Quiz quiz, String loggedInUser) {
        quiz.setEmail(loggedInUser);
        return quizRepo.save(quiz);
    }

    public void deleteQuiz(long id, String loggedInUser) {
        if (getQuiz(id).getEmail().equals(loggedInUser)) {
            quizRepo.deleteById(id);
        } else {
            throw new UserNotAllowedException();
        }
    }

    public Quiz getQuiz(long id) {
        Optional<Quiz> quizFromDb = quizRepo.findById(id);
        if (quizFromDb.isEmpty()) throw new QuizNotFoundException();
        return quizFromDb.get();
    }

    public Page<Quiz> getAllQuizzes(int pageNo) {
        return quizRepo.findAll(PageRequest.of(pageNo, PAGE_SIZE));
    }

    public Page<QuizCompleted> getAllCompletedQuizzes(int pageNo, String loggedInUser) {
        return quizCompletedRepo.findAllCompletedQuizzes(loggedInUser, PageRequest.of(pageNo, PAGE_SIZE));
    }

    public QuizResult checkQuizSubmission(long quizId, UserAnswer userAnswers, String loggedInUser) {
        Quiz quiz = getQuiz(quizId);
        int totalQuestions = quiz.getQuestions().size();
        int correctCount = 0;

        for (int i = 0; i < totalQuestions; i++) {
            if (quiz.getQuestions().get(i).checkAnswer(userAnswers.getAnswers().get(i))) {
                correctCount++;
            }
        }

        double percentage = ((double) correctCount / totalQuestions) * 100;

        if (percentage == 100.0) {
            quizCompletedRepo.save(new QuizCompleted(
                    quizId,
                    quiz.getTitle(),
                    true,
                    loggedInUser,
                    percentage
            ));
            return new QuizResult(percentage, QuizResult.Result.SUCCESS);
        } else {
            quizCompletedRepo.save(new QuizCompleted(
                    quizId,
                    quiz.getTitle(),
                    false,
                    loggedInUser,
                    percentage
            ));
            return new QuizResult(percentage, QuizResult.Result.FAIL);
        }
    }
    public Page<Quiz> getAllQuizzesByUser(int pageNo, String loggedInUser) {
        return quizRepo.findByEmail(loggedInUser, PageRequest.of(pageNo, PAGE_SIZE));
    }


}
