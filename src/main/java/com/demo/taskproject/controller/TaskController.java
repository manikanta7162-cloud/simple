package com.demo.taskproject.controller;

import com.demo.taskproject.entity.Task;
import com.demo.taskproject.payload.TaskDto;
import com.demo.taskproject.service.TaskService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/{usersId}/task")
    public ResponseEntity<TaskDto> saveTask(@PathVariable(name = "usersId") long usersId , @RequestBody TaskDto taskDto)
    {
        return  new ResponseEntity<TaskDto>(taskService.saveTask(usersId, taskDto),HttpStatus.CREATED);
    }
    @GetMapping("/{usersId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasksByUsersId(@PathVariable(name = "usersId") long usersId)
    {
        return  new ResponseEntity<List<TaskDto>>(taskService.findAllTasksById(usersId),HttpStatus.OK);
    }

    @GetMapping("/{usersId}/{taskId}/fetch")
    public ResponseEntity<TaskDto> findByIds(@PathVariable (name = "usersId") long usersId,
                                             @PathVariable (name = "taskId") long taskId)
    {
        return  new ResponseEntity<>(taskService.findByUsersId(usersId,taskId),HttpStatus.OK);
    }
    @DeleteMapping("/{usersId}/{taskId}/delete")
    public ResponseEntity<String> deleteByIds(@PathVariable (name = "usersId") long usersId,
                                              @PathVariable (name = "taskId") long taskId)
    {
          taskService.deleteByUsersId(usersId,taskId);
        return  new ResponseEntity<>("Deleted Successully", HttpStatus.OK);
    }
}
