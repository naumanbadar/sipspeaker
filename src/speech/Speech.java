/**
 * 
 */
package speech;

import com.sun.speech.freetts.FreeTTS;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;


public class Speech {
	public static void produce(String fileName, String textToSpeak) {
		AudioPlayer audioPlayer = null;
		String voiceName = "kevin16";

		// System.out.println();
		// System.out.println("Using voice: " + voiceName);
		//
		// /*
		// * The VoiceManager manages all the voices for FreeTTS.
		// */
		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice helloVoice = voiceManager.getVoice(voiceName);

		// if (helloVoice == null) {
		// System.err.println("Cannot find a voice named " + voiceName +
		// ".  Please specify a different voice.");
		// System.exit(1);
		// }

		/*
		 * Allocates the resources for the voice.
		 */
		helloVoice.allocate();

		/*
		 * Synthesize speech.
		 */
		// create a audioplayer to dump the output file
		audioPlayer = new SingleFileAudioPlayer(fileName, javax.sound.sampled.AudioFileFormat.Type.WAVE);
		// attach the audioplayer
		helloVoice.setAudioPlayer(audioPlayer);

		helloVoice.speak(textToSpeak);

		/*
		 * Clean up and leave.
		 */
		helloVoice.deallocate();
		// don't forget to close the audioplayer otherwise file will not be
		// saved
		audioPlayer.close();
	}
}
