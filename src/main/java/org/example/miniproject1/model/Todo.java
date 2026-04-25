package org.example.miniproject1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @Column(name = "due_date")
    @FutureOrPresent(message = "Ngày phải từ hôm nay trở đi")
    private LocalDate dueDate;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private String priority;
}