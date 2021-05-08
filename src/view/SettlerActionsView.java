package view;

import Exceptions.ActionFailedException;
import control.Game;
import map.asteroid.Asteroid;
import map.entity.Settler;
import map.resource.Resource;

import javax.swing.*;
import java.awt.*;

public class SettlerActionsView extends JPanel {
    private final JButton placeTeleportBtn;
    private Settler settler;

    SettlerActionsView() {
        JButton moveBtn = new JButton("Move");
        moveBtn.addActionListener(actionEvent -> {
            // todo: remove testing code (should be settler.getAsteroid...)
            NeighbourChooser chooser = new NeighbourChooser(Game.getInstance().getCurrentSettler().getAsteroid().getNeighbours());
            Asteroid chosen = chooser.chooseAsteroid();
            if (chosen != null)
                settler.move(chosen);
        });

        JButton drillBtn = new JButton("Drill");
        drillBtn.addActionListener(actionEvent -> {
            try {
                settler.drill();
            } catch (ActionFailedException e) {
                JOptionPane.showMessageDialog(GameWindow.getInstance(), e.getMessage());
            }
        });

        JButton mineBtn = new JButton("Mine");
        mineBtn.addActionListener(actionEvent -> {
            try {
                settler.mine();
            } catch (ActionFailedException e) {
                JOptionPane.showMessageDialog(GameWindow.getInstance(), e.getMessage());
            }
        });

        this.placeTeleportBtn = new JButton("Place Teleport");
        placeTeleportBtn.addActionListener(actionListener -> settler.placeTeleport());

        JButton buildRobotBtn = new JButton("Build Robot");
        buildRobotBtn.addActionListener(actionListener -> {
            try {
                settler.buildRobot();
            } catch (ActionFailedException e) {
                JOptionPane.showMessageDialog(GameWindow.getInstance(), e.getMessage());
            }
        });

        JButton buildTeleportBtn = new JButton("Build Teleport");
        buildTeleportBtn.addActionListener(actionEvent -> {
            try {
                settler.buildTeleport();
            } catch (ActionFailedException e) {
                JOptionPane.showMessageDialog(GameWindow.getInstance(), e.getMessage());
            }
        });

        JButton placeBtn = new JButton("Place back resource");
        placeBtn.addActionListener(actionEvent -> {
            try {
                if (settler.getResources().isEmpty()) {
                    JOptionPane.showMessageDialog(GameWindow.getInstance(), "There is no resource in the inventory, nothing to place back!");
                    return;
                }
                ResourceChooser rc = new ResourceChooser(settler.getResources());
                Resource res = rc.chooseResource();
                if (res != null)
                    settler.placeBack(res);
            } catch (ActionFailedException e) {
                JOptionPane.showMessageDialog(GameWindow.getInstance(), e.getMessage());
            }
        });

        add(moveBtn);
        add(drillBtn);
        add(mineBtn);
        add(placeBtn);
        add(buildTeleportBtn);
        add(placeTeleportBtn);
        add(buildRobotBtn);
    }


    public void updateView(Settler settler) {
        this.settler = settler;

        // if the settler doesn't have at least one teleport gate or the asteroid he's standing on already has a
        // teleport, the place teleport button is disabled
        if (settler.getTeleportNumber() == 0 || settler.getAsteroid().getTeleportGate() != null) {
            this.placeTeleportBtn.setEnabled(false);
        }
    }
}
