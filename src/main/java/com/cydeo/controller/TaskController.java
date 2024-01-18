package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getTask(){
       List<TaskDTO> TaskDTOList = taskService.listAllTasks();
       return ResponseEntity.ok(new ResponseWrapper("Task are successfully retrieved",TaskDTOList, HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId){
        TaskDTO taskDTO = taskService.findById(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task are successfully retrieved",taskDTO, HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> creteTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is succesfully created",HttpStatus.CREATED));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted",HttpStatus.OK));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTask(){
       List<TaskDTO> taskDTOList =  taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks  are successfully retrieved",taskDTOList, HttpStatus.OK));
    }

    @PutMapping("/employee/update/")
    public ResponseEntity<ResponseWrapper> employeeUpdateTask(@RequestBody TaskDTO task){
        taskService.updateStatus(task);
        return ResponseEntity.ok(new ResponseWrapper("Tasks  are successfully updating", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    public ResponseEntity<ResponseWrapper> employeeArchivedTask(){
        List<TaskDTO> taskDTOS =  taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks  are successfully retrieved",taskDTOS, HttpStatus.OK));
    }
}
