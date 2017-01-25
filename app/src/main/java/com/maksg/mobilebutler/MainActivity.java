package com.maksg.mobilebutler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import wifiSwitcher.WifiConnector;

public class MainActivity extends AppCompatActivity {
    private int type = 0;
    private WifiConnector connector = new WifiConnector();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.netNameTE);
        final EditText pass = (EditText) findViewById(R.id.netPassTE);

        connector.setContext(this);

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        Toast.makeText(getApplicationContext(), "Ничего не выбрано",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wepRbutton:
                        type = WifiConnector.NETWORK_TYPE_WEP_WPA;
                        break;
                    case R.id.openRbutton:
                        type = WifiConnector.NETWORK_TYPE_OPEN;
                        break;

                }
            }
        });

        final Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connector.connect(name.getText().toString(), pass.getText().toString(), type);
            }
        });

        Button enableWifiButton = (Button) findViewById(R.id.enableWifiButton);
        enableWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connector.setWifiState(true);
            }
        });

        Button disableWifiButton = (Button) findViewById(R.id.disableWifiButton);
        disableWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connector.setWifiState(false);
            }
        });
    }

}
