package de.sydsoft.sg_wolfskrone.gui.screens;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.sydsoft.sg_wolfskrone.appstates.ClientMainAppState;

public class IntroScreen extends MainScreenController{

//    private Thread t;
//    private boolean running = true;
//    private long runTime = 0;
    private Element sydsoftLogo;
    private Element wolfskroneLogo;

    public IntroScreen(ClientMainAppState state) {
        super(state);
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        sydsoftLogo = screen.findElementByName("SydsoftLogo");
//        t = new Thread(this);
//        t.run();
    }

    public void toWolfskroneIntro() {
        sydsoftLogo.hideWithoutEffect();
        nifty.gotoScreen("startWolfskrone");
        this.bind(nifty, nifty.getScreen("startWolfskrone"));
        wolfskroneLogo = screen.findElementByName("WolfskroneLogo");
    }

    public void toLoginScreen() {
        wolfskroneLogo.hideWithoutEffect();
        nifty.gotoScreen("login");
//        running = false;
        state.setCompleted(true);
    }

    public String getCurrentScreenID() {
        return this.screen.getScreenId();
    }

    @Override
    public void onStartScreen() {
        super.onStartScreen();
        if (nifty.getCurrentScreen().getScreenId().equals("startSydSoft")) {
            toWolfskroneIntro();
            return;
        }
        if (nifty.getCurrentScreen().getScreenId().equals("startSpile")) {
            toLoginScreen();
            return;
        }
    }
    
    public void run() {
        try {
//            while (running) {
//                synchronized (this.t) {
//                    runTime++;
//                    switch ((int) runTime) {
//                        case 100://Sydsoft logo einblenden
//           //                 sydsoftLogo.startEffect(EffectEventId.onCustom, null, "fadeInSydsoft");
//                            break;
//                        case 4000://sydsoft logo ausblenden
//             //               sydsoftLogo.startEffect(EffectEventId.onCustom, null, "fadeOutSydsoft");
//                            break;
//                        case 5000://wolfskroneLogo einblenden
//                            toWolfskroneIntro();
//            //                wolfskroneLogo.startEffect(EffectEventId.onCustom, null, "fadeInWolfskrone");
//                            break;
//                        case 9000://wolfskronelogo ausblenden
//           //                 wolfskroneLogo.startEffect(EffectEventId.onCustom, null, "fadeOutWolfskrone");
//                            break;
//                        case 10000://zu loginscreen wechseln
//                            toLoginScreen();
//                            break;
//                        default:
//                            break;
//                    }
//                    t.wait(10);
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setRunTime(long runTime) {
        //this.runTime = runTime;
    }
}
