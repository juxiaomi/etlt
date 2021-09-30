package org.etlt.gui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IRRPanel extends JPanel{

    public IRRPanel() {
        init();
    }

    public void init() {
        setLayout(new BorderLayout());

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(4, 2, 5, 5));
        JLabel totalLabel = new JLabel("总额：");
//        totalLabel.setBounds(new Rectangle());
        final JTextField totalField = new JTextField();

//        totalField.setDocument(new DoubleDocument());

        displayPanel.add(totalLabel);
        displayPanel.add(totalField);


        JLabel everyLabel = new JLabel("每期还款：");
        final JTextField everyField = new JTextField();

        displayPanel.add(everyLabel);
        displayPanel.add(everyField);

        JLabel periodLabel = new JLabel("期数(月)：");
        final JTextField periodField = new JTextField();
        displayPanel.add(periodLabel);
        displayPanel.add(periodField);

        final JLabel yearlyLabel = new JLabel("年化利率：");
        final JTextField yearlyField = new JTextField();
        yearlyField.setEditable(false);
        displayPanel.add(yearlyLabel);
        displayPanel.add(yearlyField);

        add(displayPanel, BorderLayout.CENTER);

        JPanel operationPanel = new JPanel();
        JButton calculatorButton = new JButton("计算");
        calculatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        operationPanel.add(calculatorButton);

        add(operationPanel, BorderLayout.SOUTH);
    }


}
