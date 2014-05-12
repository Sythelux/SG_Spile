/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.gui.screens;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.sydsoft.sg_wolfskrone.appstates.ClientMainAppState;
import de.sydsoft.sg_wolfskrone.gui.InputHandler;
import de.sydsoft.sg_wolfskrone.gui.ingame.chat.ChatAreaController;
import de.sydsoft.sg_wolfskrone.gui.ingame.chat.TextFieldControl;
import de.sydsoft.sg_wolfskrone.util.LocHelper;

/**
 *
 * @author sythelux
 */
public class IngameScreen extends MainScreenController {

    private String lastscreen = "";
    private boolean waitscreenactive = false;
    private ChatAreaController chatArea;
    private Element textInputPanel;
    private Element textInput;

    @Override
    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        textInput = screen.findElementByName("text_input");
        chatArea = screen.findControl("chat_area", ChatAreaController.class);
        textInputPanel = screen.findElementByName("ChatTextInput");
    }

    public IngameScreen(ClientMainAppState state) {
        super(state);
        chatArea = new ChatAreaController();
    }

    public void quit() {
        state.setCompleted(true);
        nifty.gotoScreen("end");
    }

    public void startWaitScreen() {
        lastscreen = screen.getScreenId();
//        nifty.gotoScreen("fetching");
        waitscreenactive = true;
    }

    @Override
    public void onStartScreen() {
        super.onStartScreen();
    }

    public void stopWaitScreen() {
        if (lastscreen != null && lastscreen.equals("fetching")) {
            lastscreen = "start";
        }
        nifty.gotoScreen(lastscreen);
        waitscreenactive = false;
    }

    public boolean isWaitscreenactive() {
        return waitscreenactive;
    }

    /**
     * Sends a message to the chat area
     */
    public void sendMessage() {
        state.getGame().sendMessage(textInput.getControl(TextFieldControl.class).getText());
        if (chatArea.getText().isEmpty()) {
            chatArea.append(LocHelper.timeAsChannelStamp());
            chatArea.append(textInput.getControl(TextFieldControl.class).getText());
        } else {
            chatArea.append("\n");
            chatArea.append(LocHelper.timeAsChannelStamp());
            chatArea.append(textInput.getControl(TextFieldControl.class).getText());
        }
        textInput.getControl(TextFieldControl.class).setText("");
        textInput.setFocus();
    }

    public ChatAreaController getChatArea() {
        return chatArea;
    }

    public void buttonClicked(String id) {
        //app.restart() bei options
    }

    public void keyPressed(String id) {
        switch (id) {
            case InputHandler.ReturnKeyString:
                if (textInputPanel.isVisible()) {
                    if (textInput.getControl(TextFieldControl.class).getText().equals("")) {
                        textInputPanel.hide();
                    } else {
                        sendMessage();
                    }
                } else {
                    textInputPanel.show();
                    textInput.setFocus();
                }
                break;
        }
    }

    public String getPlayerAt(String groupNum, String playerNum) {
        return "test:" + playerNum;
    }
}
