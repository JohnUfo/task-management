package uz.taskmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.taskmanagementsystem.exception.ResourceNotFoundException;
import uz.taskmanagementsystem.exception.UnauthorizedAccessException;
import uz.taskmanagementsystem.model.Comment;
import uz.taskmanagementsystem.model.Task;
import uz.taskmanagementsystem.model.User;
import uz.taskmanagementsystem.model.enums.TaskStatus;
import uz.taskmanagementsystem.model.enums.TaskPriority;
import uz.taskmanagementsystem.repository.TaskRepository;
import uz.taskmanagementsystem.repository.UserRepository;
import uz.taskmanagementsystem.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setAuthor(task.getAuthor());
        existingTask.setAssignee(task.getAssignee());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

    @Override
    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskPriority(Long id, TaskPriority priority) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setPriority(priority);
        return taskRepository.save(task);
    }

    @Override
    public Task assignTaskToUser(Long id, Long assigneeId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + assigneeId));

        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    @Override
    public Comment addCommentToTask(Long id, Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        comment.setTask(task);
        task.getComments().add(comment);
        taskRepository.save(task);

        return comment;
    }

    @Override
    public Task updateTaskStatusAsAssignee(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!task.getAssignee().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to update this task");
        }

        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public Comment addCommentToTaskAsAssignee(Long id, Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!task.getAssignee().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to add a comment to this task");
        }

        comment.setTask(task);
        task.getComments().add(comment);
        taskRepository.save(task);

        return comment;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @Override
    public Page<Task> getTasksByAuthor(Long authorId, Pageable pageable) {
        return taskRepository.findByAuthorId(authorId, pageable);
    }

    @Override
    public Page<Task> getTasksByAssignee(Long assigneeId, Pageable pageable) {
        return taskRepository.findByAssigneeId(assigneeId, pageable);
    }

    @Override
    public List<Comment> getCommentsByTaskId(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return task.getComments();
    }

    @Override
    public Page<Task> getAllTasks(TaskStatus status, TaskPriority priority, Long authorId, Long assigneeId, Pageable pageable) {
        if (status != null && priority != null && authorId != null && assigneeId != null) {
            return taskRepository.findByStatusAndPriorityAndAuthorIdAndAssigneeId(status, priority, authorId, assigneeId, pageable);
        } else if (status != null && priority != null && authorId != null) {
            return taskRepository.findByStatusAndPriorityAndAuthorId(status, priority, authorId, pageable);
        } else if (status != null && priority != null && assigneeId != null) {
            return taskRepository.findByStatusAndPriorityAndAssigneeId(status, priority, assigneeId, pageable);
        } else if (status != null && authorId != null && assigneeId != null) {
            return taskRepository.findByStatusAndAuthorIdAndAssigneeId(status, authorId, assigneeId, pageable);
        } else if (priority != null && authorId != null && assigneeId != null) {
            return taskRepository.findByPriorityAndAuthorIdAndAssigneeId(priority, authorId, assigneeId, pageable);
        } else if (status != null && priority != null) {
            return taskRepository.findByStatusAndPriority(status, priority, pageable);
        } else if (status != null && authorId != null) {
            return taskRepository.findByStatusAndAuthorId(status, authorId, pageable);
        } else if (status != null && assigneeId != null) {
            return taskRepository.findByStatusAndAssigneeId(status, assigneeId, pageable);
        } else if (priority != null && authorId != null) {
            return taskRepository.findByPriorityAndAuthorId(priority, authorId, pageable);
        } else if (priority != null && assigneeId != null) {
            return taskRepository.findByPriorityAndAssigneeId(priority, assigneeId, pageable);
        } else if (authorId != null && assigneeId != null) {
            return taskRepository.findByAuthorIdAndAssigneeId(authorId, assigneeId, pageable);
        } else if (status != null) {
            return taskRepository.findByStatus(status, pageable); // Fixed
        } else if (priority != null) {
            return taskRepository.findByPriority(priority, pageable); // Fixed
        } else if (authorId != null) {
            return taskRepository.findByAuthorId(authorId, pageable);
        } else if (assigneeId != null) {
            return taskRepository.findByAssigneeId(assigneeId, pageable);
        } else {
            return taskRepository.findAll(pageable);
        }
    }
}