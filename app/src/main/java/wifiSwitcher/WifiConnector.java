package wifiSwitcher;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiConnector {
    public static final int NETWORK_TYPE_WEP_WPA = 1;
    public static final int NETWORK_TYPE_OPEN = 2;
    private WifiManager wifiManager = null;

    private void checkWifiManager() {
        if (wifiManager == null)
            throw new RuntimeException("WifiManager is not set");
    }

    public void setContext(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public void connect(String networkSSID, String networkPass, int networkType) {
        if (!isWifiEnabled())
            throw new RuntimeException("Wifi is not enabled");

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + networkSSID + "\"";

        if (networkType == NETWORK_TYPE_WEP_WPA) {
            wifiConfiguration.wepKeys[0] = "\"" + networkPass + "\"";
            wifiConfiguration.wepTxKeyIndex = 0;
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

            wifiConfiguration.preSharedKey = "\"" + networkPass + "\"";
        } else if (networkType == NETWORK_TYPE_OPEN)
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        else
            throw new RuntimeException("Network type is incorrect");

        wifiManager.addNetwork(wifiConfiguration);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration conf : list) {
            if (conf.SSID != null && conf.SSID.equals("\"" + networkSSID + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(conf.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }

    public void setWifiState(boolean wifiState) {
        checkWifiManager();
        wifiManager.setWifiEnabled(wifiState);
    }

    public boolean isWifiEnabled() {
        checkWifiManager();
        return wifiManager.isWifiEnabled();
    }
}
