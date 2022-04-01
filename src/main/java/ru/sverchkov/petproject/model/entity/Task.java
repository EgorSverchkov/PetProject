package ru.sverchkov.petproject.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "task_items")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskName;

    @ManyToOne
    private User user;



}
