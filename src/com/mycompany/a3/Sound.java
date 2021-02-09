/**
 * 
 */
package com.mycompany.a3;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/**
 * @author Dennis
 *
 */
public class Sound {
	private Media m;
	
	public Sound(String fileName) {
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
			m = MediaManager.createMedia(is, "audio/wav");
		} catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void play() {
		m.play();
	}
}
