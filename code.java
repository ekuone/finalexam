
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import javax.swing.*;

public class App
{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable()
        {
            public void run() {
                CalWin window = new CalWin();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setPreferredSize(new Dimension(250, 150));
                window.pack();
                window.setVisible(true);
            }
        });
    }
}
class CalWin extends JFrame {
    public CalWin() {
        setTitle("FinalExam");
        CalBox Box = new CalBox();
        add(Box);
        pack();
    }
}
class CalBox extends JPanel {

    public CalBox() {
        setLayout(new BorderLayout());

        user_input = 0;
        user_operation = "=";
        run = true;

        solution = new JButton("Hello, mr. Nurlan!");
        solution.setEnabled(false);
        add(solution, BorderLayout.NORTH);

        ActionListener numbers = new InsertAction();
        ActionListener point = new InsertAction();
        ActionListener operation = new CommandAction();

        Box = new JPanel(null);
        //Box.setLayout(new GridLayout(4, 4));
        Box.setLayout(new GridLayout(5, 4));

        addButton("7", numbers);
        addButton("8", numbers);
        addButton("9", numbers);
        addButton("/", operation);

        addButton("4", numbers);
        addButton("5", numbers);
        addButton("6", numbers);
        addButton("*", operation);

        addButton("1", numbers);
        addButton("2", numbers);
        addButton("3", numbers);
        addButton("-", operation);

        addButton("0", numbers);
        addButton(".", numbers);
        addButton("=", operation);
        addButton("+", operation);
        addButton("^", operation);

        add(Box, BorderLayout.CENTER);



    }
    private void addButton(String num_icon, ActionListener listener) {
        JButton num_button = new JButton(num_icon);
        num_button.addActionListener(listener);
        Box.add(num_button);
    }
    private class InsertAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            String input = event.getActionCommand();
            if(run) {
                solution.setText("");
                run = false;
            }
            solution.setText(solution.getText() + input);
        }
    }
    private class CommandAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            String operation = event.getActionCommand();
            if(run)
            {
                if(operation.equals("-"))
                {
                    solution.setText(operation);
                    run = false;
                }
                else user_operation = operation;
            }
            else
            {
                Abracadabra(Double.parseDouble(solution.getText()));

                try {
                    txtwrite(Double.parseDouble(solution.getText()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                user_operation = operation;
                run=true;
            }
        }

        private void txtwrite(double parseDouble) {
            try {
                File answers = new File("answers.txt");
                if (answers.createNewFile()){
                    String answ = solution.getText();
                    byte[] bs = answ.getBytes();
                    Path writtenFilePath = Files.write(Paths.get(answ), bs);
                    new String(Files.readAllBytes(writtenFilePath));
                }
                else{
                    String answ = solution.getText();
                    byte[] bs = answ.getBytes();
                    Path writtenFilePath = Files.write(Paths.get(answ), bs);
                    new String(Files.readAllBytes(writtenFilePath));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void Abracadabra(double user_num)
    {
        if(user_operation.equals("+")) user_input += user_num;
        else if(user_operation.equals("*")) user_input *= user_num;
        else if(user_operation.equals("/")) user_input /= user_num;
        else if(user_operation.equals("=")) user_input = user_num;
        else if(user_operation.equals("-")) user_input -= user_num;
        else if(user_operation.equals("^")) user_input =Math.pow(user_input,user_num);
        solution.setText("" + new DecimalFormat("0.0").format(user_input));

    }
    private JPanel Box;
    private boolean run;
    private double user_input;
    private String user_operation;
    private JButton solution;
}
