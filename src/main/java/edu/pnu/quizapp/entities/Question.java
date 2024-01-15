package edu.pnu.quizapp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotNull
    @Size(min = 2, message = "The question must have at least 2 answer options.")
    @ElementCollection
    private List<String> options;

    @NonNull
    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "answer")
    private List<Integer> answers;

    public boolean checkAnswer(List<Integer> userAnswer) {
        return new HashSet<>(userAnswer).equals(new HashSet<>(answers));
    }
}
