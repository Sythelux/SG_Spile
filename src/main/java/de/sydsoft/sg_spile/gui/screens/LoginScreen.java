package de.sydsoft.sg_spile.gui.screens;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.sydsoft.libst.util.Console;
import de.sydsoft.sg_spile.appstates.ClientMainAppState;
import de.sydsoft.sg_spile.entities.Player;
import de.sydsoft.sg_spile.logic.ServerRequestMessage;

public class LoginScreen extends MainScreenController {

    public static final String SCREENNAME = "login";
    private Element loginPopUp;
//    private Element changeCharacterButton;
    private Element actualCharacterText;
//    private Element actualCharacterIcon;
    private Element[] notifyButtons;
    private Element playerName;
    private Element passWord;
    private Element playerNotFound;
    private boolean initialized = false;
    private boolean playerShown = false;

    public LoginScreen(ClientMainAppState state) {
        super(state);
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        loginPopUp = nifty.createPopup("loginPopup");
//        changeCharacterButton = getElementAtCurrentScreen("changeCharacterButton");
        actualCharacterText = getElementAtCurrentScreen("ActualCharacterText");
//        actualCharacterIcon = getElementAtCurrentScreen("ActualCharacterIcon");
        notifyButtons = new Element[7];
        for (int i = 0; i < notifyButtons.length; i++) {
            notifyButtons[i] = getElementAtCurrentScreen("NotifyButton" + (i + 1));
        }
        initialized = true;
    }

    @Override
    public void onStartScreen() {
        super.onStartScreen();
    }

    @Override
    public void onEndScreen() {
        super.onEndScreen();
    }

    public void showPlayer() {
        if (state.getGame().getActualCharacter() == null) {
            nifty.showPopup(nifty.getCurrentScreen(), loginPopUp.getId(), null);
            playerName = getPopUpElement("userName", loginPopUp);
            passWord = getPopUpElement("passWord", loginPopUp);
            playerNotFound = getPopUpElement("NoAccount", loginPopUp);
            playerShown = true;
        }
    }

    public void login(Player player) {
        //download player
        if (player == null) {
            playerNotFound.setVisible(true);
        } else {
            playerNotFound.setVisible(false);
            state.getGame().setPlayer(player);
            actualCharacterText.getRenderer(TextRenderer.class).setText(player.getActualCharacter().toLoginScreenText());
            nifty.closePopup(loginPopUp.getId());
        }
    }

    public void joinGame() {
        state.setCompleted(true);
        nifty.gotoScreen("loading");
    }

    public void quit() {
        //nifty.gotoScreen("end");
    }

    @Override
    public void buttonClicked(String guiElementID) {
        super.buttonClicked(guiElementID);
        switch (guiElementID) {
            case "loginButton":
                sendAccount();
                break;
            case "changeCharacterButton":
                break;
            case "actualCharacter":
                joinGame();
                break;
            case "LogoutButton":
                break;
            case "StatsButton":
                break;
        }
    }

    public void sendAccount() {
        String playerNameS = ((TextField) getPopUpElementController(playerName.getId(), TextField.class, loginPopUp)).getText();
        String passwordS = ((TextField) getPopUpElementController(passWord.getId(), TextField.class, loginPopUp)).getText();
        StringBuilder passwordHash = new StringBuilder();
        String algorithm = "SHA";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            Console.errMsg(ex);
        }
        byte[] digest = md.digest(passwordS.getBytes());
        for (byte b : digest) {
            passwordHash.append(b);
        }
        state.getGame().getClient().send(new ServerRequestMessage(0, new String[]{playerNameS, passwordHash.toString()}));
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isPlayerShown() {
        return playerShown;
    }
}
