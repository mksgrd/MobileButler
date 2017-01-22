package switchers;

import android.content.Context;
import android.media.AudioManager;
import android.util.SparseArray;

public class StreamController {
    private AudioManager audioManager;
    private SparseArray<AudioStream> streamSparseArray = new SparseArray<>();

    public void setContext(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void enableStreamControl(int streamType) {
        if (streamSparseArray.get(streamType) == null)
            streamSparseArray.put(streamType, new AudioStream(streamType, audioManager));
        else
            throw new IllegalArgumentException("Stream has already controlled");
    }

    public void disableStreamControl(int streamType) {
        if (streamSparseArray.get(streamType) != null)
            streamSparseArray.delete(streamType);
        else
            throw new RuntimeException("Stream has already not controlled");
    }

    public int getStreamVolume(int streamType) {
        return streamSparseArray.get(streamType).getVolume();
    }

    public void setStreamVolume(int streamType, int volume) {
        streamSparseArray.get(streamType).setVolume(volume);
    }

    public boolean isStreamMuted(int streamType) {
        return streamSparseArray.get(streamType).isMute();
    }

    public void setStreamMuteStare(int streamType, boolean muteState) {
        streamSparseArray.get(streamType).setMuteState(muteState);
    }

    public int getStreamMaxVolume(int streamType) {
        return streamSparseArray.get(streamType).getMaxVolume();
    }
}
