package model;

import java.util.Date;

public class UrgentItem extends Item {

    protected int priority;

    public UrgentItem(String name, boolean status, Date dueDate, int priority) {
        super(name, status, dueDate);
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

//    public void setPriority(int priority) {
//        this.priority = priority;
//    }
}
