package com.demo.taskproject.serviceimpl;

import com.demo.taskproject.entity.Task;
import com.demo.taskproject.entity.Users;
import com.demo.taskproject.exception.APIException;
import com.demo.taskproject.exception.TaskNotFound;
import com.demo.taskproject.exception.UserNotFound;
import com.demo.taskproject.payload.TaskDto;
import com.demo.taskproject.repository.TaskRepository;
import com.demo.taskproject.repository.UsersRepository;
import com.demo.taskproject.service.TaskService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDto saveTask(long usersId, TaskDto taskDto) {
    Users users =  usersRepository.findById(usersId).orElseThrow(() -> new UserNotFound(String.format("This %d not found",usersId)));
        Task task = modelMapper.map(taskDto,Task.class);
        task.setUsers(users);
        Task saveTask = taskRepository.save(task);
        TaskDto save = modelMapper.map(saveTask,TaskDto.class);

        return save;
    }

    @Override
    public List<TaskDto> findAllTasksById(long usersId) {
        usersRepository.findById(usersId).orElseThrow(() -> new UserNotFound(String.format("this user % not found",usersId)));
        List<Task> tasks = taskRepository.findAllTasksByUsersId(usersId);
        return tasks.stream().map(m -> modelMapper.map(m,TaskDto.class)).collect(Collectors.toList());

    }

    @Override
    public TaskDto findByUsersId(long usersId, long taskId) {
        Users user = usersRepository.findById(usersId).orElseThrow(() -> new UserNotFound(String.format("user id %d not found") ));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFound(String.format("task id %d not found ")));
         if(user.getId() != task.getUsers().getId())
         {
             throw   new APIException("User Id and task id  are not matched");
         }
        Task list =  taskRepository.findByUsersIdAndId(usersId,taskId);
        return  modelMapper.map(list,TaskDto.class);
    }

    @Override
    @Transactional
    public void deleteByUsersId(long usersId, long taskId) {

        Users user = usersRepository.findById(usersId)
                .orElseThrow(() ->
                        new UserNotFound(
                                String.format("user id %d not found", usersId)
                        ));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFound(
                                String.format("task id %d not found", taskId)
                        ));

        if (Objects.equals(user.getId(), task.getUsers().getId())) {
            taskRepository.deleteByUsersIdAndId(usersId, taskId);
        } else {

            throw new APIException(
                    "User Id and task id are not matched"
            );
        }

    }
}
