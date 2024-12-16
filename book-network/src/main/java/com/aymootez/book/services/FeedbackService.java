package com.aymootez.book.services;

import com.aymootez.book.mappers.FeedbackMapper;
import com.aymootez.book.repositories.FeedbackRepository;
import com.aymootez.book.models.dtos.feedback.FeedbackRequest;
import com.aymootez.book.models.entities.Book;
import com.aymootez.book.repositories.BookRepository;
import com.aymootez.book.models.dtos.PageResponse;
import com.aymootez.book.models.dtos.exception.OperationNotPermittedException;
import com.aymootez.book.models.entities.Feedback;
import com.aymootez.book.models.dtos.feedback.FeedbackResponse;
import com.aymootez.book.models.entities.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + request.bookId()));
        if(book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("You cannot give a feedback for an archived or not shereable book");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot give a feedback tp your own book");
        }

        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbackByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = feedbackRepository.findAllFeedbackByBook(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(feedback -> feedbackMapper.toFeedbackResponse(feedback, user.getId())).toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast());
    }
}
