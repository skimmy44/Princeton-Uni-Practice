package placeholder;

import model.Item;
import model.UrgentItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class UrgentItemTest {

    UrgentItem gymItem;
    UrgentItem emptyItem;
    UrgentItem doneItem;
    UrgentItem todoItem;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");

    @BeforeEach
    public void setUp() throws ParseException {


        gymItem = new UrgentItem("GYM", false, formatter.parse("04/04/1996"), 1);
        emptyItem = new UrgentItem("", true, formatter.parse("11/11/2011"), 2);
        doneItem = new UrgentItem("Cook", true, formatter.parse("12/12/2012"), 3);
        todoItem = new UrgentItem("Hangout", false, formatter.parse("01/01/2010"), 4);
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
        // test case for dueDate when date == null (date has not been initialized)

        assertEquals(1, this.gymItem.getPriority());
        assertEquals(3, doneItem.getPriority());
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

    //TODO hashCode 랑 equals TEST하기 그냥 RegularItemTest에 했던거처럼 똑같이

//    @Test
//    public void testGetPriority() {
//        assertEquals(gymItem.getPriority(), 5);
//    }

}

