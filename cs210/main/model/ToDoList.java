package model;

import exception.TooManyThingsToDo;
import observer.Subject;
import observer.ToDoListObserver;
import ui.Loadable;
import ui.Saveable;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static tool.StringUtility.splitOnSpace;

public class ToDoList extends Subject implements Loadable, Saveable, Observer {
    final int maxSIZE = 4;
    public List<Item> todo;   //fields
    protected List<Item> done;
    protected SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private Map<String, Item> itemArrayListMap = new HashMap<>();
    //private boolean tooMany = true;


    // Constructor of the todolist
    public ToDoList() {
        todo = new ArrayList<>();
        done = new ArrayList<>();
        //tooMany = new boolean[];
        addObserver(new ToDoListObserver()); //TODO changed it with D6 video
    }

    //EFFECT return the list of todo item
    public List<Item> getTodo() {
        return todo;
    }

    //EFFECT return the list of item that have been done
    public List<Item> getDone() {
        return done;
    }

    //TODO deliverable 9!
    //MODIFIES this
    //EFFECT add an item in the todo list
    public void addItemInToDo(Item item) throws TooManyThingsToDo {
        if (!(this.getTodo().contains(item))) {
            this.getTodo().add(item);
            item.addInToDoList(this);
            //item.addToDoList(this);
            //addObserver(item); //TODO deliverable 10
        }
        if (maxSize()) {
            throw new TooManyThingsToDo();
        }
    }

    public void addItemInUI(String name, String dueDate, String priority1) {
        Item item;
        String itemName = name;
        int priority = Integer.parseInt(priority1);
        Date date = this.parseDateFromString(dueDate);
        getItem(itemName, date, priority);
        
    }

    //TODO deliverable 9! for high cohesion         //detach
    //EFFECTS: remove an item in the todo list
    public void removeItemInToDo(Item item) {
        if (todo.contains(item)) {
            todo.remove(item);
            item.removeInToDoList();
        }
    }

//    //MODIFIES this
//    //EFFECT make the todo item to become a done item
//    public Item getItem(String name, Date dueDate) {
//        Item matcher = new RegularItem(name, false, dueDate);
//        int itemIdx = this.todo.indexOf(matcher);
//        return this.todo.get(itemIdx);
//    }

    //TODO test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //EFFECT get the item that matches the given info
    public Item getItem(String name, Date dueDate, int priority) {
        Item matcher;
        if (priority == 0) {
            matcher = new RegularItem(name, false, dueDate);
        } else {
            matcher = new UrgentItem(name, false, dueDate, priority);
        }
        return matcher;
    }

    // my gaesori
    //TODO test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    public void update(Observable itemv, Object obj) {
        Item item = (Item) itemv; //type casting
        removeItemInToDo(item);//cross(Item)
        getItem(item.name, item.dueDate, item.getPriority());
    }


    //MODIFIES this
    //EFFECT remove item from the todo-list and add that item in done list
    //EFFECT only remove the Item in todo. remove item in both ToDoList and Item class
    public void crossOff(int index) {
        if (!(index + 1 > todo.size())) {
            //setChanged(); //TODO why does it not working D6
            notifyObservers(); //TODO changed it by watching D6 video (change the input maybe)done?maybe
            Item itemToDo = todo.get(index);
            //to-do.get()returns an item at that index & assign it to local variable itemToDo
            System.out.println("This item is done: " + itemToDo.getName());
            removeItemInToDo(itemToDo);

            done.add(itemToDo); // add item to the done-list
            itemToDo.setStatus(true);

        } else {
            System.out.println("Index out of range");
        }
    }


    //EFFECT: return true if it reaches the max size
    public boolean maxSize() {
        return todo.size() >= maxSIZE;
    }



    @Override //TODO reduce the length of method.
    public void load() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./data/todoData.txt"));
        todo = new ArrayList<>();

        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);

            Item item = extractItem(partsOfLine);

            try {
                addItemInToDo(item);
            } catch (TooManyThingsToDo tooManyThingsToDo) {
                System.out.println("Too many Item in the list!");
            }

            // printing to terminal
        }
    }

    //I may not need to test this because it is the helper method for load()
    private Item extractItem(ArrayList<String> partsOfLine) {
        Item item;
        String itemName = partsOfLine.get(0);
        int priority = Integer.parseInt(partsOfLine.get(3));
        boolean status = Boolean.parseBoolean(partsOfLine.get(1));
        Date date = this.parseDateFromString(partsOfLine.get(2));

        if (priority == 0) {
            item = new RegularItem(itemName, status, date);
        } else {
            item = new UrgentItem(itemName, status, date, priority);
        }
        return item;
    }

    //helper method for extractItem(ArrayList<String> partsOfLine)
    private Date parseDateFromString(String date) {
        Date parsedDate = new Date();
        try {
            parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            // TODO
        }
        return parsedDate;
    }

    @Override
    public void save() throws IOException {
        List<String> lines = new ArrayList<>();
        PrintWriter writer = new PrintWriter("./data/todoData.txt", "UTF-8");

        for (int i = 0; i < todo.size(); i++) {
            String status = Boolean.toString(todo.get(i).getStatus());
            String dates = formatter.format(todo.get(i).getDueDate());
            String priority = Integer.toString(todo.get(i).getPriority());
            lines.add(todo.get(i).getName() + " " + status + " " + dates + " " + priority);

        }
        for (String thing : lines) {
            writer.println(thing);
        }
        // saving to file
        writer.close();
    }

    //TODO do the test
    public void addRecommendation(String name, Item toTake) {
        if (!itemArrayListMap.containsKey(name)) {
            itemArrayListMap.put(name, toTake);
        }
    }


    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all items
//    public void run() throws ParseException, IOException {
//        // add two items
//        load();
//        showAll();
//        introduceList();
//
//        // show indexes
//        showTodoIndex();
//
//        // show initial state
//        showAll();
//
//        // cross off first item (index 0)
//        crossOff(0);
//
//        // show the changed state
//        showAll();
//    }
//
//    //EFFECTS: introduce todo list application
//    public void introduceList() throws ParseException, IOException {
//        System.out.println("Welcome to ToDoList Application");
//        System.out.println("If you want to start, enter '1'");
//
//        Scanner scanner = new Scanner(System.in);
//        String thing = scanner.nextLine();
//
//        if (thing.equals("1")) {
//            addInList();
//        } else {
//            System.out.println("Sorry try again");
//        }
//
//    }
//
////    // EFFECT create an Item with user input as a name set initial deadline to null
////    public Item createItem() throws ParseException {
////
////        // get user input
////        Scanner scanner = new Scanner(System.in);
////        System.out.println("Enter the item text: ");
////        String name = scanner.nextLine(); // change input(Scanner) to String
////
////        System.out.println("Enter the item's due date (DD/MM/YYYY)");
////
////        String dueDate = scanner.nextLine();
////        // Javadoc Date find
////        Date date = formatter.parse(dueDate);
////        return new RegularItem(name, false, date);
////
////    }
//
//    //REQUIRES: user should put the priority in between 1 and 5.
//    //EFFECTS: ... // helper for chooseItem()
//    public void ifUrgentList() throws ParseException {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter the priority (1: highest, 5: lowest)");
//        String prior = scanner.nextLine();
//        int priority = Integer.parseInt(prior);
//
//        System.out.println("Enter the item text: ");
//        String name = scanner.nextLine(); // change input(Scanner) to String
//
//        System.out.println("Enter the item's due date (DD/MM/YYYY)");
//        String dueDate = scanner.nextLine();
//        Date date = formatter.parse(dueDate);
//
//        Item item = new UrgentItem(name, false, date, priority);
//        ToDoList.todo.add(item);
//    }
//
//
//    //EFFECTS: ... // helper for chooseItem()
//    public void ifRegularList() throws ParseException {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter the item text: ");
//        String name = scanner.nextLine(); // change input(Scanner) to String
//
//        System.out.println("Enter the item's due date (DD/MM/YYYY)");
//
//        String dueDate = scanner.nextLine();
//        Date date = formatter.parse(dueDate);
//
//        Item item = new RegularItem(name, false, date);
//        ToDoList.todo.add(item);
//    }
//
//    //EFFECTS: give two options (urgent or regular) to user.
//    public void chooseItem() throws ParseException, IOException {
//        Scanner scanner = new Scanner(System.in);
//        String thing = scanner.nextLine();
//
//        if (thing.equals("urgent")) {
//            ifUrgentList();
//            showAll();
//            newItem();
//        } else if (thing.equals("regular")) {
//            ifRegularList();
//            showAll();
//            newItem();
//        }
//    }
//
//    //EFFECTS: show all the list so far and save and close. //helper for newItem()
//
//
//    //EFFECTS: give a choice to user for the item and go to next user input. //helper for newItem()
//    public void addInList() throws ParseException, IOException {
//        System.out.println("Do you want to make Urgent Item or Regular Item");
//        System.out.println("If Urgent Item, enter 'urgent'");
//        System.out.println("If Regular Item, enter 'regular'");
//        chooseItem();
//        //addItem(createItem()); 삭제함
//        showAll();
//    }
//
//    public void newItem() throws ParseException, IOException {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter 'add item', if you want to add item");
//        System.out.println("Enter 'quit' if  you want to quit");
//
//        String thing = scanner.nextLine();
//
//        if (thing.equals("add item")) {
//            addInList();
//        } else if (thing.equals("quit")) {
//            save();
//            System.out.println("Thank you for using");
//            return;
//        } else {
//            System.out.println("Sorry try again");
//        }
//        newItem();
//    }
//
//
//
//    //MODIFIES this
//    //EFFECT showed index of the item in the list
//    public void showTodoIndex() {
//        for (Item item : todo) {
//            int itemIndex = todo.indexOf(item);
//            System.out.print(itemIndex + " : ");
//            System.out.println(item.getName());
//        }
//    }
//
//    //EFFECT print all the items in the list
//    public void showAll() {
//        for (Item todoItem : todo) { // item = local variable (it changes every time the loop iterates)
//            System.out.print("TODO :");
//            System.out.print(todoItem.getName());
//            System.out.print(" priority: ");
//            System.out.println(todoItem.getPriority());
//        }
//
//        for (Item doneItem : done) {
//            System.out.print("DONE :");
//            System.out.println(doneItem.getName());
//            System.out.print(" priority: ");
//            System.out.println(doneItem.getPriority());
//        }
//    }



}

