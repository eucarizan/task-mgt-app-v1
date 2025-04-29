package dev.nj.tms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nj.tms.dictionaries.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createdAt;

    private AppUser author;

    public Task() {}

    public Task(String title, String description, AppUser author) {
        setTitle(title);
        setDescription(description);
        this.status = TaskStatus.CREATED;
        this.createdAt = LocalDateTime.now();
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Optional.ofNullable(title)
                .orElseThrow(() -> new IllegalArgumentException("Title cannot be null"));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Optional.ofNullable(description)
                .orElseThrow(() -> new IllegalArgumentException("Description cannot be null"));
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }
}
