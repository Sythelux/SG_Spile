/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture2D;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class CopyOfVideoPlayer extends SimpleApplication {

	private static final int			WIDTH			= 1280;
	private static final int			HEIGHT			= 720;
	private static final int			DEPTH			= 4;
	private static final String			VIDEO_FORMAT	= "RGBA";

	private static final Format			TEXTURE_FORMAT	= Format.RGBA16;

	private final Image					videoImage;
	private final BufferedImage			bufImg;
	private final Texture2D				videoTexture;
	private final MediaPlayerFactory	factory;
	private final DirectMediaPlayer		mediaPlayer;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Specify a single media URL");
			System.exit(1);
		}
		String[] vlcArgs = (args.length == 1) ? new String[] {} : Arrays.copyOfRange(args, 1, args.length);

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/home/linux/vlc/install/lib");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		new CopyOfVideoPlayer(args[0], vlcArgs).start();

	}

	public CopyOfVideoPlayer(String args, String[] vlcArgs) {
		bufImg = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(WIDTH, HEIGHT);
		bufImg.setAccelerationPriority(1.0f);

		factory = new MediaPlayerFactory(vlcArgs);

		mediaPlayer = factory.newDirectMediaPlayer(new MyBufferFormatCallbackAdapter(), new MyRenderCallbackAdapter());

		videoImage = new Image(TEXTURE_FORMAT, WIDTH, HEIGHT, null);
		videoTexture = new Texture2D(videoImage);
	}

	@Override
	public void simpleInitApp() {
		Box videoBox = new Box(new Vector3f(0, 0, -1f), 1.28f, 0.720f, 2f);
		Geometry cube = new Geometry("My Video Box", videoBox);
		Material mat_stl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_stl.setTexture("ColorMap", videoTexture);
		cube.setMaterial(mat_stl);
		cube.rotate((float) Math.PI, 0, 0);
		rootNode.attachChild(cube);
	}
	
	@Override
	public void update() {
//		ByteBuffer tmp = ByteBuffer.allocate(0);
//		ImageToAwt.convert(bufImg, TEXTURE_FORMAT, tmp);
//		videoImage.setData(tmp);
		super.update();
	}

	@Override
	public void start() {
		System.out.println("starting");
		mediaPlayer.play();
		super.start();
	}
	
	@Override
	public void stop() {
		System.out.println("stopping");
		mediaPlayer.release();
		factory.release();
		super.stop();
	}

	private final class MyBufferFormatCallbackAdapter implements BufferFormatCallback {

		@Override
		public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
			return  new BufferFormat(VIDEO_FORMAT, sourceWidth, sourceHeight, new int[] { sourceWidth * DEPTH }, new int[] { sourceHeight });
		}


	}

	private final class MyRenderCallbackAdapter extends RenderCallbackAdapter {
		public MyRenderCallbackAdapter() {
			super(new int[WIDTH * HEIGHT]);
		}

		@Override
		protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
			System.out.println("on display: "+rgbBuffer.length);
			bufImg.setRGB(0, 0, WIDTH, HEIGHT, rgbBuffer, 0, WIDTH);
		}
	}
}