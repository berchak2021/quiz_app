package edu.pnu.quizapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull
    @Size(min = 1, message = "The quiz must have at least 1 questions")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Question> questions;

    @JsonIgnore
    private String email;

    public boolean checkAnswers(List<List<Integer>> userAnswers) {

        if (questions.size() != userAnswers.size()) {
            return false;
        }

        for (int i = 0; i < questions.size(); i++) {
            if (!questions.get(i).checkAnswer(userAnswers.get(i))) {
                return false;
            }
        }

        return true;
    }
}
