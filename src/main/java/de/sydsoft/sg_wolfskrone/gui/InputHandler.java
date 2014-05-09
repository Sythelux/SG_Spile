/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.gui;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.system.AppSettings;
import de.sydsoft.libst.util.Console;
import de.sydsoft.libst.util.Constants;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.sydsoft.sg_wolfskrone.entities.Player;

/**
 *
 * @author sythelux
 */
public class InputHandler {

    private InputManager inputManager;
    private Player player;
    private Nifty nifty;
    private AppSettings settings;
    private final String ctrlS = "ctrl";
    private final String lcS = "leftclick";
    private final String rcS = "rightclick";
    private final String aheadS = "ahead";
    private final String aheadLockS = "aheadLock";
    private final String backS = "back";
    private final String leftS = "left";
    private final String rightS = "right";
    private final String jumpS = "jump";
    private final String debugS = "debug";
    private final String mmlS = "mouseMoveLeft";
    private final String mmrS = "mouseMoveRight";
    private final String mmuS = "mouseMoveUp";
    private final String mmdS = "mouseMoveDown";
    public boolean ahead, back, left, right, jump, aheadLock, leftC, rightC;
    public float mouseAxeLeft, mouseAxeRight, mouseAxeUp, mouseAxeDown;

    public InputHandler(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public InputHandler(InputManager inputManager, Player player, Nifty nifty, AppSettings settings) {
        this.inputManager = inputManager;
        this.player = player;
        this.nifty = nifty;
        this.settings = settings;
    }

    public void initialize() {
//        inputManager.addMapping(ctrlS, new KeyTrigger(KeyInput.KEY_LCONTROL));
//        inputManager.addListener(ctrlH, ctrlS);
        inputManager.addMapping(leftS, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addListener(leftH, leftS);
        inputManager.addMapping(rightS, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(rightH, rightS);
        inputManager.addMapping(aheadS, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addListener(aheadH, aheadS);
        inputManager.addMapping(backS, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(downH, backS);
        inputManager.addMapping(jumpS, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(jumpH, jumpS);
        inputManager.addMapping(debugS, new KeyTrigger(KeyInput.KEY_F5));
        inputManager.addListener(debugH, debugS);
        inputManager.addMapping(aheadLockS, new KeyTrigger(KeyInput.KEY_CAPITAL));
        inputManager.addListener(aheadLockH, aheadLockS);

        inputManager.addMapping(lcS, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(lcH, lcS);
        inputManager.addMapping(rcS, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(rcH, rcS);

        inputManager.addMapping(mmlS, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addListener(mmlH, mmlS);
        inputManager.addMapping(mmrS, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addListener(mmrH, mmrS);
        inputManager.addMapping(mmuS, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addListener(mmuH, mmuS);
        inputManager.addMapping(mmdS, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addListener(mmdH, mmdS);
        if (settings == null) {
            settings = new AppSettings(true);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Key- and MouseListener">
    private ActionListener ctrlH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (inputManager.isCursorVisible() && keyPressed) {
                inputManager.setCursorVisible(false);
            } else {
                inputManager.setCursorVisible(true);
            }
        }
    };
    private ActionListener aheadH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                ahead = true;
            } else {
                ahead = false;
                aheadLock = false;
            }
        }
    };
    private ActionListener downH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                back = true;
            } else {
                back = false;
            }
        }
    };
    private ActionListener leftH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                left = true;
            } else {
                left = false;
            }
        }
    };
    private ActionListener rightH = new ActionListener() {

        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                right = true;
            } else {
                right = false;
            }
        }
    };
    private ActionListener jumpH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                jump = true;
            } else {
                jump = false;
            }
        }
    };
    private ActionListener debugH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                if (Constants.DEBUG) {
                    Constants.DEBUG = false;
                    nifty.setDebugOptionPanelColors(false);
                } else {
                    Constants.DEBUG = true;
                    nifty.setDebugOptionPanelColors(true);
                }
            }
        }
    };
    private ActionListener aheadLockH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                if (aheadLock) {
                    aheadLock = false;
                } else {
                    aheadLock = true;
                }
            }
        }
    };
    private ActionListener lcH = new ActionListener() {

        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                leftC = true;
                inputManager.setCursorVisible(false);
            } else {
                leftC = false;
                if (!rightC) {
                    inputManager.setCursorVisible(true);
                }
            }
        }
    };
    private ActionListener rcH = new ActionListener() {

        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                rightC = true;
                inputManager.setCursorVisible(false);
            } else {
                rightC = false;
                if (!leftC) {
                    inputManager.setCursorVisible(true);
                }
            }
        }
    };
    private AnalogListener mmlH = new AnalogListener() {

        @Override
        public void onAnalog(String name, float value, float tpf) {
            mouseAxeLeft = value;
        }
    };
    private AnalogListener mmrH = new AnalogListener() {

        @Override
        public void onAnalog(String name, float value, float tpf) {
            mouseAxeRight = value;
        }
    };
    private AnalogListener mmuH = new AnalogListener() {

        @Override
        public void onAnalog(String name, float value, float tpf) {
            mouseAxeUp = value;
        }
    };
    private AnalogListener mmdH = new AnalogListener() {

        @Override
        public void onAnalog(String name, float value, float tpf) {
            mouseAxeDown = value;
        }
    };
    // </editor-fold>

    // <editor-fold desc="Nifty Eventhandler">
    @NiftyEventSubscriber(id = "VSyncCB")
    public void onVSynchCheckBoxChanged(final String id, final CheckBoxStateChangedEvent event) {
        settings.setVSync(event.isChecked());
    }

    @NiftyEventSubscriber(id = "OptionsButton")
    public void onOptionsButtonClicked(final String id, final ButtonClickedEvent event) {
        Console.dbgMsg("onOptionsButtonClicked");
    }
    // </editor-fold>
}
