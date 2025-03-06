package uz.taskmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.taskmanagementsystem.model.Task;
import uz.taskmanagementsystem.model.enums.TaskStatus;
import uz.taskmanagementsystem.model.enums.TaskPriority;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find tasks by status with pagination
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    // Find tasks by priority with pagination
    Page<Task> findByPriority(TaskPriority priority, Pageable pageable);

    // Find tasks by author ID with pagination
    Page<Task> findByAuthorId(Long authorId, Pageable pageable);

    // Find tasks by assignee ID with pagination
    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);

    // Filter tasks by status and priority with pagination
    Page<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority, Pageable pageable);

    // Filter tasks by status and author ID with pagination
    Page<Task> findByStatusAndAuthorId(TaskStatus status, Long authorId, Pageable pageable);

    // Filter tasks by status and assignee ID with pagination
    Page<Task> findByStatusAndAssigneeId(TaskStatus status, Long assigneeId, Pageable pageable);

    // Filter tasks by priority and author ID with pagination
    Page<Task> findByPriorityAndAuthorId(TaskPriority priority, Long authorId, Pageable pageable);

    // Filter tasks by priority and assignee ID with pagination
    Page<Task> findByPriorityAndAssigneeId(TaskPriority priority, Long assigneeId, Pageable pageable);

    // Filter tasks by author ID and assignee ID with pagination
    Page<Task> findByAuthorIdAndAssigneeId(Long authorId, Long assigneeId, Pageable pageable);

    // Filter tasks by status, priority, and author ID with pagination
    Page<Task> findByStatusAndPriorityAndAuthorId(TaskStatus status, TaskPriority priority, Long authorId, Pageable pageable);

    // Filter tasks by status, priority, and assignee ID with pagination
    Page<Task> findByStatusAndPriorityAndAssigneeId(TaskStatus status, TaskPriority priority, Long assigneeId, Pageable pageable);

    // Filter tasks by status, author ID, and assignee ID with pagination
    Page<Task> findByStatusAndAuthorIdAndAssigneeId(TaskStatus status, Long authorId, Long assigneeId, Pageable pageable);

    // Filter tasks by priority, author ID, and assignee ID with pagination
    Page<Task> findByPriorityAndAuthorIdAndAssigneeId(TaskPriority priority, Long authorId, Long assigneeId, Pageable pageable);

    // Filter tasks by status, priority, author ID, and assignee ID with pagination
    Page<Task> findByStatusAndPriorityAndAuthorIdAndAssigneeId(TaskStatus status, TaskPriority priority, Long authorId, Long assigneeId, Pageable pageable);
}