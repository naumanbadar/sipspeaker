/**
 * 
 */
package speech;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;


public class Speech {
	public static void produce(String fileName, String textToSpeak) {
		AudioPlayer audioPlayer = null;
		String voiceName = "kevin16";

		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice helloVoice = voiceManager.getVoice(voiceName);

		helloVoice.allocate();
		audioPlayer = new SingleFileAudioPlayer(fileName, javax.sound.sampled.AudioFileFormat.Type.WAVE);
		helloVoice.setAudioPlayer(audioPlayer);

		helloVoice.speak(textToSpeak);

		helloVoice.deallocate();
		audioPlayer.close();
	}
}
