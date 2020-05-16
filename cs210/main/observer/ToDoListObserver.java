package observer;

import java.util.Observable;
import java.util.Observer;

public class ToDoListObserver implements Observer { //TODO 이거를 ConcreteObserver라고 하네
    @Override
    public void update(Observable o, Object arg) {  //TODO deliverable 10 input 넣어야함 근데 모르겠음
        System.out.println("I dont know what to put here for now");

    }
}
