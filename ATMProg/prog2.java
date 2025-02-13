import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class prog2 extends JFrame{
    private double bal = 1000.00;   //initial balance
    private JTextArea textDis;      //text area in the middle
    private ArrayList<String> transactionHistory;   //to store transaction history

    public prog2() {
        transactionHistory = new ArrayList<>();
        setTitle("Maze Bank");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 

        textDis = new JTextArea();
        textDis.setEditable(false);
        textDis.setFont(new Font("Courier New",Font.PLAIN,16));
        textDis.setBackground(Color.WHITE);
        textDis.setText("Welcome To Maze Bank\n Please Enter Your PIN :\n");
        add(textDis, BorderLayout.CENTER);

        JPanel buttonPanel1 = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(3 , 1));
        buttonPanel2.setLayout(new GridLayout(3 , 1));

        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("       Deposit        ");
        JButton transferButton = new JButton("Transfer");
        JButton transferHistoryButton = new JButton("Transaction History");
        JButton exitButton = new JButton("Exit");

        withdrawButton.addActionListener(new WithdrawAction());
        depositButton.addActionListener(new DepositAction());
        checkBalanceButton.addActionListener(new CheckBalanceAction());
        transferButton.addActionListener(new TransferAction()); 
        transferHistoryButton.addActionListener(new HistoryAction()); 
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel1.add(withdrawButton);
        buttonPanel1.add(depositButton);
        buttonPanel1.add(transferButton);
        buttonPanel2.add(checkBalanceButton);
        buttonPanel2.add(transferHistoryButton);
        buttonPanel2.add(exitButton);

        add(buttonPanel1,BorderLayout.WEST);
        add(buttonPanel2,BorderLayout.EAST);
        String PIN = JOptionPane.showInputDialog("Enter Your PIN: ");
        if(PIN != null && PIN.equals("6898")) {
            textDis.setText("PIN Accepted.\nChoose An Operation.");
        }
        else if(PIN == null) {
            System.exit(0);
        }
        else {
            JOptionPane.showMessageDialog(prog2.this,"Invalid PIN.\nPlease Try Again");
            System.exit(0);
        }
    }

    private class WithdrawAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amt = JOptionPane.showInputDialog(prog2.this,"Enter Amount to Withdraw: ");
            if(amt != null) {
                try {
                    double amount = Double.parseDouble(amt);
                    if(amount > 0 && amount <= bal) {
                        bal = bal - amount;
                        transactionHistory.add("Withdrew: $"+amount);
                        textDis.setText("Withdrawl Succesful.\nCurrent Balance: $"+bal);
                    }
                    else if(bal <= 0 || amount > bal) {
                        textDis.setText("Insufficient Funds.");
                    }
                } catch (NumberFormatException ex) {
                    textDis.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class DepositAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amt = JOptionPane.showInputDialog(prog2.this,"Enter Amount to Deposit: ");
            if(amt != null) {
                try {
                    double amount = Double.parseDouble(amt);
                    bal = bal + amount;
                    transactionHistory.add("Deposited: $"+amount);
                    textDis.setText("Deposit Succesful.\nCurrent Balance: $"+bal);
                }
                catch (NumberFormatException ex) {
                    textDis.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class CheckBalanceAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textDis.setText("Current Balance: $"+bal);
        }
    }
    
    private class TransferAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String recp = JOptionPane.showInputDialog(prog2.this,"Enter Recipient's Account No. : ");
            if(recp != null){
                try{
                    long recpAcc = Long.parseLong(recp);
                    if(recpAcc > 99999999999L && recpAcc < 1000000000000L) {
                        String amt = JOptionPane.showInputDialog(prog2.this,"Enter Amount: ");
                        double amount = Double.parseDouble(amt);
                        if(amount > 0 && amount <= bal) {
                            bal = bal - amount;
                            transactionHistory.add("\nTransferred Amount: $"+bal+"\nTransfer Account: "+recp+"\n");
                            textDis.setText("Transferred Amount Succesfully.\nCurrent Balance: $"+bal);
                        }
                        else if(bal <= 0 || amount > bal) {
                            textDis.setText("Insufficient Funds.");
                        }
                    }
                    else {
                        textDis.setText("Invalid Account No.\nPlease Try Again");
                    } 
                }
                catch (NumberFormatException ex) {
                    textDis.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class HistoryAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(transactionHistory.isEmpty()) {
                textDis.setText("No Transaction History \n ");
            }
            StringBuilder history = new StringBuilder("Transaction History : \n");
            for(String transaction : transactionHistory){
                history.append(transaction).append("\n");
            }
            textDis.setText(history.toString());
        }
    }
    public static void main(String[] args) {
            prog2 atm = new prog2();
            atm.setVisible(true);
    }
}