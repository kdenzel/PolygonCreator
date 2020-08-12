/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kswmd.polygoncreator;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 *
 * @author kai
 */
public class Main extends javax.swing.JFrame {

    private static final Logger LOGGER = LogManager.getLogger();
    private JFileChooser imgFileChooser;
    private JFileChooser exportPolygonChooser;
    private StringBuilder helpMessage;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        imgFileChooser = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes());
        imgFileChooser.setFileFilter(imageFilter);

        exportPolygonChooser = new JFileChooser();
        StringBuilder sb = new StringBuilder();
        sb.append("Left Click: Set vertex.\n");
        sb.append("Left Click (on vertex pressed): Drag vertex.\n");
        sb.append("Right Click (on vertex): Delete vertex.\n");
        sb.append("Scroll: zoom in and out");
        helpMessage = sb;
        LOGGER.debug("Components initialized.");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        decoratorPanel = new de.kswmd.polygoncreator.DecoratorPanel();
        jMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        infoMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        decoratorPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                decoratorPanelMouseDragged(evt);
            }
        });
        decoratorPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                decoratorPanelMouseWheelMoved(evt);
            }
        });
        decoratorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                decoratorPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                decoratorPanelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout decoratorPanelLayout = new javax.swing.GroupLayout(decoratorPanel);
        decoratorPanel.setLayout(decoratorPanelLayout);
        decoratorPanelLayout.setHorizontalGroup(
            decoratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        decoratorPanelLayout.setVerticalGroup(
            decoratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        jScrollPane.setViewportView(decoratorPanel);

        fileMenu.setText("File");

        openMenuItem.setText("Open");
        openMenuItem.setToolTipText("");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        exportMenuItem.setText("Export Polygon");
        exportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exportMenuItem);

        jMenuBar.add(fileMenu);

        helpMenu.setText("Help");

        infoMenuItem.setText("Info");
        infoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(infoMenuItem);

        jMenuBar.add(helpMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        LOGGER.debug("Open filechooser.");
        int r = imgFileChooser.showOpenDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = imgFileChooser.getSelectedFile();
                decoratorPanel.setImage(selectedFile);
                jScrollPane.setPreferredSize(decoratorPanel.getSize());
                this.pack();
                this.setLocationRelativeTo(null);
                LOGGER.debug("Chosen file: " + selectedFile.getAbsolutePath());
            } catch (IOException ex) {
                LOGGER.warn("Couldn't convert file to buffered image", ex);
                JOptionPane.showMessageDialog(this, "Something is wrong with the file. " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void decoratorPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_decoratorPanelMouseReleased
        if (!decoratorPanel.hasSelectedPoint()) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                decoratorPanel.addVertex(evt.getX(), evt.getY());
            } else if (evt.getButton() == MouseEvent.BUTTON3) {
                decoratorPanel.removeVertex(evt.getX(), evt.getY());
            }
        } else {
            decoratorPanel.removeSelection();
        }
        decoratorPanel.repaint();
    }//GEN-LAST:event_decoratorPanelMouseReleased

    private void decoratorPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_decoratorPanelMouseWheelMoved
        if (evt.getWheelRotation() < 0) {
            decoratorPanel.scaleUp();
        } else {
            decoratorPanel.scaleDown();
        }
        decoratorPanel.repaint();
    }//GEN-LAST:event_decoratorPanelMouseWheelMoved

    private void exportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuItemActionPerformed
        if (decoratorPanel.isPolygon()) {
            boolean canceled;
            do {
                canceled = false;
                int userSelection = exportPolygonChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = exportPolygonChooser.getSelectedFile();
                    LOGGER.debug("Save as file: " + fileToSave.getAbsolutePath());
                    if (!fileToSave.exists()) {
                        try {
                            fileToSave.createNewFile();
                            decoratorPanel.export(fileToSave);
                        } catch (IOException ex) {
                            LOGGER.error("Something went wrong in saving file " + fileToSave.getAbsolutePath(), ex);
                            JOptionPane.showMessageDialog(this, "Couldn't save file " + fileToSave.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        int result = JOptionPane.showConfirmDialog(this, "Are you sure you wan't to override this file?");
                        if (result == JOptionPane.OK_OPTION) {
                            LOGGER.debug("Override.");
                            try {
                                decoratorPanel.export(fileToSave);
                            } catch (IOException ex) {
                                LOGGER.error("Something went wrong in writing to file " + fileToSave.getAbsolutePath(), ex);
                                JOptionPane.showMessageDialog(this, "Couldn't write to file " + fileToSave.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            LOGGER.debug("Override canceled.");
                            canceled = true;
                        }
                    }
                }
            }while(canceled);
        
        } else {
            LOGGER.debug("Couldn't export the polygon. Maybe no image loaded or not enough vertices? Need at least 3 vertices.");
            JOptionPane.showMessageDialog(this, "You need at least 3 Vertices.", "Not enough vertices", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_exportMenuItemActionPerformed

    private void decoratorPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_decoratorPanelMousePressed
        if (evt.getButton() == MouseEvent.BUTTON1) {
            decoratorPanel.selectPoint(evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_decoratorPanelMousePressed

    private void decoratorPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_decoratorPanelMouseDragged
        if (decoratorPanel.hasSelectedPoint()) {
            decoratorPanel.moveSelectedPoint(evt.getX(), evt.getY());
            decoratorPanel.repaint();
        }
    }//GEN-LAST:event_decoratorPanelMouseDragged

    private void infoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, helpMessage.toString(), "Help", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_infoMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        LOGGER.debug("BYE.");
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        boolean debug = Arrays.stream(args).anyMatch(s -> {
            return s.equals("-v") || s.equals("-verbose");
        });
        if (debug) {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration config = context.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig("de.kswmd");
            loggerConfig.setLevel(Level.DEBUG);
            context.updateLoggers();
            LOGGER.debug("Arguments: " + Arrays.toString(args));
        } else {
            LOGGER.info("Use -v or -verbose to print debug output.");
        }

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.kswmd.polygoncreator.DecoratorPanel decoratorPanel;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem infoMenuItem;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JMenuItem openMenuItem;
    // End of variables declaration//GEN-END:variables
}
