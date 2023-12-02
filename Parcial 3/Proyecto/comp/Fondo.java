package comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import coords.*;
import lib.*;

public class Fondo extends JPanel implements Runnable {

    private BufferedImage fondo = null;
    private Graphics gFondo;
    private BufferedImage animacion = null;
    private Graphics gAnimacion;
    


    //Calculos
    private int[] vectorProyeccion = { 8, 7, 50 };
    

    public Fondo() {}

    private void dibujarRieles() {
        Color gris = new Color(214, 212, 210);
        Rieles rieles = new Rieles();
        ArrayList<Puntos> puntosXY = new ArrayList<Puntos>();
        BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graficos2D graficos2d = new Graficos2D(buffer);
        Graficos3D graficos3d = new Graficos3D();

        for(int i=0; i<rieles.getPuntosX().length; i++) {
            double u = graficos3d.calcularUParalela(rieles.getPuntosZ()[i], vectorProyeccion[2]);
            double x = graficos3d.calcularXParalela(rieles.getPuntosX()[i], u, vectorProyeccion[0]);
            double y = graficos3d.calcularYParalela(rieles.getPuntosY()[i], u, vectorProyeccion[1]);

            puntosXY.add(new Puntos((int)x, (int)y));
        }

        graficos2d.paintLine(puntosXY.get(0).x, puntosXY.get(0).y, puntosXY.get(1).x, puntosXY.get(1).y, Color.GRAY, fondo);
        graficos2d.paintLine(puntosXY.get(2).x, puntosXY.get(2).y, puntosXY.get(3).x, puntosXY.get(3).y, Color.GRAY, fondo);
        
        for(int i=0; i<4; i++) {
            graficos2d.paintLine(puntosXY.get(i).x, puntosXY.get(i).y, puntosXY.get(4).x, puntosXY.get(4).y, Color.GRAY, fondo);
        }

        int[][] riel = {
            { puntosXY.get(0).x, puntosXY.get(0).y },
            { puntosXY.get(4).x, puntosXY.get(4).y },
            { puntosXY.get(1).x, puntosXY.get(1).y }
        };
        graficos2d.fillPolygon(riel, gris, fondo);
        int[][] riel2 = {
            { puntosXY.get(3).x, puntosXY.get(3).y },
            { puntosXY.get(4).x, puntosXY.get(4).y },
            { puntosXY.get(2).x, puntosXY.get(2).y }
        };
        graficos2d.fillPolygon(riel2, gris, fondo);
        graficos2d.floodFillCircle(640, 10, 5, fondo, Color.WHITE);
    }

    private void dibujarTablas() {
        Color maderaOscuro = new Color(48, 33, 8);
        Color madera = new Color(139, 96, 23);
        Tablas tablas = new Tablas();
        ArrayList<Puntos> puntosXY = new ArrayList<Puntos>();
        BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graficos2D graficos2d = new Graficos2D(buffer);
        Graficos3D graficos3d = new Graficos3D();

        for(int i=0; i<tablas.getPuntosX().length; i++) {
            double u = graficos3d.calcularUPerspectiva(tablas.getPuntosZ()[i], vectorProyeccion[2]);
            double x = graficos3d.calcularXPerspectiva(tablas.getPuntosX()[i], u, vectorProyeccion[0]);
            double y = graficos3d.calcularYPerspectiva(tablas.getPuntosY()[i], u, vectorProyeccion[1]);

            puntosXY.add(new Puntos((int)x, (int)y));
        }

        ArrayList<Tablas> caras = new ArrayList<Tablas>();
        int separacion = 0;
        int reduccion = 0;

        for(int i=0; i<6; i++) {

            int[][] cara = {
                { puntosXY.get(1).x + reduccion, puntosXY.get(1).y - separacion },
                { puntosXY.get(0).x + reduccion, puntosXY.get(0).y - separacion },
                { puntosXY.get(3).x - reduccion, puntosXY.get(3).y - separacion },
                { puntosXY.get(4).x - reduccion, puntosXY.get(4).y - separacion } 
            };
            
            int[][] cara2 = {
                { puntosXY.get(2).x + reduccion, puntosXY.get(2).y - separacion },
                { puntosXY.get(1).x + reduccion, puntosXY.get(1).y - separacion },
                { puntosXY.get(4).x - reduccion, puntosXY.get(4).y - separacion },
                { puntosXY.get(5).x - reduccion, puntosXY.get(5).y - separacion }
            };
            
            reduccion += 40;
            separacion += 90;
            caras.add(new Tablas(cara, cara2));
        }

        System.out.println(caras.size());
        for(int i=0; i<caras.size(); i ++) {
            graficos2d.fillPolygon(caras.get(i).cara, maderaOscuro, fondo);
            graficos2d.fillPolygon(caras.get(i).cara2, madera, fondo);
        }

        dibujarRieles();
    }
    
    private void dibujarEdificios() {
        Edificios edificios = new Edificios();
    }

    private void dibujarCerros() {

    }

    private void dibujarAnimacion() {
        
    }


    private void dibujarFondo(Graphics g) {

        if( fondo == null ) {

            fondo = new BufferedImage(1280, 600, BufferedImage.TYPE_INT_RGB);
            gFondo = fondo.getGraphics();

            dibujarTablas();
            dibujarEdificios();
            dibujarCerros();

        }

        update(g);
    }

    @Override
    public void update(Graphics g) {
        animacion = new BufferedImage(1280, 600, BufferedImage.TYPE_INT_RGB);
        gAnimacion = animacion.getGraphics();
        gAnimacion.drawImage(fondo, 0, 0, this);

        dibujarAnimacion();

        g.drawImage(animacion, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {              
        dibujarFondo(g);
    }

    @Override
    public void run() {
        
        while (true) {

            try {
                repaint();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }

        }

    }
}
