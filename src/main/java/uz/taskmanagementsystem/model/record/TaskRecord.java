package uz.taskmanagementsystem.model.record;

import uz.taskmanagementsystem.model.enums.TaskPriority;
import uz.taskmanagementsystem.model.enums.TaskStatus;

public record TaskRecord(
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long assigneeId
) {
}