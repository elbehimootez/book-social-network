package com.aymootez.book.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;

    @Column
    private Double note; //1-5 stars
    private String comment;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}