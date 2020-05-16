package model;



import exception.TooManyThingsToDo;
import observer.ToDoListObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public abstract class Item extends Observable {
    public String name;
    public boolean status; // true if done, false otherwise
    public Date dueDate;
    private ToDoList toDoList;

    //Constructor
    public Item(String name, boolean status, Date dueDate) {
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
        toDoList = new ToDoList();
    }

    //private SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
    //Date date = formatter.parse(dueDate);

    //SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    //	String date = sdf.format(new Date());
    //	System.out.println(date); //15/10/2013

    //getters
    public String getName() {
        return name;
    }

    //EFFECT: true if done, false otherwise
    public boolean getStatus() {
        return status;
    }

    //EFFECT: get duedate
    public Date getDueDate() {
        return dueDate;
    }

    //setters
    //EFFECT returns the name of item
    public void setName(String name) {
        this.name = name;
    }

    //EFFECT return the status of item
    public void setStatus(boolean status) {
        this.status = status;
    }

    //EFFECT return the due date of item
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    //TODO deliverable 9 that no violating LSP because it is abstract.
    public abstract int getPriority();
    //if it was not abstract, may violate LSP?

    public ToDoList getToDoList() {
        return toDoList;
    }

    //TODO this is setChanged method
    public void setToDoList(ToDoList toDoList) { //attach method
        this.toDoList = toDoList;
    }

    //MODIFIES: this, ToDoList
    //EFFECTS: add item in toDoList if toDoList doesn't contain that toDoList
    public void addInToDoList(ToDoList toDoList) throws TooManyThingsToDo {
        if (!(this.toDoList == toDoList)) {
            this.toDoList = toDoList;
            toDoList.addItemInToDo(this);
            // toDoList.getTodo().add(this);
        }
    }

    //TODO deliverable 9! instead of toDoList.getTodo().remove(this);, toDoList.removeItemInToDo(this);
    //TODO this change low level to high level.
    //TODO this is an example of increase cohesion
    //EFFECTS: remove item in todo inside ToDoList
    public void removeInToDoList() {  //detach method
        if (!(toDoList.getTodo().size() == 0)) {
            toDoList.removeItemInToDo(this);
            //toDoList.getTodo().remove(this);
        }
    }

    @Override
    public int hashCode() {
        String match = name + dueDate;
        return match.hashCode();
    }

    //any two objects with the same name and dueDate will be considered to be equivalent,
    // even though they are distinct objects.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (!name.equals(item.name)) {
            return false;
        }
        return dueDate.equals(item.dueDate);
    }

    //TODO test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Item becomes Observable
    //      Item's state change needs to be broadcasted.
    //this is notify() method
    public void done() {
        this.status = true;
        toDoList.update(this, null);
        // notify()
    }


//    @Override //TODO deliverable 10 고쳐야함
//    public void update() {
//        System.out.println("Gotta fix this method later");
//    }






//    @Override
//    public void load() {
//        System.out.println("Load!");
//    }
//
//    @Override
//    public void save() {
//        System.out.println("Save!");
//    }
    //TODO 1. ask whether I have to make new class for
    // 2. List<Item> for todo, done
    // 3. How to coupling down and cohesion up
    // 4. Fix save and load test
}

