package coords;

public class Rieles {
    /*                         A    B     C    D  Limite                     */
    private int[] puntosX = { 400, 450, 800, 850, 640 };
    private int[] puntosY = { 500, 500, 500, 500, 0};
    private int[] puntosZ = { 10,  10,  10, 10, 0};

    public int[] getPuntosX() {
        return this.puntosX;
    }

    public void setPuntosX(int[] puntosX) {
        this.puntosX = puntosX;
    }

    public int[] getPuntosY() {
        return this.puntosY;
    }

    public void setPuntosY(int[] puntosY) {
        this.puntosY = puntosY;
    }

    public int[] getPuntosZ() {
        return this.puntosZ;
    }

    public void setPuntosZ(int[] puntosZ) {
        this.puntosZ = puntosZ;
    }

    public Rieles() {}
}
