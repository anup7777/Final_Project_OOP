package Currency;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;

public class ConverterPanell extends JPanel{
    //creating object of Currency class
    Currencyy currency = new Currencyy();

    //create variable
    private String[] startingCurrencies = currency.getCurrencies();
    private String[] startingSymbols = currency.getSymbols();
    private double[] startingFactors = currency.getFactors();
    private double [] newFactors = new double [8];
    private String [] newSymbols = new String [8];
    private String symbolForResult;
    private boolean usingCurrencyFromFile = false;
    private int conversionCount = 0;

    String [] testSymbols = {"Â¥", "â‚¬", "$", "A$", "C$", "â‚©", "à¸¿", "Ø¯.Ø¥", "kr", "R"};

    //creating label, textfield ,button checkbox and combo box for the frame
    private JTextField InputTextField;
    private JTextField conversionResult;
    private JLabel numberOfConversion;
    private JLabel lblFrom,to,from;
    private JCheckBox reverseCheckBox;
    private JButton resetButton,ShowButton;
    private JButton convertButton;
    private JComboBox<String> ComboBox;
    private Font fon1;

}
