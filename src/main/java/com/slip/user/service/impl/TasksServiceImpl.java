package com.slip.user.service.impl;

import com.slip.user.Models.Tasks;
import com.slip.user.Models.User;
import com.slip.user.repositories.TasksRepostitory;
import com.slip.user.service.TasksService;
import com.slip.user.service.UserService;
import com.slip.user.util.AppUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TasksServiceImpl implements TasksService {
    private  final TasksRepostitory tasksRepostitory;
    private final UserService userService;

    public TasksServiceImpl(TasksRepostitory tasksRepostitory, UserService userService) {
        this.tasksRepostitory = tasksRepostitory;
        this.userService = userService;
    }

    @Override
    public Tasks findTaskByID(Long id){
        return tasksRepostitory.findById(id).orElseThrow();
    }

    @Override
    public String deleteTaskByID(Long id){
         tasksRepostitory.deleteById(id);
         return "Task deleted successfully";
    }
    @Override
    public Tasks updateTaskById(Long tasksId, Tasks tasksUpdated){
        Tasks tasks=this.findTaskByID(tasksId);
        tasks.setUpdatedAt(Instant.now());
        tasks.setDescription(tasksUpdated.getDescription());
        return tasksRepostitory.save(tasks);
    };

    @Override
    public List<Tasks> findAllTasksByUserId(String userRef){
        return tasksRepostitory.getUserByUserId(userRef);
    }
    @Override
    public Tasks save(Tasks tasks){
        final String userEmail= AppUtils.getUserEmail();
        User user = userService.getUserByEmail(userEmail);
        tasks.setUserRef(user.getRef().toString());

        tasks.setUpdatedAt(Instant.now());
        if(tasksRepostitory.getUserTaskCount(tasks.getUserRef())>5) throw new RuntimeException("User can't create more than 5 tasks");

        if(tasks.getRef()==null ) {
            tasks.setCreatedAt(Instant.now());
            tasks.setRef(UUID.randomUUID().toString());
        }
        Tasks task= tasksRepostitory.save(tasks);
        tasksRepostitory.createPlannedRelation(task.getRef(),userEmail);
        return task;

    }
}
