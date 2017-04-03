package deviceFunctionsManager;

import android.bluetooth.BluetoothAdapter;

public class BluetoothController {
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public BluetoothController() {
        if (bluetoothAdapter == null)
            throw new RuntimeException("Device does not support Bluetooth");
    }

    public boolean getBluetoothState() {
        return bluetoothAdapter.isEnabled();
    }

    public void setBluetoothState(boolean state) {
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (state && !isEnabled)
            bluetoothAdapter.enable();
        else if (!state && isEnabled)
            bluetoothAdapter.disable();
    }
}
