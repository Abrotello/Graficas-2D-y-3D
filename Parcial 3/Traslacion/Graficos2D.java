import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Graficos2D {

    private BufferedImage imagen;
    private BufferedImage buffer;
    private Graphics gImagen;

    public Graficos2D(BufferedImage bufferedImage) {
        this.buffer = bufferedImage;
    }

    public void paintLine(int x1, int y1, int x2, int y2, Color color, BufferedImage imagen) {

        int x, y, dx, dy, dx2, dy2,  incX, incY, d = 0;
        this.imagen = imagen;
        
        dx = Math.abs(x2 - x1);
        dy = Math.abs(y2 - y1);

        dx2 = 2*dx;
        dy2 = 2*dy;

        incX = x1 < x2 ? 1 : -1;    //Si x1<x2 entonces incX valdra 1, si x1>x2 incX valdra -1
        incY = y1 < y2 ? 1 : -1;    //Si y1<y2 entonces incY valdra 1, si y1>y2 incY valdra -1

        x = x1;
        y = y1;

        if(dx >= dy) {
            while(true) {
                if(x == x2)
                    break;
                x += incX;
                d += dy2;
                if(d > dx) {
                    y += incY;
                    d -= dx2;
                }

                putPixel(x, y, color);
                
            }
        } else {
            while(true) {
                if(y == y2)
                    break;
                y += incY;
                d += dx2;
                if(d > dy) {
                    x += incX;
                    d -= dy2;
                }

                putPixel(x, y, color);

            }
        }
    }

    public void paintCircle(int xcentro, int ycentro, int radio, double angulo, Color color, BufferedImage imagen) {
        this.imagen = imagen;
        int x = 0, y = 0;
        int anguloFinal = 3600;
        final double PASOS = 0.1;
        double anguloRadianes = 0;
        
        //Math.sin funciona en radianes
        for(int i=0; i <= anguloFinal; i++) {
            angulo += PASOS;
            anguloRadianes = degreesToRadians(angulo);
            x = (int) Math.round(xcentro + radio * Math.sin(anguloRadianes));
            y = (int) Math.round(ycentro + radio * Math.cos(anguloRadianes));

            putPixel(x, y, color);
        }
    }

    private double degreesToRadians(double anguloConvertir) {
        double resultado = anguloConvertir * Math.PI / 180;
        return resultado;
    }

    public void paintRect(int x1, int y1, int x2, int y2, Color color, BufferedImage image) {

        int x3 = x2;
        int y3 = y1;

        int x4 = x1;
        int y4 = y2;

        paintLine(x1, y1, x3, y3, color, image);
        paintLine(x1, y1, x4, y4, color, image);
        paintLine(x3, y3, x2, y2, color, image);
        paintLine(x4, y4, x2, y2, color, image);
    }

    public void poligono4Lados(int[] x, int[] y, Color c, BufferedImage bufferedImage) {

        paintLine(x[0], y[0], x[1], y[1], c, bufferedImage);
        paintLine(x[0], y[0], x[2], y[2], c, bufferedImage);
        
        paintLine(x[1], y[1], x[3], y[3], c, bufferedImage);
        paintLine(x[2], y[2], x[3], y[3], c, bufferedImage);

    }

    public void poligono3Lados(int[] x, int[] y, Color c, BufferedImage bufferedImage) {
     
        paintLine(x[0], y[0], x[1], y[1], c, bufferedImage);
        paintLine(x[0], y[0], x[2], y[2], c, bufferedImage);
        paintLine(x[1], y[1], x[2], y[2], c, bufferedImage);

    }

    public void floodFill(int x, int y, int rgb, BufferedImage bufferedImage, Color nuevo) {
        
        if((((x<0) || x > bufferedImage.getWidth())) || ((y<0) || (y>bufferedImage.getHeight()))) {
            System.out.println("return");
            return;
        }

        if(bufferedImage.getRGB(x, y) == rgb) {
            
            bufferedImage.setRGB(x, y, nuevo.getRGB());

            floodFill(x + 1, y, rgb, bufferedImage, nuevo);
            floodFill(x - 1, y, rgb, bufferedImage, nuevo);
            floodFill(x, y + 1, rgb, bufferedImage, nuevo);
            floodFill(x, y - 1, rgb, bufferedImage, nuevo);
        } else {
            return;
        }  
    }

    public void floodFillCircle(int xcentro, int ycentro, int radio, BufferedImage bufferedImage, Color color) {
        while(radio != 0) {
            radio--;
            paintCircle(xcentro, ycentro, radio, 0, color, bufferedImage);
        }
    }

    public void fillRect(int x1, int y1, int x2, int y2, Color relleno, BufferedImage image) {
        y1 += 1;
        x2 -= 1;
        while(y1 < y2) {
            paintLine(x1, y1, x2, y1, relleno, image);
            y1++;
        }
    }

    public void fillPolygon(int[][] figura, Color c, BufferedImage buffer) {
        int n = figura.length;
        int startX = Integer.MAX_VALUE;
        int startY = Integer.MAX_VALUE;
        int endX = Integer.MIN_VALUE;
        int endY = Integer.MIN_VALUE;
        this.imagen = buffer;

        for (int i = 0; i < n - 1; i++) {
            int x = figura[i][0];
            int y = figura[i][1];
            startX = Math.min(startX, x);
            startY = Math.min(startY, y);
            endX = Math.max(endX, x);
            endY = Math.max(endY, y);
        }

        int direction = 0; // 0: right, 1: down, 2: left, 3: up
        int x = startX;
        int y = startY;

        while (startX <= endX && startY <= endY) {
            while (x >= startX && x <= endX && y >= startY && y <= endY) {
                if (isInsideFigure(x, y, figura)) {
                    putPixel(x, y, c);
                }

                switch (direction) {
                    case 0:
                        x++;
                        break;
                    case 1:
                        y++;
                        break;
                    case 2:
                        x--;
                        break;
                    case 3:
                        y--;
                        break;
                }
            }

            switch (direction) {
                case 0:
                    startY++;
                    x--;
                    y++;
                    break;
                case 1:
                    endX--;
                    x--;
                    y--;
                    break;
                case 2:
                    endY--;
                    x++;
                    y--;
                    break;
                case 3:
                    startX++;
                    x++;
                    y++;
                    break;
            }

            direction = (direction + 1) % 4;
        }

    }

    private boolean isInsideFigure(int x, int y, int[][] figura) {

        int longitud = figura.length;
        boolean isInside = false;


        for (int i = 0, j = longitud - 1; i < longitud; j = i++) {
            int xi = figura[i][0];
            int yi = figura[i][1];
            int xj = figura[j][0];
            int yj = figura[j][1];

            if (((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
                isInside = !isInside;
            }
        }

        return isInside;
    }

    private void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        gImagen = imagen.getGraphics();
        gImagen.drawImage(buffer, x, y, null);
    }
}