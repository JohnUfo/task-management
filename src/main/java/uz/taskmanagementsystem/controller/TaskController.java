package uz.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.taskmanagementsystem.model.Comment;
import uz.taskmanagementsystem.model.Task;
import uz.taskmanagementsystem.model.enums.TaskStatus;
import uz.taskmanagementsystem.model.enums.TaskPriority;
import uz.taskmanagementsystem.service.impl.TaskServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new task", description = "Allows admins to create a new task")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task createdTask = taskServiceImpl.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Allows admins to update an existing task")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        Task updatedTask = taskServiceImpl.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Allows admins to delete a task")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskServiceImpl.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    @Operation(summary = "Update task status", description = "Allows admins to update the status of a task")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        Task updatedTask = taskServiceImpl.updateTaskStatus(id, status);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/priority")
    @Operation(summary = "Update task priority", description = "Allows admins to update the priority of a task")
    public ResponseEntity<Task> updateTaskPriority(@PathVariable Long id, @RequestParam TaskPriority priority) {
        Task updatedTask = taskServiceImpl.updateTaskPriority(id, priority);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/assignee")
    @Operation(summary = "Assign task to user", description = "Allows admins to assign a task to a user")
    public ResponseEntity<Task> assignTaskToUser(@PathVariable Long id, @RequestParam Long assigneeId) {
        Task updatedTask = taskServiceImpl.assignTaskToUser(id, assigneeId);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/comments")
    @Operation(summary = "Add comment to task", description = "Allows admins to add a comment to a task")
    public ResponseEntity<Comment> addCommentToTask(@PathVariable Long id, @Valid @RequestBody Comment comment) {
        Comment addedComment = taskServiceImpl.addCommentToTask(id, comment);
        return ResponseEntity.ok(addedComment);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/status/user")
    @Operation(summary = "Update task status as assignee", description = "Allows users to update the status of tasks assigned to them")
    public ResponseEntity<Task> updateTaskStatusAsAssignee(@PathVariable Long id, @RequestParam TaskStatus status) {
        Task updatedTask = taskServiceImpl.updateTaskStatusAsAssignee(id, status);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/comments/user")
    @Operation(summary = "Add comment to task as assignee", description = "Allows users to add comments to tasks assigned to them")
    public ResponseEntity<Comment> addCommentToTaskAsAssignee(@PathVariable Long id, @Valid @RequestBody Comment comment) {
        Comment addedComment = taskServiceImpl.addCommentToTaskAsAssignee(id, comment);
        return ResponseEntity.ok(addedComment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Retrieves a task by its ID")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskServiceImpl.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get tasks by author", description = "Retrieves tasks created by a specific author")
    public ResponseEntity<Page<Task>> getTasksByAuthor(@PathVariable Long authorId, Pageable pageable) {
        Page<Task> tasks = taskServiceImpl.getTasksByAuthor(authorId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assignee/{assigneeId}")
    @Operation(summary = "Get tasks by assignee", description = "Retrieves tasks assigned to a specific user")
    public ResponseEntity<Page<Task>> getTasksByAssignee(@PathVariable Long assigneeId, Pageable pageable) {
        Page<Task> tasks = taskServiceImpl.getTasksByAssignee(assigneeId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Get comments by task ID", description = "Retrieves all comments for a specific task")
    public ResponseEntity<List<Comment>> getCommentsByTaskId(@PathVariable Long id) {
        List<Comment> comments = taskServiceImpl.getCommentsByTaskId(id);
        return ResponseEntity.ok(comments);
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieves all tasks with optional filters and pagination")
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long assigneeId,
            Pageable pageable) {
        Page<Task> tasks = taskServiceImpl.getAllTasks(status, priority, authorId, assigneeId, pageable);
        return ResponseEntity.ok(tasks);
    }
}