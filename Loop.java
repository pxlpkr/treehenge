import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Loop implements ActionListener {
    private Timer timer;

    public long aOther = 0;
    public long aPhysics = 0;

    public Loop() {
        this.timer = new Timer(1000/60, this);
        this.timer.setInitialDelay(1);
    }

    public void start() {
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        this.tick();
    }

    public void tick()
    {
        long tStart = System.nanoTime();

        int i = (int) Application.getRenderer().getCamera().getX()+49;
        int j = (int) Application.getRenderer().getCamera().getZ()+49;
        double k = Application.getRenderer().getCamera().getX() % 1;
        if (k < 1) {
            k = 1+k;
        } else {}
        double l = Application.getRenderer().getCamera().getZ() % 1;
        if (l < 1) {
            l = 1+l;
        } else {}
        // try {
        //     double avgH = (
        //         Application.heights[ i ][ j ] * (1-k) * (1-l) +
        //         Application.heights[i+1][ j ] * (k) * (1-l) +
        //         Application.heights[i+1][j+1] * (k) * (l) +
        //         Application.heights[ i ][j+1] * (1-k) * (l)
        //     );
        //     Application.getRenderer().getCamera().setY(avgH-1.8);
        // } catch (IndexOutOfBoundsException e) {
        //     Application.getRenderer().getCamera().moveRelative(0, Application.a, 0);
        //     Application.a += 0.02;
        // }

        double moveSpeed = 0.05;
        if (Application.getHeldKeys().contains(Integer.valueOf(KeyEvent.VK_SHIFT))) {
            moveSpeed *= 0.5;
        }
        if (Application.getHeldKeys().contains(Integer.valueOf(KeyEvent.VK_R))) {
            moveSpeed *= 5;
        }
        int rotateSpeed = 3;
        for (Integer keyValue : Application.getHeldKeys()) {
            keyValue = keyValue.intValue();
            switch (keyValue) {
                case KeyEvent.VK_W:
                    Application.getRenderer().getCamera().moveRelative(0,0,moveSpeed);
                    break;
                case KeyEvent.VK_S:
                    Application.getRenderer().getCamera().moveRelative(0,0,-moveSpeed);
                    break;
                case KeyEvent.VK_D:
                    Application.getRenderer().getCamera().moveRelative(moveSpeed,0,0);
                    break;
                case KeyEvent.VK_A:
                    Application.getRenderer().getCamera().moveRelative(-moveSpeed,0,0);
                    break;
                case KeyEvent.VK_SHIFT:
                    // if (Application.getPhysics().cameraGrounded()) {
                    Application.getRenderer().getCamera().moveRelative(0,2*moveSpeed,0);
                    // };
                    break;
                case KeyEvent.VK_SPACE:
                    // if (Application.getPhysics().cameraGrounded()) {
                    //     Application.getPhysics().modifyCameraVelocity(0,-20,0);
                    // }
                    Application.getRenderer().getCamera().moveRelative(0,-moveSpeed,0);
                    break;
                case KeyEvent.VK_RIGHT:
                    Application.getRenderer().getCamera().rotateYaw(rotateSpeed);
                    break;
                case KeyEvent.VK_LEFT:
                    Application.getRenderer().getCamera().rotateYaw(-rotateSpeed);
                    break;
                case KeyEvent.VK_UP:
                    Application.getRenderer().getCamera().rotatePitch(-rotateSpeed);
                    break;
                case KeyEvent.VK_DOWN:
                    Application.getRenderer().getCamera().rotatePitch(rotateSpeed);
                    break;
            }
        }

        long tOther = System.nanoTime();

        Application.getPhysics().tick();

        long tPhysics = System.nanoTime();

        Application.getFrame().repaint();

        aOther += tOther-tStart;
        aPhysics += tPhysics-tOther;
        aOther /= 2;
        aPhysics /= 2;
    }
}