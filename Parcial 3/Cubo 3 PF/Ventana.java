import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JPanel {
    
    private BufferedImage image;
    private int[] vectorProyeccion = { 10, -10, 100 };
    private int[] vectorProyeccion2 = { 10, 10, -50 };
    private int[] vectorProyeccion3 = { -10, 10, 100 };

    private int xcentro = 50;
    private int ycentro = 50;

    Color color1 = Color.WHITE;
    Color color2 = Color.GREEN;
    
    public Ventana() {
        JFrame frame = new JFrame("Cubo");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void proyeccion() {
        BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Figuras figuras = new Figuras(buffer);

        int aux = 0;
        int[] figuraX1 = new int[4]; 
        int[] figuraY1 = new int[4];
        
        int[] figuraX2 = new int[4];
        int[] figuraY2 = new int[4];
        
        Cubo cubo = new Cubo();
        image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

        for(int i=0; i<8; i++) { 
            double u1 = calcularUPerspectiva(cubo.getPuntosZ()[i], vectorProyeccion[2]);
            double x1 = calcularXPerspectiva(cubo.getPuntosX()[i], u1, vectorProyeccion[0]);
            double y1 = calcularYPerspectiva(cubo.getPuntosY()[i], u1, vectorProyeccion[1]);

            double u2 = calcularUPerspectiva(cubo.getPuntosZ()[i], vectorProyeccion2[2]);
            double x2 = calcularXPerspectiva(cubo.getPuntosX()[i], u2, vectorProyeccion2[0]);
            double y2 = calcularYPerspectiva(cubo.getPuntosY()[i], u2, vectorProyeccion2[1]);

            double u3 = calcularUPerspectiva(cubo.getPuntosZ()[i], vectorProyeccion3[2]);
            double x3 = calcularXPerspectiva(cubo.getPuntosX()[i], u3, vectorProyeccion3[0]);
            double y3 = calcularYPerspectiva(cubo.getPuntosY()[i], u3, vectorProyeccion3[1]);

            double x = (x1 + x2 + x3) / 3;
            double y = (y1 + y2 + y3) / 3;

            if(i < 4) {
                figuraX1[i] = (int) (xcentro + (x*3)); 
                figuraY1[i] = (int) (ycentro + (y*3));
            } else {
                figuraX2[aux] = (int) (xcentro + (x*3));
                figuraY2[aux] = (int) (ycentro + (y*3));
                aux++;
            }
        }

        figuras.poligono4Lados(figuraX1, figuraY1, color1, image);
        figuras.poligono4Lados(figuraX2, figuraY2, color1, image);
        figuras.bresenham(figuraX1[0], figuraY1[0], figuraX2[0], figuraY2[0], color1, image);
        figuras.bresenham(figuraX1[1], figuraY1[1], figuraX2[1], figuraY2[1], color1, image);
        figuras.bresenham(figuraX1[2], figuraY1[2], figuraX2[2], figuraY2[2], color1, image);
        figuras.bresenham(figuraX1[3], figuraY1[3], figuraX2[3], figuraY2[3], color1, image);
        
    }

    private double calcularUParalela(double z, int zp) {
        return (z / zp) * (-1);
    }

    private double calcularXParalela(double x1, double u, int xp) {
        return x1 + (xp * u);
    }

    private double calcularYParalela(double y1, double u, int zp) {
        return y1 + (zp * u);
    }

    private double calcularUPerspectiva(double z, int zp) {
        return ((-1 * zp) / (z - zp));
    }

    private double calcularXPerspectiva(double x1, double u, int xp) {
        return xp + ((x1 - xp) * u);
    }

    private double calcularYPerspectiva(double y1, double u, int yp) {
        return yp + ((y1 - yp) * u);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        proyeccion();
        g.drawImage(image, 0, 0, this);
    }
}
