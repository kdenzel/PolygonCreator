/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kswmd.polygoncreator;

import de.kswmd.polygoncreator.poly.Polygon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author kai
 */
public class DecoratorPanel extends JPanel {

    private BufferedImage image;
    private Polygon polygon = new Polygon();
    private int dotRadius = 2;
    private int scaling;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setImage(File file) throws IOException {
        image = ImageIO.read(file);
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
            g.drawImage(image, zeroX, zeroY, image.getWidth() * scaling, image.getHeight() * scaling, null);
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
                    //g.drawString(String.valueOf(i+1), p.x, p.y);
                }
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
                polygon.addVertex(new Point(newX, newY));
            }
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
        sb.append("{");
        for(int i = 0; i < polygon.size(); i++){
            Point p = polygon.get(i);
            float x = (float)p.x / image.getWidth();
            float y = (float)(image.getHeight()-p.y) / image.getHeight();
            sb.append(x);
            sb.append("f");
            sb.append(",");
            sb.append(y);
            sb.append("f");
            if(i != polygon.size() - 1){
                sb.append(",");
            }
        }
        sb.append("}");
        myWriter.write(sb.toString());
        myWriter.close();
    }

}
