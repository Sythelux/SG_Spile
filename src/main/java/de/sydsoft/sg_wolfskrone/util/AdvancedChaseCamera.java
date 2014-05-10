/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.util;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 *
 * @author sythelux
 */
public class AdvancedChaseCamera extends ChaseCamera {

    protected final static String ChaseCamBtMoveLeft = "ChaseCamBtMoveLeft";
    protected final static String ChaseCamBtMoveRight = "ChaseCamBtMoveRight";
    private String[] inputs = {ChaseCamBtMoveLeft, ChaseCamBtMoveRight};

    public AdvancedChaseCamera(Camera cam) {
        super(cam);
    }

    public AdvancedChaseCamera(Camera cam, InputManager inputManager) {
        super(cam, inputManager);
        inputManager.addMapping(ChaseCamBtMoveLeft, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(ChaseCamBtMoveRight, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, inputs);
    }

    public AdvancedChaseCamera(Camera cam, Spatial target) {
        super(cam, target);
    }

    public AdvancedChaseCamera(Camera cam, Spatial target, InputManager inputManager) {
        super(cam, target, inputManager);
        inputManager.addMapping(ChaseCamBtMoveLeft, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(ChaseCamBtMoveRight, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, inputs);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        super.onAnalog(name, value, tpf);
        switch (name) {
            case ChaseCamBtMoveLeft:
                if (inputManager.isCursorVisible()) {
                    canRotate = true;
                    rotateCamera(-value);
                    canRotate = false;
                }
                break;
            case ChaseCamBtMoveRight:
                if (inputManager.isCursorVisible()) {
                    canRotate = true;
                    rotateCamera(value);
                    canRotate = false;
                }
                break;
        }
    }

    @Override
    protected void rotateCamera(float value) {
        super.rotateCamera(value);
    }
}
