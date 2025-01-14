package com.aymootez.book.specifications;

import com.aymootez.book.models.entities.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    private BookSpecification(){
    }

    public static Specification<Book> withOwnerId(Integer ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
