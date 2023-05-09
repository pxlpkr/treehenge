import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import treehenge.*;
import treehenge.physics.*;

class Application {
    public static final Dimension WINDOW_SIZE = new Dimension(600, 600);
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final boolean DEBUG_FLAG = true;
    public static double a = 0;

    private static List<Integer> heldKeys = new ArrayList<Integer>();
    private static JFrame frame = new JFrame();
    private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private static THRenderer renderer = new THRenderer(WINDOW_SIZE);
    private static THPhysics physics = new THPhysics();
    private static Loop loop;

    public static double[][] heights;
    // private static THPObject player;

    public static void main(String[] args) {
        //Renderer
        Application.getRenderer().getCamera().setRenderDistance(30);

        //Make testing area
        heights = Debug.createHeights();
        Debug.createGroundMesh(heights);

        // THGeometry g = new THGeometry(
        //     new THVector(-4, 4, 10),
        //     new THVector(-4, 0, 10),
        //     new THVector(4, 0, 10)
        // );

        // g.setFillColor(new Color(255, 0 , 0));

        // Application.getRenderer().addGeometry(g);
        // Debug.createAxisDisplay();
        // Application.getRenderer().getCamera().setLocation(50, -15, 50);
        // Application.getRenderer().getCamera().setYaw(225);

        //Physics
        Application.getPhysics().addGlobalForce(new THVector(0, 0.006, 0));
        Application.getPhysics().setGlobalGround(0);

        // THConstruct testConstruct = THConstruct.RectangularPrism(
        //     new THVector(0, 0, 0),
        //     new THVector(0.4, -1.8, 0.4)
        // );
        // testConstruct.setColor(new Color(255,0,0));

        // THPObject testObject = new THPObject(testConstruct);
        // testObject.moveRelative(0, heights[50][50]-2, 0);

        // Application.getRenderer().addGeometry(testConstruct);
        // Application.getPhysics().addObject(testObject);

        //Player Controls

        /*
            Application.player = new THPObject(THConstruct.RectangularPrism(
                new THVector(0, 0, 0),
                new THVector(0.4, 1.8, 0.4)
            ));
            player.linkCamera(Application.getRenderer().getCamera());
            player.offsetCamera(new THVector(0.2, 1.7, 0.2));
            player.updateCamera();
            Application.getPhysics().addObject(player);
        */

        //Frame
        // frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new Keyboard());
        frame.setSize(WINDOW_SIZE);
        frame.setResizable(false);
        frame.setContentPane(new DrawSurface());
        frame.setVisible(true);

        //Loop
        Application.loop = new Loop();
        Application.loop.start();
        Application.loop.tick();
    }

    public static void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static GraphicsDevice getDevice() {return device;}
    public static THRenderer getRenderer() {return renderer;}
    public static JFrame getFrame() {return frame;}
    public static THPhysics getPhysics() {return physics;}
    public static List<Integer> getHeldKeys() {return heldKeys;}
    public static Loop getLoop() {return loop;}
}