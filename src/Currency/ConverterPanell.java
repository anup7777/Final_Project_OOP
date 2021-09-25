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
import javax.swing.ImageIcon;

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
    private JLabel lblFrom, to, from,lbl_img,lb;
    private ImageIcon image_currency;
    private JCheckBox reverseCheckBox;
    private JButton resetButton, ShowButton;
    private JButton convertButton;
    private JComboBox<String> ComboBox;
    private Font fon1;

    // Constructor to setup the GUI components
    ConverterPanell() {
        //set the layout and the background color
        setLayout(null);
        setBackground(Color.green);

        numberOfConversion = new JLabel("Conversion count: 0");  //label to display the number of conversion

        fon1 = new Font("arial", Font.BOLD, 22);

        /*JLabel lb = new JLabel("");
        lb.setBackground(Color.black);
        lb.setOpaque(true);*/

        /*image_currency = new ImageIcon(getClass().getResource("curren.png"));
        lbl_img = new JLabel(image_currency);
        lbl_img.setBounds(0, 0, 500, 600);
        add(lbl_img);*/

        //set the label for To in combo Box
        lblFrom = new JLabel("To:");
        lblFrom.setBounds(70, 15, 350, 50);
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
        convertButton.setBackground(Color.BLACK);
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

        //creating and adding edit menu item to the help menu
        JMenuItem edit = new JMenuItem("Edit");
        help.add(edit);
        edit.setToolTipText("press here to edit program");

        //creating adding about menu item to the about menu
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        help.setToolTipText("click here to find about the program");  //adding tooltip
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        about.setIcon(new ImageIcon(("about.png"))); //add icon

        //adding action listener to the about menu item to show the dialogue box
        about.addActionListener(e->
        {JOptionPane.showMessageDialog(this, "Currency conversion application for common users with several units.\n@anup 2021\n@TBC Kathmandu ");});

        //adding action listener to the load menu
        ActionListener loadListener = new LoadMenuItemListener();
        load.addActionListener(loadListener);

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
    //calculating the units
    private double getConversionFactor(int indexPosition, boolean isChecked){

        //creating and initializing the factor of currency
        double factor = 0;

        // Setup the correct factor values depending on required conversion
        switch(indexPosition){
            case 0:
                factor = startingFactors[0];
                break;
            case 1:
                factor = startingFactors[1];
                break;
            case 2:
                factor = startingFactors[2];
                break;
            case 3:
                factor = startingFactors[3];
                break;
            case 4:
                factor = startingFactors[4];
                break;
            case 5:
                factor = startingFactors[5];
                break;
            case 6:
                factor = startingFactors[6];
                break;
            case 7:
                factor = startingFactors[7];
                break;
        }

        if (isChecked){
            symbolForResult = "Â£";
        }else {
            symbolForResult = startingSymbols[indexPosition];
        }

        return factor;
    }

    private double getNewFactors(int indexPosition, boolean isChecked){

        double factor = 0;

        if (isChecked){
            symbolForResult = "Â£";
        }else {
            symbolForResult = newSymbols[indexPosition];
        }

        switch(indexPosition){
            case 0:
                factor = newFactors[0];
                break;
            case 1:
                factor = newFactors[1];
                break;
            case 2:
                factor = newFactors[2];
                break;
            case 3:
                factor = newFactors[3];
                break;
            case 4:
                factor = newFactors[4];
                break;
            case 5:
                factor = newFactors[5];
                break;
            case 6:
                factor = newFactors[6];
                break;
            case 7:
                factor = newFactors[7];
                break;
        }
        return factor;
    }

    private class ShowButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==ShowButton){
                JFrame jr = new JFrame("Data Displayed");


                DefaultTableModel model= new DefaultTableModel();
                JTable jt= new JTable(model);
                Font fon=new Font("Dialog", Font.BOLD, 22);
                Font fon1=new Font("Serif", Font.BOLD, 18);
                jt.setFont(fon1);
                jt.setRowHeight(40);
                jt.setBackground(new Color(211,244,252));
                model.addColumn("Currency");
                model.addColumn("Entered Amount");
                model.addColumn("Converted Amount");
                JTableHeader header = jt.getTableHeader();
                header.setFont(fon);
                header.setBackground(Color.black);
                header.setForeground(Color.white);
                jt.getTableHeader().setPreferredSize(new Dimension(100,50));

                try {
                    DbOperation db=new DbOperation();
                    String query="select * from converter";
                    ResultSet rs=db.select(query);
                    // DefaultTableModel model=(DefaultTableModel)jt.getModel();
                    //model.setRowCount(0);
//        	model.fireTableDataChanged();
//        	jt.repaint();
                    int i=0;

                    while(rs.next()){
                        System.out.println(rs.getString(1)+" "+rs.getString(2));
                        model.addRow(new Object[]{rs.getString(1), rs.getString(2),rs.getString(3)});

//            	((AbstractTableModel) jt.getModel()).fireTableCellUpdated(1, 0);

                    }
                } catch (Exception er) {
                    System.out.println(er.getMessage());
                }
                JScrollPane pg = new JScrollPane(jt);
                pg.setBounds(0,0,1220,480);
                jr.add(pg);

                jr.setSize(1235,520);
                jr.setLayout(null);
                jr.setVisible(true);

            }
        }
    }

    //event handler for convert button and calculating the currency
    private class ConvertButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {


            String text = InputTextField.getText().trim();


            if (!text.isEmpty() && !reverseCheckBox.isSelected()) {

                double value = 0;

                try{
                    value = Double.parseDouble(text.trim());
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,
                            "You have entered an invalid number.",
                            "WARNING",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double factor = usingCurrencyFromFile ? getNewFactors(ComboBox.getSelectedIndex(), false)
                        : getConversionFactor(ComboBox.getSelectedIndex(), false);
                double result;
                result = value * factor;
                String resultIn2dp = String.format("%.2f", result);
                conversionResult.setText(symbolForResult + resultIn2dp);
                conversionCount++;
                numberOfConversion.setText("Conversion count: " + conversionCount);
                String combo = (String) ComboBox.getSelectedItem();
                System.out.println(combo);
                String entamount = InputTextField.getText();
                System.out.println(entamount);
                String conamount = conversionResult.getText();
                System.out.println(conamount);
                DbOperation db=new DbOperation();
                String query="insert into converter(currency,enteramount,convertamount)"
                        + " values('"+combo+"','"+entamount+"','"+conamount+"')";
                int ans= db.insert(query);
            } else if (!text.isEmpty() && reverseCheckBox.isSelected()){

                double value = 0;

                try{
                    value = Double.parseDouble(text.trim());

                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,
                            "You have entered an invalid number. " +
                                    "Please enter a valid number.",
                            "WARNING",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double factor = usingCurrencyFromFile ? getNewFactors(ComboBox.getSelectedIndex(),
                        true)
                        : getConversionFactor(ComboBox.getSelectedIndex(), true);
                double result;

                result = value / factor;
                String resultIn2dp = String.format("%.2f", result);
                conversionResult.setText(symbolForResult + resultIn2dp);
                conversionCount++;
                numberOfConversion.setText("Conversion count: " + conversionCount);



            }else {

                JOptionPane.showMessageDialog(null,
                        "The TextField should not be empty.",
                        "WARNING",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //loading file through load menu
    private class LoadMenuItemListener implements ActionListener{
        //action event handler
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser jfc = new JFileChooser();  //creating object of file choose

            int userOption = jfc.showDialog(null,"Select file");
            jfc.setVisible(true);

            if (userOption == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                loadCurrencyFile(file);
                InputTextField.setText("");
                conversionResult.setText(" ");
            }
        }
    }



}
