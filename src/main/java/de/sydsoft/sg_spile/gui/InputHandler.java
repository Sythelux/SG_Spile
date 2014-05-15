/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.gui;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.screen.ScreenController;
import de.sydsoft.libst.util.Console;
import de.sydsoft.libst.util.Constants;
import de.sydsoft.sg_spile.entities.Player;
import de.sydsoft.sg_spile.gui.screens.IngameScreen;

/**
 *
 * @author sythelux
 */
//TODO: switch between right/left strife(while right mousebutton is pressed) and right/left rotate
public class InputHandler {

    private InputManager inputManager;
//    private Player player;
    private Nifty nifty;
    private AppSettings settings;
    public static final String ControlKeyString = "ctrl";
    public static final String ReturnKeyString = "returnKey";
    public static final String LeftClickString = "leftclick";
    public static final String RightClickString = "rightclick";
    public static final String AheadString = "ahead";
    public static final String AheadLockString = "aheadLock";
    public static final String BackString = "back";
    public static final String LeftString = "left";
    public static final String RightString = "right";
    public static final String JumpString = "jump";
    public static final String DebugString = "debug";
    public static final String MouseMoveLeftString = "mouseMoveLeft";
    public static final String MouseMoveRightString = "mouseMoveRight";
    public static final String MouseMoveUpString = "mouseMoveUp";
    public static final String MouseMoveDownString = "mouseMoveDown";
    public boolean ahead, back, left, right, jump, aheadLock, leftC, rightC;
    public float mouseAxeLeft, mouseAxeRight, mouseAxeUp, mouseAxeDown;

    public InputHandler(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public InputHandler(InputManager inputManager, Player player, Nifty nifty, AppSettings settings) {
        this.inputManager = inputManager;
//        this.player = player;
        this.nifty = nifty;
        this.settings = settings;
    }

    public void initialize() {
        inputManager.addMapping(LeftString, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(RightString, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(AheadString, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(BackString, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(JumpString, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping(DebugString, new KeyTrigger(KeyInput.KEY_F5));
        inputManager.addMapping(AheadLockString, new KeyTrigger(KeyInput.KEY_CAPITAL));
        inputManager.addMapping(ReturnKeyString, new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(actionListener, LeftString, RightString, AheadString, BackString, JumpString, DebugString, AheadLockString, ReturnKeyString);

        inputManager.addMapping(LeftClickString, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping(RightClickString, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(actionListener, LeftClickString, RightClickString);

        inputManager.addMapping(MouseMoveLeftString, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(MouseMoveRightString, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(MouseMoveUpString, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(MouseMoveDownString, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addListener(analogListener, MouseMoveLeftString, MouseMoveRightString, MouseMoveUpString, MouseMoveDownString);
        if (settings == null) {
            settings = new AppSettings(true);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Key- and MouseListener">
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                switch (name) {
                    case LeftString:
                        left = true;
                        break;
                    case RightString:
                        right = true;
                        break;
                    case AheadString:
                        ahead = true;
                        break;
                    case BackString:
                        back = true;
                        break;
                    case JumpString:
                        jump = true;
                        break;
                    case DebugString:
                        if (Constants.DEBUG) {
                            Constants.DEBUG = false;
                            nifty.setDebugOptionPanelColors(false);
                        } else {
                            Constants.DEBUG = true;
                            nifty.setDebugOptionPanelColors(true);
                        }
                        break;
                    case AheadLockString:
                        if (aheadLock) {
                            aheadLock = false;
                        } else {
                            aheadLock = true;
                        }
                        break;
                    case LeftClickString:
                        leftC = true;
                        inputManager.setCursorVisible(false);
                        break;
                    case RightClickString:
                        rightC = true;
                        inputManager.setCursorVisible(false);
                        break;
                    case ReturnKeyString:
                        ScreenController sc = nifty.findScreenController(IngameScreen.class.getName());
                        if (sc instanceof IngameScreen) {
                            IngameScreen is = (IngameScreen) nifty.findScreenController(IngameScreen.class.getName());
                            is.keyPressed(ReturnKeyString);
                        }
                        break;
                }
            } else {
                switch (name) {
                    case LeftString:
                        left = false;
                        break;
                    case RightString:
                        right = false;
                        break;
                    case AheadString:
                        ahead = false;
                        aheadLock = false;
                        break;
                    case BackString:
                        back = false;
                        break;
                    case JumpString:
                        jump = false;
                        break;
                    case DebugString://nothing in else
                        break;
                    case AheadLockString://nothing in else
                        break;
                    case LeftClickString:
                        leftC = false;
                        if (!rightC) {
                            inputManager.setCursorVisible(true);
                        }
                        break;
                    case RightClickString:
                        rightC = false;
                        if (!leftC) {
                            inputManager.setCursorVisible(true);
                        }
                        break;
                    case ReturnKeyString://nothing in else
                        break;
                }
            }
        }
    };
    private AnalogListener analogListener = new AnalogListener() {

        @Override
        public void onAnalog(String name, float value, float tpf) {
            switch (name) {
                case MouseMoveLeftString:
                    mouseAxeLeft = value;
                    break;
                case MouseMoveRightString:
                    mouseAxeRight = value;
                    break;
                case MouseMoveUpString:
                    mouseAxeUp = value;
                    break;
                case MouseMoveDownString:
                    mouseAxeDown = value;
                    break;
            }
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
