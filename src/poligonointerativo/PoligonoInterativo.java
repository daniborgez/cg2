
package poligonointerativo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeListener;

public class PoligonoInterativo extends JPanel {

    private int lados = 6;
    private double raio = 100;
    private double angulo = 0;
    private double escalaX = 1.0;
    private double escalaY = 1.0;
    private double shearX = 0.0;
    private double shearY = 0.0;
    private double deslocX = 0.0;
    private double deslocY = 0.0;

    public void setParametros(int lados, double angulo, double escalaX, double escalaY,
                               double shearX, double shearY, double deslocX, double deslocY) {
        this.lados = lados;
        this.angulo = angulo;
        this.escalaX = escalaX;
        this.escalaY = escalaY;
        this.shearX = shearX;
        this.shearY = shearY;
        this.deslocX = deslocX;
        this.deslocY = deslocY;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int largura = getWidth();
        int altura = getHeight();
        int centroX = largura / 2;
        int centroY = altura / 2;

        Polygon poligono = new Polygon();
        double anguloRad = Math.toRadians(angulo);
        double cos = Math.cos(anguloRad);
        double sin = Math.sin(anguloRad);

        for (int i = 0; i < lados; i++) {
            double theta = 2 * Math.PI * i / lados;
            double x = raio * Math.cos(theta);
            double y = raio * Math.sin(theta);

            // escala
            x *= escalaX;
            y *= escalaY;

            // rotação
            double xRot = x * cos - y * sin;
            double yRot = x * sin + y * cos;

            // cisalhamento
            double xShear = xRot + shearX * yRot;
            double yShear = yRot + shearY * xRot;

            // Translação
            int xFinal = (int) (xShear + centroX + deslocX);
            int yFinal = (int) (yShear + centroY + deslocY);

            poligono.addPoint(xFinal, yFinal);
        }

        g2.setColor(Color.MAGENTA);
        g2.drawPolygon(poligono);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polígono Interativo");
        PoligonoInterativo painel = new PoligonoInterativo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLayout(new BorderLayout());
        frame.add(painel, BorderLayout.CENTER);

        JPanel controles = new JPanel(new GridLayout(0, 2));

        JSlider ladosSlider = new JSlider(3, 50, 6);
        JSlider anguloSlider = new JSlider(0, 360, 0);
        JSlider escalaXSlider = new JSlider(50, 200, 100);
        JSlider escalaYSlider = new JSlider(50, 200, 100);
        JSlider shearXSlider = new JSlider(-100, 100, 0);
        JSlider shearYSlider = new JSlider(-100, 100, 0);
        JSlider deslocXSlider = new JSlider(-200, 200, 0);
        JSlider deslocYSlider = new JSlider(-200, 200, 0);

        controles.add(new JLabel("Lados"));
        controles.add(ladosSlider);
        controles.add(new JLabel("Ângulo (graus)"));
        controles.add(anguloSlider);
        controles.add(new JLabel("Escala X (%)"));
        controles.add(escalaXSlider);
        controles.add(new JLabel("Escala Y (%)"));
        controles.add(escalaYSlider);
        controles.add(new JLabel("Cisalhamento X"));
        controles.add(shearXSlider);
        controles.add(new JLabel("Cisalhamento Y"));
        controles.add(shearYSlider);
        controles.add(new JLabel("Translação X"));
        controles.add(deslocXSlider);
        controles.add(new JLabel("Translação Y"));
        controles.add(deslocYSlider);

        ChangeListener atualizar = e -> painel.setParametros(
                ladosSlider.getValue(),
                anguloSlider.getValue(),
                escalaXSlider.getValue() / 100.0,
                escalaYSlider.getValue() / 100.0,
                shearXSlider.getValue() / 100.0,
                shearYSlider.getValue() / 100.0,
                deslocXSlider.getValue(),
                deslocYSlider.getValue()
        );

        ladosSlider.addChangeListener(atualizar);
        anguloSlider.addChangeListener(atualizar);
        escalaXSlider.addChangeListener(atualizar);
        escalaYSlider.addChangeListener(atualizar);
        shearXSlider.addChangeListener(atualizar);
        shearYSlider.addChangeListener(atualizar);
        deslocXSlider.addChangeListener(atualizar);
        deslocYSlider.addChangeListener(atualizar);

        frame.add(controles, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}

