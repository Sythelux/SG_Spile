package de.sydsoft.sg_wolfskrone.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.network.Client;
import com.jme3.network.serializing.Serializer;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.Nifty;
import de.sydsoft.libst.util.Console;
import de.sydsoft.libst.util.Constants;
import de.sydsoft.sg_wolfskrone.appstates.IngameState;
import de.sydsoft.sg_wolfskrone.appstates.IntroState;
import de.sydsoft.sg_wolfskrone.appstates.LoadingState;
import de.sydsoft.sg_wolfskrone.appstates.LoginState;
import de.sydsoft.sg_wolfskrone.audio.AudioConstants;
import de.sydsoft.sg_wolfskrone.entities.Player;
import de.sydsoft.sg_wolfskrone.entities.chars.PlayerCharacter;
import de.sydsoft.sg_wolfskrone.gui.screens.IngameScreen;
import de.sydsoft.sg_wolfskrone.gui.screens.IntroScreen;
import de.sydsoft.sg_wolfskrone.gui.screens.LoadingScreen;
import de.sydsoft.sg_wolfskrone.gui.screens.LoginScreen;
import de.sydsoft.sg_wolfskrone.logic.ChatMessage;
import de.sydsoft.sg_wolfskrone.logic.PlayerLoaderMessage;
import de.sydsoft.sg_wolfskrone.logic.ServerAnswerMessage;
import de.sydsoft.sg_wolfskrone.logic.ServerRequestMessage;

public class GameClient extends SimpleApplication {

    private static String firstRunPassed = "firstRunPassed";
    public static String ServerIPPort = "localhost:"+0xface;
    private Client client;
    private IntroState introState = new IntroState();
    private LoadingState loadingState = new LoadingState();
    private IngameState ingameState = new IngameState();
    private LoginScreen loginScreen;
    private LoginState loginState = new LoginState();
    private final BulletAppState bulletAppState = new BulletAppState();
    private Player player;
    private InputHandler inputHandler;
    private Nifty nifty;
    private IngameScreen ingameScreen;
    private AudioNode audioRootNode;

    public static void main(String[] args) throws IOException {
        Constants.DEBUG = false;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--debug":
                    case "-D":
                        Constants.DEBUG = true;
                        break;
                    case "--server":
                    case "-S":
                        ServerIPPort = args[i + 1];
                        break;
                }
            }
        }
        GameClient app = new GameClient();
        AppSettings settings = new AppSettings(true);
        try {
            settings.load("de/sydsoft/Wolfskrone");
            if (settings.getBoolean(firstRunPassed)) {
                app.setShowSettings(false);
            } else {
                settings.putBoolean(firstRunPassed, true);
                settings.setIcons(new BufferedImage[]{
                            ImageIO.read(GameClient.class.getResourceAsStream("/Interface/Logos/Wolfskrone256.png")),
                            ImageIO.read(GameClient.class.getResourceAsStream("/Interface/Logos/Wolfskrone128.png")),
                            ImageIO.read(GameClient.class.getResourceAsStream("/Interface/Logos/Wolfskrone32.png")),
                            ImageIO.read(GameClient.class.getResourceAsStream("/Interface/Logos/Wolfskrone16.png")),});
                settings.setTitle("Wolfskrone");
                settings.setWidth(1024);
                settings.setHeight(768);
                settings.setSettingsDialogImage("/Interface/Logos/Wolfskrone256.png");
                settings.putFloat(AudioConstants.AUDIOROOTNODE + "Volume", 0.5f);
                settings.putFloat(AudioConstants.MUSICNODE + "Volume", 0.25f);
                settings.putFloat(AudioConstants.SOUNDNODE + "Volume", 0.5f);
                settings.putFloat(AudioConstants.SPEECHNODE + "Volume", 0.75f);
                settings.putFloat(AudioConstants.AMBIENTSOUNDNODE + "Volume", 0.5f);
                app.setShowSettings(true);
            }
            app.setSettings(settings);
            app.start();
        } catch (BackingStoreException ex) {
            settings.putBoolean(firstRunPassed, false);
        } finally {
            try {
                settings.save("de/sydsoft/Wolfskrone");
            } catch (BackingStoreException ex) {
                Console.errMsg(ex);
            }
        }
    }

    public GameClient() {
        super();
//        assetManager = new DesktopAssetManager();
    }

    @Override
    public void simpleInitApp() {
        Serializer.registerClass(ChatMessage.class);
        Serializer.registerClass(ServerRequestMessage.class);
        Serializer.registerClass(PlayerLoaderMessage.class);
        Serializer.registerClass(ServerAnswerMessage.class);
        if (!Constants.DEBUG) {
            for (Enumeration<String> e = LogManager.getLogManager().getLoggerNames(); e.hasMoreElements();) {
                Logger.getLogger(e.nextElement()).setLevel(Level.OFF);
            }
            Logger.getLogger(Console.LOG).setLevel(Level.ALL);
        } else {
            inputManager.setCursorVisible(true);
        }
        inputManager.setCursorVisible(true);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();

        // <editor-fold defaultstate="collapsed" desc="Init Sound">
//        audioRootNode = new AudioNode(assetManager, AudioConstants.AUDIOROOTNODE);
//        AudioNode musicNode = new AudioNode(assetManager, AudioConstants.MUSICNODE);
//        AudioNode soundNode = new AudioNode(assetManager, AudioConstants.SOUNDNODE);
//        AudioNode speechNode = new AudioNode(assetManager, AudioConstants.SPEECHNODE);
//        AudioNode ambientNode = new AudioNode(assetManager, AudioConstants.AMBIENTSOUNDNODE);
        audioRootNode = new AudioNode();
        AudioNode musicNode = new AudioNode();
        AudioNode soundNode = new AudioNode();
        AudioNode speechNode = new AudioNode();
        AudioNode ambientSoundNode = new AudioNode();
        audioRootNode.setName(AudioConstants.AUDIOROOTNODE);
        audioRootNode.setVolume(settings.getFloat(AudioConstants.AUDIOROOTNODE + "Volume"));
        musicNode.setName(AudioConstants.MUSICNODE);
        musicNode.setVolume(settings.getFloat(AudioConstants.MUSICNODE + "Volume"));
        soundNode.setName(AudioConstants.SOUNDNODE);
        soundNode.setVolume(settings.getFloat(AudioConstants.SOUNDNODE + "Volume"));
        speechNode.setName(AudioConstants.SPEECHNODE);
        speechNode.setVolume(settings.getFloat(AudioConstants.SPEECHNODE + "Volume"));
        ambientSoundNode.setName(AudioConstants.AMBIENTSOUNDNODE);
        ambientSoundNode.setVolume(settings.getFloat(AudioConstants.AMBIENTSOUNDNODE + "Volume"));
        audioRootNode.attachChild(musicNode);
        audioRootNode.attachChild(soundNode);
        audioRootNode.attachChild(speechNode);
        audioRootNode.attachChild(ambientSoundNode);
        rootNode.attachChild(audioRootNode);
        // </editor-fold>


        IntroScreen introScreen = new IntroScreen(introState);
        introState.setScreenController(introScreen);
        nifty.registerScreenController(introScreen);

        loginScreen = new LoginScreen(loginState);
        loginState.setScreenController(loginScreen);
        nifty.registerScreenController(loginScreen);

        LoadingScreen loadingScreen = new LoadingScreen(ingameState);
        loadingState.setScreenController(loadingScreen);
        nifty.registerScreenController(loadingScreen);

        ingameScreen = new IngameScreen(ingameState);
        ingameState.setScreenController(ingameScreen);
        nifty.registerScreenController(ingameScreen);

        if (Constants.DEBUG) {
            nifty.fromXml("Interface/Nifty/NiftyScreens.xml", "login");
        } else {
            nifty.fromXml("Interface/Nifty/NiftyScreens.xml", "startSydSoft");
        }
        nifty.addControls();
        guiViewPort.addProcessor(niftyDisplay);

        inputHandler = new InputHandler(inputManager, player, nifty, this.getSettings());

        if (Constants.DEBUG) {
            stateManager.attach(loginState);
        } else {
            stateManager.attach(introState);
        }
    }

    public void toLoginScreen() {
        stateManager.attach(loginState);
        stateManager.detach(introState);
    }

    public void toLoadingScreen() {
        stateManager.attach(loadingState);
        stateManager.detach(loginState);
    }

    public void toIngameScreen() {
        stateManager.attach(ingameState);
        stateManager.detach(loadingState);
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        if (loginState.isEnabled() && loginScreen.isInitialized() && !loginScreen.isPlayerShown()) {
            loginScreen.showPlayer();
        }
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerCharacter getActualCharacter() {
        if (player == null) {
            return null;
        }
        return player.getActualCharacter();
    }

    public AppSettings getSettings() {
        return settings;
    }

    public void sendMessage(String mail) {
        client.send(new ChatMessage(mail));
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public Nifty getNifty() {
        return nifty;
    }

    public AudioNode getAudioRootNode() {
        return audioRootNode;
    }
}
