package com.karabocodes.Todo.service.impl;

import com.karabocodes.Todo.dto.TodoDto;
import com.karabocodes.Todo.entity.Todo;
import com.karabocodes.Todo.exception.ResourcesNotFoundException;
import com.karabocodes.Todo.repository.TodoRepository;
import com.karabocodes.Todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        //convert tododo into jpa enitity
//        Todo todo= new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todo.isCompleted());

        Todo todo = modelMapper.map(todoDto, Todo.class);

        //Todo jpa entity
        Todo savedTodo = todoRepository.save(todo);

        //convvert saved tod jpa enity objcet into tododto object
//         TodoDto savedTodoDto = new TodoDto();
//         savedTodoDto.setId(todoDto.getId());
//         savedTodoDto.setTitle(todoDto.getTitle());
//         savedTodoDto.setDescription(todoDto.getDescription());
//         savedTodoDto.setCompleted(todo.isCompleted());
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
         return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Todo not found with id" + id));

        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List <Todo> todos = todoRepository.findAll();

        return todos.stream().map((todo)-> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(()-> new ResourcesNotFoundException("Todo not found with id :"+ id));
        todo.setTitle(todo.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todo.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
