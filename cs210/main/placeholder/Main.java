package placeholder;

import exception.MoreThanFive;
import exception.TooManyThingsToDo;
import model.ToDoList;
import ui.UserInput;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, MoreThanFive, TooManyThingsToDo {

        UserInput todoList = new UserInput();
        ToDoList toDoList = new ToDoList();


        try {
            todoList.run(toDoList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
