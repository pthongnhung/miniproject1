package org.example.miniproject1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.miniproject1.model.Todo;
import org.example.miniproject1.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String viewEdit(
            @PathVariable Long id,
            Model model
    ) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if(optionalTodo.isPresent()){
            model.addAttribute("todo", optionalTodo.get());
            return "todo-edit";
        }

        return "redirect:/";
    }

    // Lưu dữ liệu sửa
    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult result
    ) {
        if(result.hasErrors()){
            return "todo-edit";
        }

        todoRepository.save(todo);
        return "redirect:/";
    }

//    Xóa
@GetMapping("/delete/{id}")
public String delete(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes
) {

    if (!todoRepository.existsById(id)) {
        redirectAttributes.addFlashAttribute(
                "message",
                "Task không tồn tại!"
        );
        return "redirect:/";
    }

    todoRepository.deleteById(id);

    redirectAttributes.addFlashAttribute(
            "message",
            "Xóa thành công!"
    );

    return "redirect:/";
}

}