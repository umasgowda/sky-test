package com.sky.rewards;

import java.util.List;

public class Portfolio {
    private List<ChannelSubscription> channelSubscriptions;

    public Portfolio(List<ChannelSubscription> channelSubscriptions) {
        this.channelSubscriptions = channelSubscriptions;
    }

    public List<ChannelSubscription> getChannelSubscriptions() {
        return channelSubscriptions;
    }
}
