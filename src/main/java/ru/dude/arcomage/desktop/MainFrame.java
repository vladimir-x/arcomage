/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.desktop;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.Settings;
import com.badlogic.gdx.backends.lwjgl.LwjglFrame;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

/**
 *
 * @author elduderino
 */
public class MainFrame extends LwjglFrame {

    private static MainFrame instance;

    public static MainFrame getInstance(){
        return instance;
    }

    /**
     * Creates new form MainFrame
     *
     * @param listener
     * @param settings
     */
    AppImpl appImpl;
    
    public MainFrame(AppImpl appImpl, Settings settings) {
        super(appImpl, "", settings.windowWidth, settings.windowHeight);
        this.appImpl = appImpl;
        initComponents();
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setLocationRelativeTo(null);
        instance = this;
    }

    public void showMessageDialog(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainMenuBar = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        startItem = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JPopupMenu.Separator();
        optionItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Arcomage");
        setResizable(false);

        gameMenu.setText("Игра");

        startItem.setText("Новая");
        startItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startItemActionPerformed(evt);
            }
        });
        gameMenu.add(startItem);
        gameMenu.add(separator1);

        optionItem.setText("Настройки");
        optionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionItemActionPerformed(evt);
            }
        });
        gameMenu.add(optionItem);

        exitItem.setText("Выход");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        gameMenu.add(exitItem);

        mainMenuBar.add(gameMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitItemActionPerformed

    private void optionItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionItemActionPerformed
        OptionDialog od = new OptionDialog(null, true);
        od.setLocationRelativeTo(null);
        od.setVisible(true);
    }//GEN-LAST:event_optionItemActionPerformed

    private void startItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startItemActionPerformed
        if (!appImpl.isGameStart()) {
            appImpl.restart();
        } else {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Начать новую игру?");
            if (dialogResult == JOptionPane.YES_OPTION) {
                appImpl.restart();
            }
        }
    }//GEN-LAST:event_startItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JMenuItem optionItem;
    private javax.swing.JPopupMenu.Separator separator1;
    private javax.swing.JMenuItem startItem;
    // End of variables declaration//GEN-END:variables
}
