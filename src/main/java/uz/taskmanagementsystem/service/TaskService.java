package uz.taskmanagementsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.taskmanagementsystem.model.Comment;
import uz.taskmanagementsystem.model.Task;
import uz.taskmanagementsystem.model.enums.TaskPriority;
import uz.taskmanagementsystem.model.enums.TaskStatus;
import uz.taskmanagementsystem.model.record.TaskRecord;

import java.util.List;

public interface TaskService {

    Task createTask(TaskRecord taskRecord);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    Task updateTaskStatus(Long id, TaskStatus status);
    Task updateTaskPriority(Long id, TaskPriority priority);
    Task assignTaskToUser(Long id, Long assigneeId);
    Comment addCommentToTask(Long id, Comment comment);
    Task updateTaskStatusAsAssignee(Long id, TaskStatus status);
    Comment addCommentToTaskAsAssignee(Long id, Comment comment);
    Task getTaskById(Long id);
    Page<Task> getTasksByAuthor(Long authorId, Pageable pageable);
    Page<Task> getTasksByAssignee(Long assigneeId, Pageable pageable);
    List<Comment> getCommentsByTaskId(Long id);
    Page<Task> getAllTasks(TaskStatus status, TaskPriority priority, Long authorId, Long assigneeId, Pageable pageable);
}