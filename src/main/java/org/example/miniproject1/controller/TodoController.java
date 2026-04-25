package org.example.miniproject1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.miniproject1.model.Todo;
import org.example.miniproject1.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    // Hiển thị danh sách
    @GetMapping
    public String home(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "todo-list";
    }

    // Hiển thị form thêm
    @GetMapping("/add")
    public String viewAdd(Model model) {
        model.addAttribute("todo", new Todo());
        return "todo-form";
    }

    // Lưu dữ liệu
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "todo-form";
        }

        todoRepository.save(todo);
        return "redirect:/";
    }
}