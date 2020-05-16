package placeholder;

import exception.TooManyThingsToDo;
import model.Item;

import model.RegularItem;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class RegularItemTest {
    RegularItem gymItem;
    RegularItem emptyItem;
    RegularItem doneItem;
    RegularItem todoItem;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
    ToDoList toDoList;



    @BeforeEach
    public void setUp() throws ParseException {

        toDoList = new ToDoList();
        gymItem = new RegularItem("GYM", false, formatter.parse("04/04/1996"));
        emptyItem = new RegularItem("", true, formatter.parse("12/12/2222"));
        doneItem = new RegularItem("Cook", true, formatter.parse("33/33/1212"));
        todoItem = new RegularItem("Hangout", false, formatter.parse("55/55/5555"));
    }

    //private SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
    //Date date = formatter.parse(dueDate);

    //SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    //	String date = sdf.format(new Date());
    //	System.out.println(date); //15/10/2013
    @Test
    public void testGetters() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
        assertEquals("GYM", gymItem.getName()); // test case for name when non-empty string
        assertEquals("", emptyItem.getName()); // test case for name when empty string
        assertTrue(doneItem.getStatus()); // test case for status when true
        assertFalse(todoItem.getStatus()); // test case for status when false
        // test case for dueDate when date != null
        assertEquals(formatter.parse("04/04/1996"), gymItem.getDueDate());


    }
    @Test
    public void testSetName() {
        gymItem.setName("HW");
        assertTrue(gymItem.getName().equals("HW"));
    }

    @Test
    public void testSetStatus() {
        doneItem.setStatus(false);
        assertFalse(doneItem.getStatus());
    }

    @Test
    public void testSetDueDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
        todoItem.setDueDate(formatter.parse("44/44/4444"));

        assertEquals(todoItem.getDueDate(), formatter.parse("44/44/4444"));
    }

    @Test
    public void testGetPriority() {
        assertEquals(gymItem.getPriority(), 0);
    }

//    @Test
//    public void testGetToDoList() throws TooManyThingsToDo {
//        toDoList.addItem(gymItem);
//        assertTrue(toDoList.getTodo().contains(gymItem));
//        //assertEquals(toDoList, gymItem.getToDoList());
//    }

    @Test  //TODO do more test for exception
    public void testAddInToDoList() throws TooManyThingsToDo {
        gymItem.addInToDoList(toDoList);
        assertTrue(toDoList.getTodo().contains(gymItem));
    }

    @Test
    public void testRemoveInToDoList() throws TooManyThingsToDo {
        gymItem.addInToDoList(toDoList);
        assertTrue(toDoList.getTodo().contains(gymItem));
        gymItem.removeInToDoList();
        assertFalse(toDoList.getTodo().contains(gymItem));
    }

    @Test
    public void testHashCode() throws ParseException {
        RegularItem cookItem = new RegularItem("Cook", false, formatter.parse("33/33/1212"));

        assertTrue(cookItem.equals(doneItem) && doneItem.equals(cookItem));
        assertEquals(cookItem.hashCode(), doneItem.hashCode());
        assertFalse(cookItem.equals(gymItem) && gymItem.equals(cookItem));
    }

    @Test
    public void testEquals() throws ParseException {
        RegularItem cookItem = new RegularItem("Cook", false, formatter.parse("33/33/1212"));
        RegularItem otherCookItem = new RegularItem("Cooking", false, formatter.parse("33/33/1212"));
        RegularItem anotherCookItem = new RegularItem("Cook", false, formatter.parse("11/11/1111"));

        assertTrue(cookItem.equals(doneItem) && doneItem.equals(cookItem));
        assertFalse(cookItem.equals(gymItem) && gymItem.equals(cookItem));
        assertFalse(cookItem.getName().equals(otherCookItem.getName()));
        assertTrue(cookItem.getName().equals(anotherCookItem.getName()));
        assertFalse(cookItem.getDueDate().equals(anotherCookItem.getDueDate()));
        assertTrue(cookItem.getDueDate().equals(otherCookItem.getDueDate()));

        assertFalse(cookItem.equals(null));
        assertNotEquals(cookItem.getClass(), cookItem.equals(otherCookItem.getClass()));
    }
}
