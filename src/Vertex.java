public class Vertex {
    private double x;
    private double y;
    private double speedX;
    private double speedY;
    private double forceX;
    private double forceY;
    public Vertex(double x, double y, double speedX, double speedY, double forceX, double forceY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.forceX = forceX;
        this.forceY = forceY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getForceX() {
        return forceX;
    }

    public double getForceY() {
        return forceY;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void setForceX(double forceX) {
        this.forceX = forceX;
    }

    public void setForceY(double forceY) {
        this.forceY = forceY;
    }
}
