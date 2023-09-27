package com.example.demo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JPanel {
    Box box = new Box(BoxLayout.PAGE_AXIS);

    String levels[] = {"1","2","3"};
    JComboBox comboBox = new JComboBox(levels);
    JLabel arkanoid = new JLabel("ARKANOID");
    JLabel label = new JLabel("Wybierz poziom: ");
    JButton button = new JButton("START");
    JTextField nameField = new JTextField();
    JLabel nameLabel = new JLabel("Podaj imie:");
    public String name;
    JButton history = new JButton("Historia gier");
    GameHistory gameHistory;

    @Override
    public String getName() {
        return name;
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    private int selectedLevel = 0;
    public Menu(){

        nameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nameField.setText("");
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameField.getText();
                if(name.isEmpty() || name.equals("Podaj imie"))
                    nameField.setText("Podaj imie");
                else {
                    selectedLevel = comboBox.getSelectedIndex() + 1;
                    if (gameHistory != null)
                        gameHistory.setVisible(false);
                }
            }
        });

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gameHistory != null)
                    Menu.this.remove(gameHistory);
                gameHistory = new GameHistory();
                SwingUtilities.updateComponentTreeUI(Menu.this);
                Menu.this.add(gameHistory);
            }
        });

        arkanoid.setFont(new Font("Courier", Font.BOLD,40));
        box.add(arkanoid);
        box.add(Box.createVerticalStrut(15));
        box.add(label);
        box.add(Box.createVerticalStrut(10));
        box.add(comboBox);
        box.add(Box.createVerticalStrut(15));
        box.add(nameLabel);
        box.add(Box.createVerticalStrut(10));
        box.add(nameField);
        button.setAlignmentY(LEFT_ALIGNMENT);
        box.add(Box.createVerticalStrut(15));
        box.add(button);
        box.add(Box.createVerticalStrut(10));
        box.add(history);
        this.add(box);
        setFocusable(true);
        setPreferredSize(new Dimension(600, 600));
    }
}
