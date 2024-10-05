import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.event.KeyEvent.*;
import static java.lang.Thread.sleep;

public class AppFrame extends JFrame implements ActionListener {
    Block block;
    CollisionBlock collisionBlock;

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

        collisionBlock = new CollisionBlock(100, 100, new Color(0, 0, 0), 5, 700);

        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                collisionBlock.setX(getMousePosition().x - 930);
                collisionBlock.setY(getMousePosition().y - 60);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == VK_UP)
                {
                    for (int i = 0; i < 2; i++) {
                        long start = System.nanoTime();
                        collisionBlock.setY(collisionBlock.getY() - 1);
                        while (System.nanoTime() - start  < 2000000);
                    }

                }
                if(e.getKeyCode() == VK_DOWN)
                {
                    for (int i = 0; i < 2; i++) {
                        long start = System.nanoTime();
                        collisionBlock.setY(collisionBlock.getY() + 1);
                        while (System.nanoTime() - start  < 2000000);
                    }
                }
                if(e.getKeyCode() == VK_LEFT)
                {
                    for (int i = 0; i < 2; i++) {
                        long start = System.nanoTime();
                        collisionBlock.setX(collisionBlock.getX() - 1);
                        while (System.nanoTime() - start  < 2000000);
                    }
                }
                if(e.getKeyCode() == VK_RIGHT)
                {
                    for (int i = 0; i < 2; i++) {
                        long start = System.nanoTime();
                        collisionBlock.setX(collisionBlock.getX() + 1);
                        while (System.nanoTime() - start  < 2000000);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        simPanel.addMouseMotionListener(mouseMotionListener);
        this.addKeyListener(keyListener);

        block = new Block(simPanel, collisionBlock, new Color(255, 255, 255), new Color(0, 0, 0), 30,
                30, 300, 300, 0, 0, 10,
                0.5, Double.parseDouble(gravityTextField.getText()), Double.parseDouble(dampingTextField.getText()),
                0.7, 0.9, 10);

        sleep(10);
        revalidate();
        start();
    }

    int i = 0;
    public void start() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {

                    block.move(0.001);
                    if (i % 16 == 0) {
                        //revalidate();
                        repaint();
                        Thread.sleep(2);
                        i = 0;
                    }
                    i++;
                }
            }
        };
        worker.execute();
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
