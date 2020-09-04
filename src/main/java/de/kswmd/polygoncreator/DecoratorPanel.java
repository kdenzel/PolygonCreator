/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kswmd.polygoncreator;

import de.kswmd.polygoncreator.poly.Polygon;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author kai
 */
public class DecoratorPanel extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();

    private BufferedImage image;
    private File sourceFile;
    private final Polygon polygon = new Polygon();
    private Point selectedPoint = null;
    private final int dotRadius = 2;
    private int scaling;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setImage(File file) throws IOException {
        image = ImageIO.read(file);
        sourceFile = file;
        if (image != null) {
            scaling = 1;
            setSize(image.getWidth(), image.getHeight());
            setPreferredSize(getSize());
            polygon.clear();
        }
    }

    public void scaleUp() {
        if (scaling < 16) {
            scaling *= 2;
            setSize(image.getWidth() * scaling, image.getHeight() * scaling);
            setPreferredSize(getSize());
        }
    }

    public void scaleDown() {
        if (scaling != 1) {
            scaling /= 2;
            setSize(image.getWidth() * scaling, image.getHeight() * scaling);
            setPreferredSize(getSize());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int zeroX = (getWidth() / 2 - image.getWidth() * scaling / 2);
            int zeroY = (getHeight() / 2 - image.getHeight() * scaling / 2);
            drawGrid(g, zeroX, zeroY);
            g.drawImage(image, zeroX, zeroY, image.getWidth() * scaling, image.getHeight() * scaling, null);
            g.setColor(Color.BLACK);
            g.drawRect(zeroX, zeroY, image.getWidth() * scaling - 1, image.getHeight() * scaling - 1);
            if (polygon.hasVertices()) {
                g.setColor(Color.CYAN);
                for (int i = 0; i < polygon.size(); i++) {
                    Point p = polygon.get(i);
                    g.fillOval(zeroX + p.x * scaling - dotRadius * scaling, zeroY + p.y * scaling - dotRadius * scaling, dotRadius * scaling * 2, dotRadius * scaling * 2);
                    if (i > 0) {
                        Point lastPoint = polygon.get(i - 1);
                        g.drawLine(zeroX + lastPoint.x * scaling, zeroY + lastPoint.y * scaling, zeroX + p.x * scaling, zeroY + p.y * scaling);
                    }
                    if (i >= 2 && polygon.size() - 1 == i) {
                        Point firstPoint = polygon.get(0);
                        g.drawLine(zeroX + p.x * scaling, zeroY + p.y * scaling, zeroX + firstPoint.x * scaling, zeroY + firstPoint.y * scaling);
                    }
                }
                Graphics2D g2d = (Graphics2D) g;
                AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
                g2d.setComposite(composite);
                g2d.setColor(Color.CYAN);
                int[] xs = new int[polygon.size()];
                int[] ys = new int[polygon.size()];
                for (int i = 0; i < polygon.size(); i++) {
                    Point p = polygon.get(i);
                    xs[i] = zeroX + p.x * scaling;
                    ys[i] = zeroY + p.y * scaling;
                }
                g2d.fillPolygon(xs, ys, polygon.size());
            }
        }
    }

    private void drawGrid(Graphics g, int zeroX, int zeroY) {
        int squareSize = 10;
        boolean colorGrey;
        for (int y = 0; y < image.getHeight() * scaling; y += squareSize * scaling) {
            colorGrey = y % (squareSize * scaling * 2) == 0;
            for (int x = 0; x < image.getWidth() * scaling; x += squareSize * scaling) {
                if (colorGrey) {
                    g.setColor(Color.lightGray);
                    colorGrey = false;
                } else {
                    g.setColor(Color.WHITE);
                    colorGrey = true;
                }
                int width = squareSize * scaling;
                int height = squareSize * scaling;
                if (x + width > image.getWidth() * scaling) {
                    width = width - (x + width - image.getWidth() * scaling);
                }
                if (y + height > image.getHeight() * scaling) {
                    height = height - (y + height - image.getHeight() * scaling);
                }
                g.fillRect(zeroX + x, zeroY + y, width, height);
            }
        }

    }

    public void addVertex(int x, int y) {
        if (image != null) {
            int zeroX = (getWidth() / 2 - image.getWidth() * scaling / 2);
            int zeroY = (getHeight() / 2 - image.getHeight() * scaling / 2);
            int newX = (x - zeroX) / scaling;
            int newY = (y - zeroY) / scaling;
            if (newX >= 0 && newX <= image.getWidth() && newY >= 0 && newY <= image.getHeight()) {
                debugPoint("Add vertex at", newX, newY);
                polygon.addVertex(new Point(newX, newY));
            }
        }
    }

    public void removeVertex(int x, int y) {
        if (image != null) {
            int zeroX = (getWidth() / 2 - image.getWidth() * scaling / 2);
            int zeroY = (getHeight() / 2 - image.getHeight() * scaling / 2);
            int newX = (x - zeroX) / scaling;
            int newY = (y - zeroY) / scaling;
            if (newX >= 0 && newX <= image.getWidth() && newY >= 0 && newY <= image.getHeight()) {
                for (int i = 0; i < polygon.size(); i++) {
                    Point p = polygon.get(i);
                    if (newX <= p.x + dotRadius && newX >= p.x - dotRadius && newY <= p.y + dotRadius && newY >= p.y - dotRadius) {
                        debugPoint("Remove vertex at", p);
                        polygon.removeVertex(p);
                        break;
                    }
                }
            }
        }
    }

    public void selectPoint(int x, int y) {
        if (image != null) {
            int zeroX = (getWidth() / 2 - image.getWidth() * scaling / 2);
            int zeroY = (getHeight() / 2 - image.getHeight() * scaling / 2);
            int newX = (x - zeroX) / scaling;
            int newY = (y - zeroY) / scaling;
            if (newX >= 0 && newX <= image.getWidth() && newY >= 0 && newY <= image.getHeight()) {
                for (int i = 0; i < polygon.size(); i++) {
                    Point p = polygon.get(i);
                    if (newX <= p.x + dotRadius && newX >= p.x - dotRadius && newY <= p.y + dotRadius && newY >= p.y - dotRadius) {
                        selectedPoint = p;
                        debugPoint("Select point at", p);
                        break;
                    }
                }
            }
        }
    }

    public boolean hasSelectedPoint() {
        return selectedPoint != null;
    }

    public void moveSelectedPoint(int x, int y) {
        if (image != null) {
            int zeroX = (getWidth() / 2 - image.getWidth() * scaling / 2);
            int zeroY = (getHeight() / 2 - image.getHeight() * scaling / 2);
            int newX = (x - zeroX) / scaling;
            int newY = (y - zeroY) / scaling;
            if (newX >= 0 && newX <= image.getWidth() && newY >= 0 && newY <= image.getHeight()) {
                if (hasSelectedPoint()) {
                    Point p = selectedPoint;
                    p.x = newX;
                    p.y = newY;
                }
            }
        }
    }

    public void removeSelection() {
        if (hasSelectedPoint()) {
            debugPoint("Remove selection from point", selectedPoint);
            selectedPoint = null;
        }
    }

    public void removeLastVertex() {
        polygon.removeLastVertex();
    }

    public boolean isPolygon() {
        return polygon.isPolygon();
    }

    public void export(File fileToSave) throws IOException {
        FileWriter myWriter = new FileWriter(fileToSave);
        StringBuilder sb = new StringBuilder();
        sb.append("Values differ from 0 to 1 where 1 equals width/height depending on x or y axis beginning with 0/0 on left down corner.\n");
        sb.append("File: ");
        sb.append(sourceFile.getAbsolutePath());
        sb.append("\n");
        sb.append(getJSONArrayAsString());
        myWriter.write(sb.toString());
        myWriter.close();
    }

    public String getJSONArrayAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < polygon.size(); i++) {
            Point p = polygon.get(i);
            float x = (float) p.x / image.getWidth();
            float y = (float) (image.getHeight() - p.y) / image.getHeight();
            sb.append(x);
            sb.append("f");
            sb.append(",");
            sb.append(y);
            sb.append("f");
            if (i != polygon.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private void debugPoint(String message, Point p) {
        this.debugPoint(message, p.x, p.y);
    }

    private void debugPoint(String message, int px, int py) {
        float x = (float) px / image.getWidth();
        float y = (float) (image.getHeight() - py) / image.getHeight();
        LOGGER.debug(message + " [x:" + x + "|y:" + y + "]");
    }

}
