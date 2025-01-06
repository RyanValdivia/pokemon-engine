package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    @Override
    public void keyTyped (KeyEvent e) {

    }

    @Override
    public void keyPressed (KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            this.isUpPressed = true;
        }
        else if (code == KeyEvent.VK_DOWN) {
            this.isDownPressed = true;
        }
        else if (code == KeyEvent.VK_LEFT) {
            this.isLeftPressed = true;
        }
        else if (code == KeyEvent.VK_RIGHT) {
            this.isRightPressed = true;
        }
    }

    @Override
    public void keyReleased (KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            this.isUpPressed = false;
        }
        else if (code == KeyEvent.VK_DOWN) {
            this.isDownPressed = false;
        }
        else if (code == KeyEvent.VK_LEFT) {
            this.isLeftPressed = false;
        }
        else if (code == KeyEvent.VK_RIGHT) {
            this.isRightPressed = false;
        }
    }

    public boolean isUpPressed () {
        return isUpPressed;
    }

    public boolean isDownPressed () {
        return isDownPressed;
    }

    public boolean isLeftPressed () {
        return isLeftPressed;
    }

    public boolean isRightPressed () {
        return isRightPressed;
    }

    public boolean isNothingPressed () {
        return (!this.isDownPressed && !this.isUpPressed && !this.isLeftPressed && !this.isRightPressed);
    }
}
