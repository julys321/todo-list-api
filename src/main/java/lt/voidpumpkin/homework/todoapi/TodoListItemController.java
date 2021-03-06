package lt.voidpumpkin.homework.todoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoListItemController {

    @Autowired
    TodoListItemService todoListItemService;


    @CrossOrigin
    @RequestMapping(value = "/todoList", method = RequestMethod.GET)
    public List<TodoListItemResponse> getNotArchivedTodoList() {
        return todoListItemService.fetchTodoListItemsFromDatabase(false);
    }

    @CrossOrigin
    @RequestMapping(value = "/todoList/archived", method = RequestMethod.GET)
    public List<TodoListItemResponse> getArchivedTodoList() {
        return todoListItemService.fetchTodoListItemsFromDatabase(true);
    }

    @CrossOrigin
    @RequestMapping(value = "/todoListItem", method = RequestMethod.POST)
    public TodoListItemResponse addNewTodoItem(@RequestBody String todoListItemText) {
        return todoListItemService.addNewTodoItemToDatabase(todoListItemText);
    }

    @CrossOrigin
    @RequestMapping(value = "/archiveTodoListItem/{todoListItemId}", method = RequestMethod.PUT)
    public TodoListItemResponse archiveTodoItem(@PathVariable Integer todoListItemId) {
        return todoListItemService.setDatabaseTodoItemAsArchived(todoListItemId);
    }

}
