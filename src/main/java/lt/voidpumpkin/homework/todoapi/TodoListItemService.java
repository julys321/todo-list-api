package lt.voidpumpkin.homework.todoapi;

import lt.voidpumpkin.homework.generated.db.tables.Todoitem;
import lt.voidpumpkin.homework.generated.db.tables.records.TodoitemRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoListItemService {

    @Autowired
    TodoListItemController todoListItemController;

    @Autowired
    private DSLContext dsl;

    private Todoitem todoItemTable = Todoitem.TODOITEM;

    List<TodoListItemResponse> fetchTodoListItemsFromDatabase(Boolean isArchived) {
        TodoitemRecord[] todoitemRecords = dsl
                .selectFrom(todoItemTable)
                .where(todoItemTable.ISARCHIVED.eq(isArchived))
                .fetchArray();
        return turnTodoItemRecordsToResponses(todoitemRecords);
    }

    TodoListItemResponse addNewTodoItemToDatabase(String todoListItemText) {
        TodoitemRecord result = dsl.insertInto(todoItemTable)
                .set(todoItemTable.TEXT, todoListItemText)
                .set(todoItemTable.CREATIONDATE, new Timestamp(Instant.now().toEpochMilli()))
                .set(todoItemTable.ISARCHIVED, false)
                .returning()
                .fetchOne();
        return new TodoListItemResponse(result);
    }

    TodoListItemResponse setDatabaseTodoItemAsArchived(Integer todoListItemId) {
        TodoitemRecord result = dsl.update(todoItemTable)
                .set(todoItemTable.ISARCHIVED, true)
                .where(todoItemTable.ID.eq(todoListItemId))
                .returning()
                .fetchOne();
        return new TodoListItemResponse(result);
    }

    private List<TodoListItemResponse> turnTodoItemRecordsToResponses(TodoitemRecord[] todoitemRecords) {
        List<TodoListItemResponse> todoListItemsFromDatabase = new ArrayList<>();
        for (TodoitemRecord todoitemRecord : todoitemRecords) {
            todoListItemsFromDatabase.add(new TodoListItemResponse(todoitemRecord));
        }
        return todoListItemsFromDatabase;
    }
}
