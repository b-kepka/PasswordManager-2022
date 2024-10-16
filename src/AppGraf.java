import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AppGraf extends JFrame {

    JPanel mainPanel;
    Menu myMenu;
    SQL sqlite;
    JLabel szukajLabel, wpisLoginLabel, wpisHasloLabel, wpisAdresLabel, testResult;
    JTextField szukajTextField, wpisLoginTextField, wpisAdresTextField;
    JPasswordField wpisPasswordField;
    JButton wpisButton, szukajButton;

    String[] columnNames = {"ID","Login","Hasło","Adres"};
    DefaultTableModel model;
    JTable wynikTable;
    JScrollPane scrollPane;
    int actionType = 0;
    ActionListener menuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == myMenu.help) {
                    JOptionPane.showMessageDialog(null, "Aplikacja do zarządzania hasłami\nBartłomiej Kępka\nKlasa 4pP");
                }
                if (e.getSource() == myMenu.szukajLogin) {
                    szukajLabel.setText("Podaj login: ");
                    setVisibleSzukaj(true);
                    setVisibleNowyWpis(false, "");
                    testResult.setVisible(false);
                    szukajTextField.setText("");
                    actionType = 1;
                }
                if (e.getSource() == myMenu.szukajAdres) {
                    szukajLabel.setText("Podaj adres: ");
                    setVisibleSzukaj(true);
                    setVisibleNowyWpis(false, "");
                    testResult.setVisible(false);
                    szukajTextField.setText("");
                    actionType = 2;
                }
                if (e.getSource() == myMenu.test) {
                    setVisibleSzukaj(false);
                    testResult.setBounds(0,20,250,20);
                    setVisibleNowyWpis(false, "");
                    try {
                        sqlite = new SQL();
                        testResult.setText("Udane połączenie z bazą!");
                        testResult.setForeground(Color.GREEN);
                    } catch (SQLException ex) {
                        System.out.println(ex);
                        testResult.setText("Nieudane połączenie z bazą!");
                        testResult.setForeground(Color.RED);
                    }
                    finally {
                        testResult.setVisible(true);
                    }
                }
                if (e.getSource() == myMenu.nowyWpis) {
                    setVisibleNowyWpis(true, "Dodaj");
                    setVisibleSzukaj(false);
                    testResult.setVisible(false);
                    wpisLoginTextField.setText("");
                    wpisPasswordField.setText("");
                    wpisAdresTextField.setText("");
                    actionType = 1;
                }
                if (e.getSource() == myMenu.usunWpis) {
                    setVisibleNowyWpis(true, "Usuń");
                    wpisAdresLabel.setVisible(false);
                    wpisAdresTextField.setVisible(false);
                    setVisibleSzukaj(false);
                    testResult.setVisible(false);
                    wpisLoginTextField.setText("");
                    wpisPasswordField.setText("");
                    actionType = 2;
                }
                if (e.getSource() == myMenu.zamknij) {
                    System.exit(0);
                }
        }
    };
    ActionListener wpisListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == wpisButton) {
                if (!wpisLoginTextField.getText().isEmpty() && !String.valueOf(wpisPasswordField.getPassword()).isEmpty()) {
                    try {
                        sqlite = new SQL();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(actionType == 1) {
                        sqlite.addPassword(wpisLoginTextField.getText(),String.valueOf(wpisPasswordField.getPassword()),wpisAdresTextField.getText());
                    } else {
                        sqlite.deleteData(wpisLoginTextField.getText(),String.valueOf(wpisPasswordField.getPassword()));
                    }
                } else
                {
                    testResult.setVisible(true);
                    testResult.setForeground(Color.RED);
                    testResult.setBounds(0,220,250,20);
                    testResult.setText("Podaj login i hasło!");
                }
            }
        }
    };
    ActionListener searchListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == szukajButton) {
                if (!szukajTextField.getText().isEmpty()) {
                    JFrame daneFrame = new JFrame();
                    JPanel danePanel = new JPanel(null);
                    wynikTable = new JTable();
                    scrollPane = new JScrollPane(wynikTable);
                    daneFrame.setTitle("Menadżer Haseł");
                    daneFrame.setSize(400,350);
                    daneFrame.setResizable(false);
                    daneFrame.setDefaultCloseOperation(1);
                    daneFrame.setLocationRelativeTo(mainPanel);
                    daneFrame.setVisible(true);
                    daneFrame.setContentPane(danePanel);
                    scrollPane.setVisible(true);
                    wynikTable.setEnabled(false);
                    scrollPane.setBounds(0,0,402,253);
                    scrollPane.setBorder(BorderFactory.createEmptyBorder());
                    danePanel.add(scrollPane);
                    model = new DefaultTableModel(columnNames, 0);
                    try {
                        sqlite = new SQL();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (actionType == 1) {
                        sqlite.getPasswordsByLogin(szukajTextField.getText());
                    } else {
                        sqlite.getPasswordsByAdres(szukajTextField.getText());
                    }
                    for (String[] row : sqlite.values) {
                        model.addRow(row);
                    }
                    wynikTable.setModel(model);
                }
            }
        }
    };
    public AppGraf() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setTitle("Menadżer Haseł");
        setSize(250,350);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }
    private void initComponents() {
        mainPanel = new JPanel(null);
        mainPanel.setBounds(0,0,250,350);
        setContentPane(mainPanel);
        myMenu = new Menu();
        setJMenuBar(myMenu);
        szukajLabel = new JLabel("Podaj login: ");
        szukajButton = new JButton("Szukaj");
        szukajTextField = new JTextField();
        wpisLoginLabel = new JLabel("Podaj login:");
        wpisHasloLabel = new JLabel("Podaj hasło:");
        wpisAdresLabel = new JLabel("Podaj adres:");
        wpisLoginTextField = new JTextField();
        wpisPasswordField = new JPasswordField();
        wpisAdresTextField = new JTextField();
        wpisButton = new JButton("Dodaj");
        testResult = new JLabel("", SwingConstants.CENTER);
        testResult.setVisible(false);
        myMenu.help.addActionListener(menuListener);
        myMenu.szukajLogin.addActionListener(menuListener);
        myMenu.szukajAdres.addActionListener(menuListener);
        myMenu.test.addActionListener(menuListener);
        myMenu.nowyWpis.addActionListener(menuListener);
        myMenu.usunWpis.addActionListener(menuListener);
        myMenu.zamknij.addActionListener(menuListener);
        wpisButton.addActionListener(wpisListener);
        szukajButton.addActionListener(searchListener);
        szukajLabel.setBounds(25,10,90,20);
        szukajTextField.setBounds(25,30,200,20);
        szukajButton.setBounds(145, 70, 80, 20);
        setVisibleSzukaj(false);
        wpisLoginLabel.setBounds(25,10,90,20);
        wpisHasloLabel.setBounds(25,55,90,20);
        wpisAdresLabel.setBounds(25,100,90,20);
        wpisLoginTextField.setBounds(25,30,200,20);
        wpisPasswordField.setBounds(25,75,200,20);
        wpisAdresTextField.setBounds(25,120,200,20);
        wpisButton.setBounds(145,160,80,20);
        setVisibleNowyWpis(false, "");
        testResult.setBounds(0,20,250,20);
        mainPanel.add(szukajLabel);
        mainPanel.add(szukajTextField);
        mainPanel.add(szukajButton);
        mainPanel.add(wpisLoginLabel);
        mainPanel.add(wpisHasloLabel);
        mainPanel.add(wpisAdresLabel);
        mainPanel.add(wpisLoginTextField);
        mainPanel.add(wpisPasswordField);
        mainPanel.add(wpisAdresTextField);
        mainPanel.add(wpisButton);
        mainPanel.add(testResult);
    }
    private void setVisibleSzukaj(boolean visibility) {
        szukajLabel.setVisible(visibility);
        szukajTextField.setVisible(visibility);
        szukajButton.setVisible(visibility);
    }
    private void setVisibleNowyWpis(boolean visibility, String btnText) {
        wpisLoginLabel.setVisible(visibility);
        wpisLoginTextField.setVisible(visibility);
        wpisHasloLabel.setVisible(visibility);
        wpisPasswordField.setVisible(visibility);
        wpisAdresLabel.setVisible(visibility);
        wpisAdresTextField.setVisible(visibility);
        wpisButton.setVisible(visibility);
        wpisButton.setText(btnText);
    }
}
