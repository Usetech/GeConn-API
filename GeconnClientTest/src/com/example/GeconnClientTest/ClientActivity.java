package com.example.GeconnClientTest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.usetech.onroad.android.integration.ChannelInfo;
import com.usetech.onroad.android.integration.IGeConnClient;

public class ClientActivity extends Activity {
    private IGeConnClient mService = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bindService(new Intent(IGeConnClient.class.getName()), mConnection, Context.BIND_AUTO_CREATE);

        Button btnGetChannels = (Button)findViewById(R.id.btnGetChannels);
        btnGetChannels.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                OutputChannels();
            }
        });

        Button btnSendVoiceMessage = (Button)findViewById(R.id.btnSendVoiceMessage);
        btnSendVoiceMessage.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                SendVoiceMessage();
            }
        });

        Button btnToggleMute = (Button)findViewById(R.id.btnToggleMute);
        btnToggleMute.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                ToggleIsMuted();
            }
        });

        Button btnIsMuted = (Button)findViewById(R.id.btnIsMuted);
        btnIsMuted.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                ShowIsMuted();
            }
        });
    }

    private void ShowIsMuted() {
        if (mService == null) return;
        try {
            Toast.makeText(this, Boolean.toString(mService.isMuted()), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void ToggleIsMuted() {
        if (mService == null) return;
        try {
            mService.setMute(!mService.isMuted());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void OutputChannels() {
        if (mService == null) return;
        try {
            ChannelInfo[] channels = mService.getChannels();
            Toast.makeText(this, formatChannels(channels), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void SendVoiceMessage() {
        if (mService == null) return;
        try {
            ChannelInfo[] channels = mService.getChannels();
            if (channels.length > 0)
                mService.startChannelVoiceMessage(channels[0].getId());
            else
                Toast.makeText(this, "No channels", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String formatChannels(ChannelInfo[] s) {
        if (s.length == 0) return "";
        StringBuilder sb = new StringBuilder(formatChannelDto(s[0]));
        for (int i = 1; i < s.length; i++) {
            sb.append(" ");
            sb.append(formatChannelDto(s[i]));
        }
        return sb.toString();
    }

    private String formatChannelDto(ChannelInfo s) {
        return Integer.toString(s.getId()) + " " + s.getName();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IGeConnClient.Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }


    };

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
