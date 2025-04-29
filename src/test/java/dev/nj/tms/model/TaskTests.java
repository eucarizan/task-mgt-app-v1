package dev.nj.tms.model;

import dev.nj.tms.dictionaries.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTests {

    private final AppUser testUser = new AppUser("user1@mail.com", "user1Pass!");

    @Test
    public void taskCreationTest() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("new task");
        task.setDescription("a task for anyone");
        task.setStatus(TaskStatus.CREATED);
        task.setCreatedAt(LocalDateTime.now());
        task.setAuthor(testUser);

        assertEquals(1L, task.getId());
        assertEquals("new task", task.getTitle());
        assertEquals("a task for anyone", task.getDescription());
        assertEquals(TaskStatus.CREATED, task.getStatus());
        assertTrue(ChronoUnit.SECONDS.between(task.getCreatedAt(), LocalDateTime.now()) < 1);
        assertEquals("user1@mail.com", task.getAuthor().getEmail());
    }

    @Test
    public void titleCannotBeNullTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new Task(null, "description", testUser));
    }

    @Test
    public void titleCannotBeBlankTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new Task("", "description", testUser));
    }

    @Test
    public void descriptionCannotBeNullTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new Task("title", null, testUser));
    }

    @Test
    public void descriptionCannotBeBlankTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new Task("title", "", testUser));
    }
}
