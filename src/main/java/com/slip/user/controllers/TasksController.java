package com.slip.user.controllers;

import com.slip.user.Models.Tasks;
import com.slip.user.Models.User;
import com.slip.user.service.TasksService;
import com.slip.user.service.UserService;
import com.slip.user.util.AppUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/tasks")
public class TasksController {
    private final TasksService tasksService;

    private final UserService userService;



    public TasksController(TasksService tasksService, UserService userService) {
        this.tasksService = tasksService;
        this.userService = userService;
    }

    @GetMapping()
    public Map<String, List<Tasks>> getTasksByUserID(){
        final String userEmail= AppUtils.getUserEmail();
        User user = userService.getUserByEmail(userEmail);
        return Map.of("tasks",tasksService.findAllTasksByUserId(user.getRef().toString()));
    }
    @GetMapping("/{id}")
    public Tasks getTasksID(@PathVariable String id){
        return tasksService.findTaskByID(Long.valueOf(id));
    }
    @DeleteMapping("/{taskId}")
    public String deleteTasksID(@PathVariable String taskId){
        return tasksService.deleteTaskByID(Long.valueOf(taskId));
    }
    @PutMapping("/{id}")
    public Tasks updateTasks(@PathVariable Long  id, @RequestBody Tasks tasks){
        return tasksService.updateTaskById(id,tasks);
    }
    @PostMapping()
    public ResponseEntity<Tasks> addTasksForUser( @RequestBody Tasks tasks){
        return ResponseEntity.ok(tasksService.save(tasks));
    }
}
