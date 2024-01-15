package edu.pnu.quizapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class QuizCompleted {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long completedId;

    @JsonProperty("id")
    @NonNull
    private long quizId;

    @JsonProperty("title")
    private String quizTitle;

    @JsonProperty("success")
    private boolean success;

    @JsonIgnore
    @NonNull
    private String email;

    @JsonProperty("completedAt")
    private final LocalDateTime completedAt = LocalDateTime.now();

    @JsonProperty("percentage")
    private double percentage;

    public QuizCompleted(long quizId, String quizTitle, boolean success, String email, double percentage) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.success = success;
        this.email = email;
        this.percentage = percentage;

    }

}
