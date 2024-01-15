package edu.pnu.quizapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class QuizResult {

    @JsonProperty
    private double mark;
    @JsonProperty
    private String result;
    private final boolean success;

    public enum Result {
        SUCCESS(true, "Congratulations, you're right!"),
        FAIL(false, "Oops. Some answer was wrong.");

        Result(boolean success, String feedback) {
            this.feedback = feedback;
            this.success = success;
        }

        public final String feedback;
        public final boolean success;
    }

    public QuizResult(double mark, Result result) {
        this.mark = mark;
        this.result = result.feedback;
        this.success = result.success;
    }
}
