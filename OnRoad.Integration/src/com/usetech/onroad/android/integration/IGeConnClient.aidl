package com.usetech.onroad.android.integration;

import com.usetech.onroad.android.integration.ChannelInfo;

interface IGeConnClient {
    ChannelInfo[] getChannels();
    boolean isMuted();
    void setMute(boolean mute);
    void startChannelVoiceMessage(int channelId);
}
