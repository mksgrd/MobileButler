package deviceFunctionsManager;

import android.content.Context;

public class Controllers {
    private AudioController audioController = new AudioController();
    private WifiController wifiController = new WifiController();

    public void setContext(Context context) {
        audioController.setContext(context);
        wifiController.setContext(context);
    }

    public AudioController getAudioController() {
        return audioController;
    }

    public WifiController getWifiController() {
        return wifiController;
    }
}
