import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppFrame extends JFrame implements ActionListener {
    Block block;

    JTextField gravityTextField;
    JButton gravityButton;

    JTextField dampingTextField;
    JButton dampingButton;

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
        controlPanel.setLayout(new GridLayout(2, 1));
        controlPanel.setBackground(new Color(100, 100, 100));
        jPanel.add(controlPanel);

        JPanel controlPanelUpper = new JPanel();
        controlPanelUpper.setLayout(new FlowLayout());
        controlPanelUpper.setBackground(new Color(100, 100, 100));
        controlPanel.add(controlPanelUpper);

        JPanel controlPanelLower = new JPanel();
        controlPanelLower.setLayout(new FlowLayout());
        controlPanelLower.setBackground(new Color(100, 100, 100));
        controlPanel.add(controlPanelLower);

        gravityTextField = new JTextField(10);
        gravityTextField.setText("-0.05");
        //gravityTextField.addActionListener(this);
        controlPanelUpper.add(gravityTextField);

        gravityButton = new JButton("Set");
        controlPanelUpper.add(gravityButton);
        gravityButton.addActionListener(this);

        dampingTextField = new JTextField(10);
        dampingTextField.setText("0.003");
        controlPanelLower.add(dampingTextField);

        dampingButton = new JButton("Set");
        controlPanelLower.add(dampingButton);
        dampingButton.addActionListener(this);

        simPanel = new JPanel();
        simPanel.setVisible(true);
        simPanel.setBackground(new Color(100, 100, 100));
        simPanel.setLayout(new FlowLayout());
        simPanel.setSize(800, 1000);
        jPanel.add(simPanel);

        CollisionBlock collisionBlock = new CollisionBlock(50, 50, new Color(0, 0, 0), 5, 5);

        block = new Block(simPanel, collisionBlock, new Color(255, 255, 255), new Color(0, 0, 0), 30,
                30, 300, 300, 0, 0, 10,
                0.5, Double.parseDouble(gravityTextField.getText()), Double.parseDouble(dampingTextField.getText()), 0.7, 0.9, 10);
        Thread.sleep(10);
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

        if (e.getSource() == dampingButton) {
            block.setDamping(Double.parseDouble(dampingTextField.getText()));
        }
    }
}
