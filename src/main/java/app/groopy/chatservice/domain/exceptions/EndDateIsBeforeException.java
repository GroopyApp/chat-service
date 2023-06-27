package app.groopy.chatservice.domain.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EndDateIsBeforeException extends Exception {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public EndDateIsBeforeException(LocalDateTime startDate, LocalDateTime endDate) {
        super(String.format("endDate: %s is before startDate: %s", endDate.toString(), startDate.toString()));
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
