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

public class ConverterPanell extends JPanel {
    //creating object of Currency class
    Currencyy currency = new Currencyy();

    //create variable
    private String[] startingCurrencies = currency.getCurrencies();
    private String[] startingSymbols = currency.getSymbols();
    private double[] startingFactors = currency.getFactors();
    private double[] newFactors = new double[8];
    private String[] newSymbols = new String[8];
    private String symbolForResult;
    private boolean usingCurrencyFromFile = false;
    private int conversionCount = 0;

    String[] testSymbols = {"Â¥", "â‚¬", "$", "A$", "C$", "â‚©", "à¸¿", "Ø¯.Ø¥", "kr", "R"};

    //creating label, textfield ,button checkbox and combo box for the frame
    private JTextField InputTextField;
    private JTextField conversionResult;
    private JLabel numberOfConversion;
    private JLabel lblFrom, to, from;
    private JCheckBox reverseCheckBox;
    private JButton resetButton, ShowButton;
    private JButton convertButton;
    private JComboBox<String> ComboBox;
    private Font fon1;

    // Constructor to setup the GUI components
    ConverterPanell() {
        //set the layout and the background color
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);

        numberOfConversion = new JLabel("Conversion count: 0");  //label to display the number of conversion

        fon1 = new Font("arial", Font.BOLD, 22);

        //set the label for To in combo Box
        lblFrom = new JLabel("To:");
        lblFrom.setBounds(70, 20, 350, 50);
        lblFrom.setForeground(Color.BLUE);
        lblFrom.setFont(fon1);
        add(lblFrom);

        //adding the combo box for currency
        ComboBox = new JComboBox<String>(startingCurrencies);
        ComboBox.setBounds(70, 60, 250, 30);
        add(ComboBox);


        //adding the label for amount to converted
        from = new JLabel("Enter Amount to Convert:");
        from.setBounds(30, 110, 300, 30);
        add(from);

        //Creating and adding text field for input value
        InputTextField = new JTextField(15);
        InputTextField.setBounds(30, 140, 150, 30);
        add(InputTextField);
        InputTextField.setToolTipText("Enter the value to be converted");

        //Creating and adding button for conversion
        convertButton = new JButton("Convert");
        convertButton.setBounds(105, 250, 100, 30);
        convertButton.setBackground(new Color(10, 2, 2));
        convertButton.setForeground(Color.WHITE);
        add(convertButton);
        convertButton.setToolTipText("press here to convert");

        //Adding action listener to the convert button
        ActionListener conversionListener = new ConvertButtonListener();
        convertButton.addActionListener(conversionListener);

        //Creating show button
        ShowButton = new JButton("Show");
        ShowButton.setBounds(155, 350, 100, 30);
        ShowButton.setBackground(Color.BLACK);
        ShowButton.setForeground(Color.WHITE);
        add(ShowButton);
        ShowButton.setToolTipText("press here to show");

        // Adding action listener to show
        ActionListener showlistener = new ShowButtonListener();
        ShowButton.addActionListener(showlistener);


        //creating and adding the
        to = new JLabel("Total Amount Converted:");
        to.setBounds(230, 110, 300, 30);
        add(to);

        //creating and adding textfield for conversion result
        conversionResult = new JTextField(15);
        conversionResult.setBounds(230, 140, 150, 30);
        conversionResult.setEditable(false);
        conversionResult.setForeground(Color.RED);
        add(conversionResult);

        //creating and adding reset button
        resetButton = new JButton("clear");
        resetButton.setBounds(215, 250, 100, 30);
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        add(resetButton);

        //adding action listener to the reset button
        resetButton.addActionListener(e -> {
            InputTextField.setText("");
            conversionResult.setText(" ");
            conversionCount = 0;
            numberOfConversion.setText("Conversion count: " + conversionCount);
        });
        resetButton.setToolTipText("press here to clear");


        //creating Checkbox for reverse conversion
        reverseCheckBox = new JCheckBox("Reverse Conversion");
        reverseCheckBox.setBounds(110, 200, 200, 30);
        add(reverseCheckBox);
        reverseCheckBox.setToolTipText("press here to reverse the conversion");

        //adding action listener to the reverse checkbox
        reverseCheckBox.addActionListener(conversionListener);

        //creating and adding label for number of conversion
        numberOfConversion = new JLabel(String.valueOf("Conversion count :" + conversionCount));
        numberOfConversion.setBounds(145, 300, 200, 30);
        add(numberOfConversion);
        setPreferredSize(new Dimension(420, 420));

    }

    @SuppressWarnings("deprecation")
    JMenuBar setUpMenu() {
        //add menu bar to the frame
        JMenuBar menuBar = new JMenuBar();

        //create file menu
        JMenu file = new JMenu("File");
        menuBar.add(file); //adding file menu

        //creating and adding menu item to the file menu
        JMenuItem New = new JMenuItem("New");
        file.add(New);
        New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));//add icon
        New.setIcon(new ImageIcon(("about.png")));
        New.setToolTipText("Press here to create new file"); //set tool tip

        //creating and adding load menu item to the load menu
        JMenuItem load = new JMenuItem("Load");
        file.add(load);
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        load.setIcon(new ImageIcon(("download.png")));//add icon
        load.setToolTipText("Press here to load new file"); //set tool tip

        //creating and adding exit menu item to the file menu
        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);

        //creating and adding action listener to the exit menu
        exit.addActionListener(e -> System.exit(0));
        exit.setIcon(new ImageIcon(("exit.png")));   //set icon
        exit.setToolTipText("press here to exit program"); //set tool tip

        //creating and adding help menu to the menu bar
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        help.setToolTipText("press here to get help");

        return menuBar;
    }
    //method to load and calculate file show error message
    void loadCurrencyFile(File userSelectedFile) {

        try {

            BufferedReader inputFromFile =
                    new BufferedReader(new InputStreamReader(new FileInputStream(userSelectedFile), StandardCharsets.UTF_8));

            //reading file line by line
            String line = inputFromFile.readLine();

            //creating and initializing the integer
            int counterForFactors = 0;
            int counterForSymbols = 0;

            //removing the pre-existing items from the combo box after loading file
            ComboBox.removeAllItems();

            while ( line != null ) {

                //check the number of parts in a line
                String [] parts = line.split(",");

                //checking to make sure there are only three parts in a line
                if (parts.length < 3) {
                    JOptionPane.showMessageDialog(null, "Invalid number of data values!\n" +  //show error message if there are less than three item
                                    "symbol) in a line of the file!",
                            "ERROR!", JOptionPane.ERROR_MESSAGE);
                    ComboBox.addItem("Invalid data ");
                    newFactors[counterForFactors] = 0.0;
                    newSymbols[counterForSymbols] = "Invalid";
                    counterForFactors++;
                    counterForSymbols++;
                }else {

                    for(int i = 0; i < parts.length; i++){
                        if (i == 0) {

                            ComboBox.addItem(parts[i].trim());
                        }else if (i == 1){

                            try{
                                newFactors[counterForFactors] = Double.parseDouble(parts[i].trim());
                                counterForFactors++;
                            }catch (Exception e){
                                JOptionPane.showMessageDialog(null,
                                        "There was invalid value for the conversion factor "
                                                + e.getMessage() + ".", "ERROR!", JOptionPane.ERROR_MESSAGE);

                                ComboBox.removeItemAt(ComboBox.getItemCount() - 1);
                                ComboBox.addItem("Invalid data");
                                newFactors[counterForFactors] = 0.0;
                                newSymbols[counterForSymbols] = "Invalid";
                                counterForFactors++;
                                counterForSymbols++;

                                break;
                            }
                        }else {

                            String fileSymbol = parts[i].trim();


                            boolean symbolDoesExist = false;
                            for(String symbol : testSymbols){
                                if (fileSymbol.equals(symbol)){
                                    newSymbols[counterForSymbols] = fileSymbol;
                                    counterForSymbols++;
                                    symbolDoesExist = true;
                                }
                            }
                            if (!symbolDoesExist){
                                JOptionPane.showMessageDialog(null, "Invalid currency " +
                                                "symbol from the file has been found! \""
                                                + fileSymbol + "\".",
                                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                                ComboBox.removeItemAt(ComboBox.getItemCount() - 1);
                                ComboBox.addItem("Invalid data");

                                newFactors[counterForFactors - 1] = 0.0;
                                newSymbols[counterForSymbols] = "Invalid";
                                counterForFactors++;
                                counterForSymbols++;
                            }
                        }
                    }
                }
                line = inputFromFile.readLine();
            }

            inputFromFile.close();

        } catch (Exception e) {

            String errorMessage = e.getMessage();

            JOptionPane.showMessageDialog(null, errorMessage,
                    "ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

}
