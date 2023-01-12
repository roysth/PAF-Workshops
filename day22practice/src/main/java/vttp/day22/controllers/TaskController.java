package vttp.day22.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.day22.models.Task;
import vttp.day22.models.User;
import vttp.day22.repositories.TaskRepository;
import vttp.day22.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping(path="/task")
public class TaskController {

    @Autowired
    private UserService userSvc;

    @Autowired
    private TaskRepository taskRepo;

    @PostMapping
    public String postTask(@RequestBody MultiValueMap<String, String> form, Model model) {

        //Assign the data collected to User object
        //Shorter way of writing compared to the one in UserController
        User user = new User();
        user.setUsername(form.getFirst("username"));
        user.setPassword(form.getFirst("password"));

        if (!userSvc.authenticate(user)) 
            return "fail_auth";

        Task task = new Task();
        task.setTaskName(form.getFirst("taskName"));
        task.setPriority(form.getFirst("priority"));
        task.setAssignTo(user);
        task.setCompletionDate(form.getFirst("completion"));

        Integer count = taskRepo.createTask(task);

        System.out.printf(">>>> %s, count: %d\n", task.toString(), count);

        return "createdTask";
    }

    
}