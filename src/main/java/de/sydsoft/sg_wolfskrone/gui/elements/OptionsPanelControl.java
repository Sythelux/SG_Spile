/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.gui.elements;

import com.jme3.audio.AudioNode;
import com.jme3.system.AppSettings;
import de.sydsoft.sg_wolfskrone.gui.GameClient;

/**
 *
 * @author sythelux
 */
public class OptionsPanelControl{

    GameClient g;
    
    public void init(){
        //graphics
        AppSettings settings = new AppSettings(true);
        settings.getRenderer();//OpenGL1-3
        settings.getBitsPerPixel();//24bpp,16bpp
        settings.getFrameRate();//-1,57-75Hz
        settings.isFullscreen();//yes,no
        settings.getHeight();settings.getWidth();
        settings.getSamples();//1,2,4,6,8,16,32
        settings.isVSync();//yes,no
        settings.getFrequency();
        settings.getDepthBits();//32,24!,16
        settings.getStencilBits();//0,8
        //sound
        settings.getAudioRenderer();//default or disable
        settings.useStereo3D();
        AudioNode audioRootNode;
        AudioNode musicNode;
        AudioNode soundNode;
        AudioNode speechNode;
        AudioNode backgroundNode;
        //inputSettings
        settings.useInput();
        settings.useJoysticks();
    }
}
