/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2009, 2010, 2011 Caprica Software Limited.
 */

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

/**
 * This simple test player shows how to get direct access to the video frame
 * data.
 * <p>
 * This implementation uses the new (1.1.1) libvlc video call-backs function.
 * <p>
 * Since the video frame data is made available, the Java call-back may modify
 * the contents of the frame if required.
 * <p>
 * The frame data may also be rendered into components such as an OpenGL
 * texture.
 */
public class DirectTestPlayer extends VlcjTest {

	// The size does NOT need to match the mediaPlayer size - it's the size that
	// the media will be scaled to
	// Matching the native size will be faster of course
	private final int					width	= 640;
	private final int					height	= 480;

	// private final int width = 1280;
	// private final int height = 720;

	/**
	 * Image to render the video frame data.
	 */
	private final BufferedImage			image;

	private final MediaPlayerFactory	factory;

	private final DirectMediaPlayer		mediaPlayer;

	private ImagePane					imagePane;

	public DirectTestPlayer(String media, String[] args) throws InterruptedException, InvocationTargetException {
		image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);
		image.setAccelerationPriority(1.0f);

		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame("VLCJ Direct Video Test");
				// frame.setIconImage(new
				// ImageIcon(getClass().getResource("/icons/vlcj-logo.png")).getImage());
				imagePane = new ImagePane(image);
				imagePane.setSize(width, height);
				imagePane.setMinimumSize(new Dimension(width, height));
				imagePane.setPreferredSize(new Dimension(width, height));
				frame.getContentPane().setLayout(new BorderLayout());
				frame.getContentPane().add(imagePane, BorderLayout.CENTER);
				frame.pack();
				frame.setResizable(false);
				frame.setVisible(true);

				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						mediaPlayer.release();
						factory.release();
						System.exit(0);
					}
				});
			}

		});

		factory = new MediaPlayerFactory(args);
		mediaPlayer = factory.newDirectMediaPlayer(new TestBufferFormatCallback(), new TestRenderCallback());
		mediaPlayer.playMedia(media);

		// Just to show regular media player functions still work...
		Thread.sleep(5000);
		mediaPlayer.nextChapter();
	}

	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		if (args.length < 1) {
			System.out.println("Specify a single media URL");
			System.exit(1);
		}

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/home/linux/vlc/install/lib");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		String[] vlcArgs = (args.length == 1) ? new String[] {} : Arrays.copyOfRange(args, 1, args.length);

		new DirectTestPlayer(args[0], vlcArgs);

		// Application will not exit since the UI thread is running
	}

	@SuppressWarnings("serial")
	private final class ImagePane extends JPanel {

		private final BufferedImage	image;

		private final Font			font	= new Font("Sansserif", Font.BOLD, 36);

		public ImagePane(BufferedImage image) {
			this.image = image;
		}

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, null, 0, 0);
			// You could draw on top of the image here...
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2.setColor(Color.red);
			g2.setComposite(AlphaComposite.SrcOver.derive(0.3f));
			g2.fillRoundRect(100, 100, 100, 80, 32, 32);
			g2.setComposite(AlphaComposite.SrcOver);
			g2.setColor(Color.white);
			g2.setFont(font);
			g2.drawString("vlcj direct media player", 130, 150);
		}
	}

	private final class TestBufferFormatCallback implements BufferFormatCallback {

		@Override
		public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
			return new BufferFormat("RGBA", sourceWidth, sourceHeight, new int[] { sourceWidth * 4 }, new int[] { sourceHeight });
		}

	}

	private final class TestRenderCallback extends RenderCallbackAdapter {
		public TestRenderCallback() {
			super(new int[width * width]);
		}

		@Override
		protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
			System.out.println("on display: " + rgbBuffer.length);
			image.setRGB(0, 0, width, width, rgbBuffer, 0, width);
		}
	}

}