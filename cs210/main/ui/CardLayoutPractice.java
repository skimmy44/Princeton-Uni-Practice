package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutPractice {
    JFrame frame = new JFrame("ToDoList");

    JPanel panel = new JPanel();
    JPanel todoListPanel = new JPanel();
    JPanel itemChoicePanel = new JPanel();
    JPanel urgentItemPanel = new JPanel();

    //JList list = new JList();



    //Should have both SpringLayout and CardLayout
    JPanel regularItemPanel = new JPanel();

    //button for todoListPanel
    JButton todoListQuitButton = new JButton("Quit");
    JButton todoListDoneItemButton = new JButton("Done Item");
    JButton todoListCreateItemButton = new JButton("Create New Todo Item");

    //button for itemChoicePanel
    JButton itemChoiceUrgentItemButton = new JButton("Urgent Item");
    JButton itemChoiceRegularItemButton = new JButton("Regular Item");
    JButton itemChoicePreviousButton = new JButton("Previous");

    //button for urgentItemPanel
    JButton urgentItemCreateButton = new JButton("Create");
    JButton urgentItemPreviousButton = new JButton("Previous");

    //button for regularItemPanel
    JButton regularItemCreateButton = new JButton("Create");
    JButton regularItemPreviousButton = new JButton("Previous");

    CardLayout c1 = new CardLayout();

    public CardLayoutPractice() {
        //이건 그냥 CardLayout틀을 만드는거
        panel.setLayout(c1);

//        list = new JList();
//        list.setVisibleRowCount(4);
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        list.add(new JScrollPane(list));

        //adding button into the todoListPanel
        todoListPanel.add(todoListQuitButton);
        todoListPanel.add(todoListDoneItemButton);
        todoListPanel.add(todoListCreateItemButton);

        //adding button into the itemChoicePanel
        itemChoicePanel.add(itemChoiceUrgentItemButton);
        itemChoicePanel.add(itemChoiceRegularItemButton);
        itemChoicePanel.add(itemChoicePreviousButton);

        //adding button into the urgentItemPanel
        urgentItemPanel.add(urgentItemCreateButton);
        urgentItemPanel.add(urgentItemPreviousButton);

        //adding button into the regularItemPanel
        regularItemPanel.add(regularItemCreateButton);
        regularItemPanel.add(regularItemPreviousButton);

        //Setting the background color for panel
        itemChoicePanel.setBackground(Color.BLACK);
        regularItemPanel.setBackground(Color.BLUE);
        urgentItemPanel.setBackground(Color.RED);

        //panel 안에 다른 panels들을 넣음
        panel.add(todoListPanel, "todoList");
        panel.add(itemChoicePanel, "itemChoice");
        panel.add(urgentItemPanel, "urgentItem"); //second
        panel.add(regularItemPanel, "regularItem"); //third

        c1.show(panel, "todoList");

        //todolistPanel안에 있는 todoListCreateItemButton(Create New Todo Item)를 클릭하면 itemChoicePanel로 감
        todoListCreateItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "itemChoice"); //second
            }
        });

        //itemChoicePanel안에 있는 itemChoiceUrgentItemButton(Urgent Item) 을 클릭하면 urgentItemPanel로 감
        itemChoiceUrgentItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "urgentItem"); //second
            }
        });

        //itemChoicePanel안에 있는 itemChoiceRegularItemButton(Regular Item) 을 클릭하면 regularItemPanel로 감
        itemChoiceRegularItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "regularItem"); //third
            }
        });

        //itemChoicePanel안에서 itemChoicePreviousButton(Previous)를 클릭하면 맨맨처음화면으로 가게 만들기 //TODO
        itemChoicePreviousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "todoList");
            }
        });

        //urgentItemPanel, click "Previous" will goes to itemChoicePanel
        urgentItemPreviousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "itemChoice");
            }
        });

        //urgentItemPanel, click "Create" will goes to todoListPanel and shows the created urgent item
        urgentItemCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "todoList");
            }
        });

        //regularItemPanel, click "Previous" will goes to itemChoicePanel
        regularItemPreviousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "itemChoice");
            }
        });

        //regularItemPanel, click "Create" will goes to todoListPanel and shows the created regular item
        regularItemCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(panel, "todoList");
            }
        });


        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CardLayoutPractice();
            }
        });
    }
}
