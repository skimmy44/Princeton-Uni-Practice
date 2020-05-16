package observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<ToDoListObserver> observers = new ArrayList<>();

    public void addObserver(ToDoListObserver toDoListObserver) {
        if ((!observers.contains(toDoListObserver))) {
            observers.add(toDoListObserver);
        }
    }

    public void notifyObservers() {
        for (ToDoListObserver observer : observers) {
            //observer.update(); //TODO update method input 넣어야함 근데 모르겠음
        }
    }
}
