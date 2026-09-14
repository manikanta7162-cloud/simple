package com.demo.taskproject.service;

import com.demo.taskproject.payload.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    public TaskDto saveTask(long usersId, TaskDto taskDto);

    public List<TaskDto> findAllTasksById(long usersId);

    public TaskDto findByUsersId(long usersId, long taskId);

    public void deleteByUsersId(long usersId, long taskId);
}
