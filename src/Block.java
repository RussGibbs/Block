import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Math;

public class Block extends JComponent{
    private final JPanel jPanel;
    private final CollisionBlock collisionBlock;
    private final Vertex[][] vertices;
    private final BufferedImage graphics;
    private final Graphics2D g;

    Color backgroundColor;
    Color vertexColor;

    int defaultSeparation;
    int equilibriumSeperation;

    int initialHeight;
    int initialLength;

    double initialSpeedX;
    double initialSpeedY;

    double initialRandomness;
    double springyness;
    double gravity;
    double damping;
    double collisionRestitution;
    double frictionRestitution;

    int dimensions;


    public Block(JPanel jPanel, CollisionBlock collisionBlock, Color backgroundColor, Color vertexColor, int defaultSeparation,
                 int equilibriumSeperation, int initialHeight, int initialLength, double initialSpeedX,
                 double initialSpeedY, double initialRandomness, double springyness, double gravity,
                 double damping, double collisionRestitution, double frictionRestitution, int dimensions) {

        this.jPanel = jPanel;
        this.collisionBlock = collisionBlock;
        this.backgroundColor = backgroundColor;
        this.vertexColor = vertexColor;
        this.defaultSeparation = defaultSeparation;
        this.equilibriumSeperation = equilibriumSeperation;
        this.initialHeight = initialHeight;
        this.initialLength = initialLength;
        this.initialSpeedX = initialSpeedX;
        this.initialSpeedY = initialSpeedY;
        this.initialRandomness = initialRandomness;
        this.springyness = springyness;
        this.gravity = gravity;
        this.damping = damping;
        this.collisionRestitution = collisionRestitution;
        this.frictionRestitution = frictionRestitution;
        this.dimensions = dimensions;

        this.vertices = new Vertex[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                this.vertices[i][j] = new Vertex(initialLength + defaultSeparation * i, initialHeight + defaultSeparation * j,
                        (Math.random() - 0.5) * initialRandomness + initialSpeedX,
                        (Math.random() - 0.5) * initialRandomness + initialSpeedY, 0, 0);
            }
        }

        // this commented out stuff was to rotate it


        /*for (int i = 0; i < 10; i++) {
            vertices[i][0].setSpeedX(500);
            vertices[i][9].setSpeedX(-500);
            vertices[0][i].setSpeedY(500);
            vertices[9][i].setSpeedX(-500);

        }*/


        recalculateForce();
        graphics = new BufferedImage(jPanel.getWidth() - 10, jPanel.getHeight() - 10, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) graphics.getGraphics();
        g.setColor(vertexColor);
        jPanel.add(new JLabel(new ImageIcon(graphics)), BorderLayout.CENTER);
    }

    public void paint() {
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                g.fillOval((int) vertices[i][j].getX(), (int) vertices[i][j].getY(), 10, 10);
            }
        }
    }

    public void move(double dt) {
        recalculateForce();
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                Vertex v = this.vertices[i][j];
                Vertex v2;

                // applies the speed to set the position
                v.setX(v.getX() + v.getSpeedX() * dt);
                v.setY(v.getY() - v.getSpeedY() * dt);

                // applies the force to set the speed plus gravity
                v.setSpeedY(v.getSpeedY() + v.getForceY());
                v.setSpeedX(v.getSpeedX() + v.getForceX());


                // all of this essentially applies damping
                if (i != 0) {
                    double d = 0.1;
                    v2 = vertices[i - 1][j];
                    v.setSpeedX(v.getSpeedX() - damping * (v.getSpeedX() - v2.getSpeedX()) *
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                    v.setSpeedY(v.getSpeedY() - damping * (v.getSpeedY() - v2.getSpeedY())*
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                    if (j != 0) {
                        v2 = vertices[i - 1][j - 1];
                        v.setSpeedX(v.getSpeedX() - damping * (v.getSpeedX() - v2.getSpeedX()) *
                                Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                        Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                        v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                        Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                                v2.getSpeedY() * 0.1), 2))));
                        v.setSpeedY(v.getSpeedY() - damping * (v.getSpeedY() - v2.getSpeedY())*
                                Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                        Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                        v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                        Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                                v2.getSpeedY() * 0.1), 2))));
                    }
                }

                if (i != dimensions - 1) {
                    v2 = vertices[i + 1][j];
                    v.setSpeedX(v.getSpeedX() - damping * (v.getSpeedX() - v2.getSpeedX()) *
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                    v.setSpeedY(v.getSpeedY() - damping * (v.getSpeedY() - v2.getSpeedY())*
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                    if (j != dimensions - 1) {
                        v2 = vertices[i + 1][j + 1];
                        v.setSpeedX(v.getSpeedX() - damping * (v.getSpeedX() - v2.getSpeedX()) *
                                Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                        Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                        v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                        Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                                v2.getSpeedY() * 0.1), 2))));
                        v.setSpeedY(v.getSpeedY() - damping * (v.getSpeedY() - v2.getSpeedY())*
                                Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                        Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                        v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                        Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                                v2.getSpeedY() * 0.1), 2))));
                    }
                }

                if (j != 0) {
                    v2 = vertices[i][j - 1];
                    v.setSpeedX(v.getSpeedX() - damping * (v.getSpeedX() - v2.getSpeedX()) *
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                    v.setSpeedY(v.getSpeedY() - damping * (v.getSpeedY() - v2.getSpeedY())*
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                }

                if (j != dimensions - 1) {
                    v2 = vertices[i][j + 1];
                    v.setSpeedX(v.getSpeedX() - damping * (v.getSpeedX() - v2.getSpeedX()) *
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                    v.setSpeedY(v.getSpeedY() - damping * (v.getSpeedY() - v2.getSpeedY())*
                            Math.abs(Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) +
                                    Math.pow(v.getY() - v2.getY(), 2)) - Math.sqrt(Math.pow((v.getX() +
                                    v.getSpeedX() * 0.1) - (v2.getX() + v2.getSpeedX() * 0.1), 2) +
                                    Math.pow((v.getY() + v.getSpeedY() * 0.1) - (v2.getY() +
                                            v2.getSpeedY() * 0.1), 2))));
                }



                // semi-elastic collision with top and bottom
                if (v.getY() > (jPanel.getHeight() - 20.1) && v.getSpeedY() < 0 || v.getY() < 0.1 && v.getSpeedY() > 0) {
                    v.setSpeedY(v.getSpeedY() * -collisionRestitution);
                }

                // semi-elastic collision with sides
                if (v.getX() > (jPanel.getWidth() - 20.1) && v.getSpeedX() > 0 || v.getX() < 0.1 && v.getSpeedX() < 0) {
                    v.setSpeedX(v.getSpeedX() * -collisionRestitution);
                }

                // friction on the top and bottom
                if (v.getY() > (jPanel.getHeight() - 20.1) || v.getY() < 0.1) {
                    v.setSpeedX(v.getSpeedX() * frictionRestitution);
                }

                // friction on the sides
                if (v.getX() > (jPanel.getWidth() - 20.1) || v.getX() < 0.1) {
                    v.setSpeedY(v.getSpeedY() * frictionRestitution);
                }
            }
        }
        g.setColor(backgroundColor);
        g.fillRect(0, 0, jPanel.getWidth(), jPanel.getHeight());
        g.setColor(new Color(0, 0, 0));
        paint();
    }

    public void recalculateForce() {
        // calculates all the forces due to adjacent vertices
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                Vertex v = this.vertices[i][j];
                Vertex v2;
                int sign;
                double netForce;
                v.setForceY(0);
                v.setForceX(0);

                if (i != 0) {
                    if (j != 0) {
                        v2 = vertices[i - 1][j - 1];
                        sign = 1;
                        if (v.getX() < v2.getX()) {
                            sign = -1;
                        }
                        netForce = equilibriumSeperation * Math.sqrt(2) - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                        v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                        v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    }
                    if (j != dimensions - 1) {
                        v2 = vertices[i - 1][j + 1];
                        sign = 1;
                        if (v.getX() < v2.getX()) {
                            sign = -1;
                        }
                        netForce = equilibriumSeperation * Math.sqrt(2) - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                        v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                        v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    }
                    v2 = vertices[i - 1][j];
                    sign = 1;
                    if (v.getX() < v2.getX()) {
                        sign = -1;
                    }
                    netForce = equilibriumSeperation - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                    v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                }

                if (j != 0) {
                    v2 = vertices[i][j - 1];
                    sign = 1;
                    if (v.getX() < v2.getX()) {
                        sign = -1;
                    }
                    netForce = equilibriumSeperation - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                    v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                }

                if (i != dimensions - 1) {
                    if (j != 0) {
                        v2 = vertices[i + 1][j - 1];
                        sign = 1;
                        if (v.getX() < v2.getX()) {
                            sign = -1;
                        }
                        netForce = equilibriumSeperation * Math.sqrt(2) - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                        v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                        v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    }
                    if (j != dimensions - 1) {
                        v2 = vertices[i + 1][j + 1];
                        sign = 1;
                        if (v.getX() < v2.getX()) {
                            sign = -1;
                        }
                        netForce = equilibriumSeperation * Math.sqrt(2) - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                        v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                        v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    }
                    v2 = vertices[i + 1][j];
                    sign = 1;
                    if (v.getX() < v2.getX()) {
                        sign = -1;
                    }
                    netForce = equilibriumSeperation - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                    v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                }

                if (j != dimensions - 1) {
                    v2 = vertices[i][j + 1];
                    sign = 1;
                    if (v.getX() < v2.getX()) {
                        sign = -1;
                    }
                    netForce = equilibriumSeperation - Math.sqrt(Math.pow(v.getY() - v2.getY(), 2) + Math.pow(v.getX() - v2.getX(), 2));
                    v.setForceX(v.getForceX() + (sign * netForce * Math.cos(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                    v.setForceY(v.getForceY() - (sign * netForce * Math.sin(Math.atan((v.getY() - v2.getY()) / (v.getX() - v2.getX())))) * springyness);
                }

                v.setForceY(v.getForceY() + gravity);
            }
        }

    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setDamping(double damping) {
        this.damping = damping;
    }
}
