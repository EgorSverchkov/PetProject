package ru.sverchkov.petproject.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sverchkov.petproject.model.entity.Task;
import ru.sverchkov.petproject.model.entity.User;
import ru.sverchkov.petproject.repository.TasksRepository;
import ru.sverchkov.petproject.repository.UsersRepository;

import java.security.Principal;

@Controller
public class PetProjectController {

    private static final Logger logger = LoggerFactory.getLogger(PetProjectController.class);


    private final TasksRepository tasksRepository;

    private final UsersRepository usersRepository;

    @Autowired
    public PetProjectController(TasksRepository tasksRepository, UsersRepository usersRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public String indexPage(Model model, Principal principal) {
        logger.info("User name {}", principal.getName());
        model.addAttribute("tasks", tasksRepository.findByUserUsername(principal.getName()));
        model.addAttribute("task", new Task());
        return "index";
    }


    @PostMapping
    public String addTask(Task task, Principal principal) {
        logger.info("User name {}", principal.getName());
        User user =usersRepository.findByUsername(principal.getName()).get();
        task.setUser(user);
        tasksRepository.save(task);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        tasksRepository.deleteById(id);
        return "redirect:/";
    }

}
