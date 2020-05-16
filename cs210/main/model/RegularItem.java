package model;

import java.util.Date;

public class RegularItem extends Item {


    //Constructor
    public RegularItem(String name, boolean status, Date dueDate) {
        super(name, status, dueDate);
    }

    @Override
    public int getPriority() {
        return 0;
    }

}
