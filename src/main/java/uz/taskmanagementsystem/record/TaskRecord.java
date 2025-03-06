package uz.taskmanagementsystem.record;

import uz.taskmanagementsystem.model.enums.TaskStatus;
import uz.taskmanagementsystem.model.enums.TaskPriority;

import java.time.LocalDateTime;

public record TaskRecord(
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long assigneeId,
        LocalDateTime createdAt
) {
}