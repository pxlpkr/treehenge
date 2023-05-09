import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                Application.close();
                break;
            case KeyEvent.VK_ENTER:
                // System.out.println(Application.getPhysics().getPhysicsCamera().formatStats()+"\n");
                System.out.println(Application.getRenderer().getCamera().formatStats()+"\n");
                break;
            case KeyEvent.VK_F:
                if (Application.getDevice().getFullScreenWindow() == null) {
                    Application.getDevice().setFullScreenWindow(Application.getFrame());
                    Application.getRenderer().getCamera().setDim(Application.SCREEN_SIZE);
                    System.out.println(Application.getRenderer().getCamera().getDim()[0]);
                } else {
                    Application.getDevice().setFullScreenWindow(null);
                    Application.getRenderer().getCamera().setDim(Application.WINDOW_SIZE);
                }
                break;
            case KeyEvent.VK_P:
                Application.getPhysics().toggle();
                break;
            default:
                Integer o = Integer.valueOf(e.getKeyCode());
                if (!Application.getHeldKeys().contains(o)) {
                    Application.getHeldKeys().add(o);
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            default:
                Application.getHeldKeys().remove(Integer.valueOf(e.getKeyCode()));
                break;
        }
    }
    public void keyTyped(KeyEvent e) {}
}