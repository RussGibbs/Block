import java.awt.*;

public class CollisionBlock {
    private int width;
    private int height;
    private final Color color;
    private double x;
    private double y;

    public CollisionBlock(int width, int height, Color color, double x, double y) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public CollisionBlock(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
        x = 0;
        y = 0;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
