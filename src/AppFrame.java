import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppFrame extends JFrame implements ActionListener {
    Block block;
    JTextField gravityTextField;
    JButton gravityButton;

    JPanel simPanel;

    public AppFrame() throws InterruptedException {
        this.setBackground(new Color(200, 200, 200));
        this.setSize(1920, 1080);
        this.setVisible(true);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel jPanel= new JPanel();
        jPanel.setVisible(true);
        jPanel.setLayout(new GridLayout(1, 2));
        jPanel.setBackground(new Color(100, 100, 100));

        this.add(jPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBackground(new Color(100, 100, 100));
        jPanel.add(controlPanel);

        gravityTextField = new JTextField();
        gravityTextField.setText("-0.05");
        controlPanel.add(gravityTextField);

        gravityButton = new JButton("Set");
        controlPanel.add(gravityButton);
        gravityButton.addActionListener(this);



        simPanel = new JPanel();
        simPanel.setVisible(true);
        simPanel.setBackground(new Color(100, 100, 100));
        simPanel.setLayout(new FlowLayout());
        simPanel.setSize(800, 1000);
        jPanel.add(simPanel);



        block = new Block(simPanel);

        block.setGravity(Double.parseDouble(gravityTextField.getText()));
        block.paint();
        revalidate();
        start();
    }

    int i = 0;
    public void start() throws InterruptedException {
        while (true) {
            long start = System.nanoTime();
            block.move(0.001);
            if (i % 16 == 0) {
                //revalidate();
                repaint();
                Thread.sleep(2);
                i = 0;
            }
            i++;
            while (System.nanoTime() - start < 100000);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gravityButton) {
            block.setGravity(Double.parseDouble(gravityTextField.getText()));
        }
    }
}
