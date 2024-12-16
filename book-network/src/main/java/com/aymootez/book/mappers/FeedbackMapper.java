package com.aymootez.book.mappers;

import com.aymootez.book.models.dtos.feedback.FeedbackRequest;
import com.aymootez.book.models.entities.Book;
import com.aymootez.book.models.entities.Feedback;
import com.aymootez.book.models.dtos.feedback.FeedbackResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {


    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .shareable(false) // Not required and has no impact :: just to satisfy lombok : boolean ->Boolean
                        .archived(false) // Not required and has no impact :: just to satisfy lombok
                        .build()
                )
                .build();
    }


    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
                .build();
    }
}
