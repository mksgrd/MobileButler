package deviceFunctionsManager;

import android.content.Context;

public class ControllersInitializer {
    private AudioController audioController = new AudioController();
    private WifiController wifiController = new WifiController();
    private BluetoothController bluetoothController = new BluetoothController();

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

    public BluetoothController getBluetoothController() {
        return bluetoothController;
    }
}
