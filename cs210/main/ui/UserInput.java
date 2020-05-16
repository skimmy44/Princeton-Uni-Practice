package ui;

import exception.MoreThanFive;
import exception.TooManyThingsToDo;
import model.Item;
import model.RegularItem;
import model.ToDoList;
import model.UrgentItem;
import network.ReadWebPageEx;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class UserInput {
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    private ToDoList toDoList;
    private Scanner scanner = new Scanner(System.in);


    public void run(ToDoList toDoList) throws ParseException, IOException, TooManyThingsToDo, MoreThanFive {
        this.toDoList = toDoList;
        // add two items
        toDoList.load();
        showAll();
        introduceList(toDoList);

        // show indexes
        //showTodoIndex(); //I think I dont need it

        // show initial state
        showAll();

        // cross off first item (index 0)
        //Item item = toDoList.getItem("Gym", new Date());
        //item.done(); //TODO fix it later
        // toDoList.crossOff(0);

        // show the changed state
    }

    //TODO deliverable 9!
    //EFFECT print all the items in the list
    public void showAll() {
        printOutAllTodoItem(toDoList.getTodo(), "TODO :  ");
        printOutAllTodoItem(toDoList.getDone(), "DONE :  ");
    }

    private void printOutAllTodoItem(List<Item> todo, String s) {
        for (Item todoItem : todo) { // item = local variable (it changes every time the loop iterates)
            System.out.print(s);
            System.out.print(todoItem.getName());
            System.out.print("  ,  Priority: ");
            System.out.println(todoItem.getPriority());
        }
    }

    //EFFECTS: introduce todo list application
    private void introduceList(ToDoList toDoList) throws ParseException, IOException, MoreThanFive, TooManyThingsToDo {
        System.out.println("Welcome to ToDoList Application");
        System.out.println("If you want to start, enter '1'");

        String thing = scanner.nextLine();

        if (thing.equals("1")) {
            if (!toDoList.todo.isEmpty()) {
                doneSavedItem(toDoList);
            } else {
                newItem(toDoList);
            }
        } else {
            System.out.println("Sorry try again");
        }
    }



    private void doneSavedItem(ToDoList toDoList) throws MoreThanFive, TooManyThingsToDo, ParseException, IOException {
        System.out.println("Have you done any item in the given list?");
        System.out.println("Enter 'Yes' if you have.");
        System.out.println("Enter 'No' if you have not.");
        String thing = scanner.nextLine();
        if (thing.equals("Yes")) {
            checkDoneItemName(toDoList);
        } else if (thing.equals("No")) {
            newItem(toDoList);
        } else {
            System.out.println("Sorry try again");
            doneSavedItem(toDoList);
            //TODO decide later whether this has to be recursive
        }

    }

    private void checkDoneItemName(ToDoList toDoList) throws MoreThanFive,
            TooManyThingsToDo, ParseException, IOException {
        System.out.println("Which item have you done?");
        System.out.println("Enter the item's name");
        String thing = scanner.nextLine();
        List<Item> todoListCopy = new ArrayList<>(toDoList.getTodo());
        for (Item item: todoListCopy) {
            if (thing.equals(item.getName())) {
                helperForDoneItemName(item);
            }
        }
        newItem(toDoList);
    }

    private void helperForDoneItemName(Item item) {
        System.out.println("Enter the item's priority");
        String prior = scanner.nextLine();
        if (prior.equals(Integer.toString(item.getPriority()))) {
            item.done();
            System.out.println("You are done with this " + item.getName() + " item!");
        }
    }

    private void newItem(ToDoList toDoList) throws ParseException, IOException, TooManyThingsToDo, MoreThanFive {

        System.out.println("Enter 'add item', if you want to add item");
        System.out.println("Enter 'quit' if  you want to quit");


        String thing = scanner.nextLine();

        if (thing.equals("add item")) {
            addInList(toDoList);
        } else if (thing.equals("quit")) {
            toDoList.save();
            System.out.println("Thank you for using");
            return;
        } else {
            System.out.println("Sorry try again");
            newItem(toDoList);
        }
    }

//    System.out.println("Enter 'weather please' if you want a weather");
//    else if (thing.equals("weather please")) { //TODO get rid of it later
//        weatherOption();
//    }
//    else if (thing.equals("Have you done any item in the given list?")) {
//        makehelper();
//    }

    //EFFECTS: give a choice to user for the item and go to next user input. //helper for newItem()
    private void addInList(ToDoList toDoList) throws ParseException, MoreThanFive, TooManyThingsToDo, IOException {
        System.out.println("Do you want to make Urgent Item or Regular Item");
        System.out.println("If Urgent Item, enter 'urgent'");
        System.out.println("If Regular Item, enter 'regular'");
        chooseItem(toDoList);
        showAll();
        newItem(toDoList);
    }

    //EFFECTS: give two options (urgent or regular) to user.
    private void chooseItem(ToDoList toDoList) throws ParseException, TooManyThingsToDo, MoreThanFive {
        String thing = scanner.nextLine();

        if (thing.equals("urgent")) {
            tryCatchUrgent();
        } else if (thing.equals("regular")) {
            tryCatchRegular();
        } else {
            System.out.println("Sorry try again");
            chooseItem(toDoList);
        }
    }

    //EFFECTS: helper for chooseItem that try and catch when thing equals urgent
    private void tryCatchUrgent() throws ParseException, MoreThanFive, TooManyThingsToDo {
        try {
            ifUrgentList();
        } catch (TooManyThingsToDo tooManyThingsToDo) {
            System.out.println("Too many urgent item in the list");
            return;
        } catch (MoreThanFive moreThanFive) {
            System.out.println("Wrong priority! Choose the number in between 1 to 5");
            ifUrgentList();
        } finally {
            System.out.println("");
        }
    }


    //REQUIRES: user should put the priority in between 1 and 5.
    //EFFECTS: ... // helper for chooseItem()
    // I used to have MoreThanFive exception but now I dont need it.
    private void ifUrgentList() throws ParseException, TooManyThingsToDo, MoreThanFive {

        System.out.println("Enter the priority (1: highest, 5: lowest)");
        String prior = scanner.nextLine();
        try {
            int priority = Integer.parseInt(prior);
            if (priority > 5) { //TODO get rid of this part some day later
                throw new MoreThanFive();
            }
            System.out.println("Enter the item text: ");
            String name = scanner.nextLine(); // change input(Scanner) to String

            System.out.println("Enter the item's due date (DD/MM/YYYY)");
            String dueDate = scanner.nextLine();
            ifUrgentHelper(name, dueDate, priority);
        } catch (NumberFormatException e) {
            System.out.println("Wrong priority! Choose the number in between 1 to 5");
            ifUrgentList();
        }
    }

    private void ifUrgentHelper(String name,String dueDate, int priority) throws TooManyThingsToDo, ParseException {
        Date date = formatter.parse(dueDate);

        Item item = new UrgentItem(name, false, date, priority);
        toDoList.addItemInToDo(item);
    }

    //EFFECTS: helper for chooseItem that try and catch when thing equals regular
    private void tryCatchRegular() throws ParseException {
        try {
            ifRegularList();
        } catch (TooManyThingsToDo tooManyThingsToDo) {
            System.out.println("Too many regular item in the list");
            return;
        } finally {
            System.out.println("");
        }
    }

    //EFFECTS: ... // helper for chooseItem()
    private void ifRegularList() throws ParseException, TooManyThingsToDo {

        System.out.println("Enter the item text: ");
        String name = scanner.nextLine(); // change input(Scanner) to String

        System.out.println("Enter the item's due date (DD/MM/YYYY)");

        String dueDate = scanner.nextLine();
        Date date = formatter.parse(dueDate);

        Item item = new RegularItem(name, false, date);
        toDoList.addItemInToDo(item);
    }

//    //MODIFIES this
//    //EFFECT showed index of the item in the list
//    public void showTodoIndex() {
//        for (Item item : toDoList.getTodo()) {
//            int itemIndex = toDoList.getTodo().indexOf(item);
//            System.out.print(itemIndex + " : ");
//            System.out.println(item.getName());
//        }
//    }

    private void weatherOption() throws IOException {
        System.out.println("Do you want a weather to be shown?");
        System.out.println("type 'yes' or 'no'.");
        ReadWebPageEx read = new ReadWebPageEx();

        String thing = scanner.nextLine();
        if (thing.equals("yes")) {
            read.readWeb();
        }
    }

    //EFFECTS: show all the list so far and save and close. //helper for newItem()



    //TODO probably make something that can go back to previous stage
    //TODO check their status whether they are done or not. It currently does not change the status when save.
    //TODO when I enter without any input during the user input, it throws exception. Fix it.
    //TODO is it okay to remove MoreThanFive exception? because I realized that I dont need it anymore.
}
