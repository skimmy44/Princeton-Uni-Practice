package placeholder;

import exception.TooManyThingsToDo;
import model.Item;

import model.RegularItem;
import model.UrgentItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.ToDoList;
import ui.UserInput;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList toDoList;
    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;
    private Item item6;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

    @BeforeEach
    public void setUp() throws ParseException {
        toDoList = new ToDoList();


        item1 = new RegularItem("GYM", false, formatter.parse("05/10/1996"));
        item2 = new RegularItem("Cook", true, null);
        item3 = new RegularItem("HomeWork", false, null);
        item4 = new RegularItem("Sleep", true, null);
        item5 = new RegularItem("Study", false, formatter.parse("04/04/1999"));
        item6 = new UrgentItem("Please", false, formatter.parse("26/11/2019"), 2);


    }




//    ///////////끝내야함  TODO
//    @Test
//    public void testCreateItem() {
//        toDoList.getTodo().add(item1);
//        assertTrue(toDoList.getTodo().contains(item1));
//    }

    @Test
    public void testGetToDo() throws TooManyThingsToDo {
        toDoList.addItemInToDo(item1);
        assertEquals(toDoList.getTodo().get(0), item1);
    }

    @Test
    public void testGetDone() throws TooManyThingsToDo {
        toDoList.addItemInToDo(item1);
        toDoList.addItemInToDo(item2);
        toDoList.crossOff(1);
        assertEquals(1, toDoList.getDone().size());
    }




    @Test //TODO do more test for exception
    public void testAddItem() throws TooManyThingsToDo {
        // false because item has not yet been added
        assertFalse(toDoList.getTodo().contains(item1));

        // add item
        toDoList.addItemInToDo(item1);

        // true because item1 has been added to todo in todoList
        assertTrue(toDoList.getTodo().contains(item1));

        assertFalse(toDoList.maxSize());

        try {
            toDoList.addItemInToDo(item2);
        } catch (TooManyThingsToDo tooManyThingsToDo) {
            fail("Too many list");
        } finally {
            System.out.println("");
        }

        assertFalse(toDoList.maxSize());

        toDoList.addItemInToDo(item3);
        toDoList.addItemInToDo(item2);
        try {
            toDoList.addItemInToDo(item4);
            fail("Too many items");
        } catch (TooManyThingsToDo tooManyThingsToDo) {
            System.out.println("Too many items");
        } finally {
            System.out.println("");
        }
//        try {
//            toDoList.maxSize();
//        } catch (TooManyThingsToDo tooManyThingsToDo) {
//            fail("Too many list");
//
//        }

    }

    @Test //TODO do the test for else case
    public void testCrossOffIfValid() throws TooManyThingsToDo {
        toDoList.addItemInToDo(item3);

        toDoList.crossOff(0);

        assertFalse(toDoList.getTodo().contains(item3));
        assertTrue(toDoList.getDone().contains(item3));
        // todoList -> done -> item3 -> check if status is true
        assertTrue(toDoList.getDone().get(0).getStatus());
    }

    @Test
    public void testSaveAndLoad() throws IOException, TooManyThingsToDo {
        toDoList.addItemInToDo(item1);
//        item1 = new Item("GYM", false, new Date(119, Calendar.SEPTEMBER, 23));
//        item2 = new Item("Cook", true, null);
//        item3 = new Item("HomeWork", false, null);
        toDoList.save();
//        String firstItem = "GYM false "
//
        List<String> lines = Files.readAllLines(Paths.get("./data/todoData.txt"));
        assertEquals("GYM false 05/10/1996 0", lines.get(0));

        toDoList.load();
        assertEquals(toDoList.getTodo().size(), 1);
    }

    @Test
    public void testGetItem() throws TooManyThingsToDo, ParseException {
        assertEquals(toDoList.getItem("Nooooooo", formatter.parse("01/01/1111"), 0),
                new RegularItem("Nooooooo", false, formatter.parse("01/01/1111")));

        assertEquals(toDoList.getItem("Yesss", formatter.parse("77/77/7777"), 3),
                new UrgentItem("Yesss", false, formatter.parse("77/77/7777"), 3));
    }

}
