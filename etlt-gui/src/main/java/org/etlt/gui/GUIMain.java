package org.etlt.gui;

import org.etlt.EtltRuntimeException;
import org.etlt.gui.bounds.BoundsManager;
import org.etlt.gui.bounds.SettingReader;
import org.etlt.gui.menu.ItemConfig;
import org.etlt.gui.menu.MenuConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GUIMain {
    public static void main(String[] args) {
        GUIMain mainFrame = new GUIMain();
        mainFrame.initMenu();
        mainFrame.initWelcomePanel();
        mainFrame.show();
    }

    private JFrame mainFrame;

    public GUIMain(){
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Etlv Kits");
    }

    protected void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        this.mainFrame.setJMenuBar(menuBar);
        //-- first menu group, it's necessary
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener((event) -> {
            System.exit(0);
        });
        fileMenu.add(exitMenu);
        try {
            SettingReader reader = new SettingReader();
            java.util.List<MenuConfig> menuConfig = reader.readList(getClass().getClassLoader().getResourceAsStream("menu.json"), MenuConfig.class);

            menuConfig.forEach(config->{
                /**
                 * add this menu group to menu bar
                 */
                JMenu menu = new JMenu(config.getName());
                menuBar.add(menu);
                /**
                 * add all menu items to this group
                 */
                List<ItemConfig> itemConfigs = config.getItems();
                itemConfigs.forEach(itemConfig -> {
                    JMenuItem item = new JMenuItem(itemConfig.getName());
                    item.addActionListener(e -> {
                        try {
                            JPanel panel = (JPanel) Class.forName(itemConfig.getPanel()).newInstance();
                            mainFrame.setContentPane(panel);
                            SwingUtilities.updateComponentTreeUI(mainFrame);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    });
                    menu.add(item);
                });

            });
        }catch (IOException e){
            throw new EtltRuntimeException(e);
        }
    }

    protected void initWelcomePanel(){
        JPanel welcomePanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                try {
                    Image image = ImageIO.read(getClass().getResourceAsStream("/image/welcome.jfif"));
                    g.drawImage(image, 0, 0, image.getWidth(this),
                            image.getHeight(this), mainFrame);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mainFrame.setContentPane(welcomePanel);
    }

    public void show() {
        Dimension dimension = new Dimension(300, 200);
        mainFrame.setSize(dimension);
        mainFrame.setLocation(BoundsManager.getComponentAnchor(dimension));
        mainFrame.setVisible(true);
    }

}
