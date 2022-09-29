package com.sky.rewards;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RewardServiceTest {

    @Mock
    EligibilityService mockEligibilityService;

    @InjectMocks
    private RewardService rewardService;

    @Test
    public void technicalFailureExceptionReturnsNoRewards() throws Exception {
        when(mockEligibilityService.isEligible("1")).thenThrow(new TechnicalFailureException());

        Portfolio portfolio = new Portfolio(Arrays.asList(ChannelSubscription.values()));
        Set<Reward> rewards = rewardService.getRewards("1", portfolio);

        assertThat(rewards.size(), is(0));
    }

    @Test(expected = InvalidAccountNumberException.class)
    public void invalidAccountNumberExceptionReturnsNoRewards() throws Exception {
        when(mockEligibilityService.isEligible("1")).thenThrow(new InvalidAccountNumberException());

        Portfolio portfolio = new Portfolio(Arrays.asList(ChannelSubscription.values()));
        rewardService.getRewards("1", portfolio);

    }


    @Test
    public void eligibleCustomerWithKidsGetsNoReward() throws Exception {

        when(mockEligibilityService.isEligible("6")).thenReturn(true);

        List<ChannelSubscription> channelSubscriptions = new ArrayList<>();
        channelSubscriptions.add(ChannelSubscription.KIDS);
        Portfolio portfolio = new Portfolio(channelSubscriptions);

        Set<Reward> rewards = rewardService.getRewards("6", portfolio);

        assertThat(rewards.size(), is(0));

        verify(mockEligibilityService).isEligible("6");
    }

    @Test
    public void eligibleCustomerWithSportsGetsChampionsLeagueReward() throws Exception {

        when(mockEligibilityService.isEligible("7")).thenReturn(true);

        List<ChannelSubscription> channelSubscriptions = new ArrayList<>();
        channelSubscriptions.add(ChannelSubscription.SPORTS);
        Portfolio portfolio = new Portfolio(channelSubscriptions);

        Set<Reward> rewards = rewardService.getRewards("7", portfolio);

        assertThat(rewards.size(), is(1));

        assertThat(rewards, hasItem(Reward.CHAMPIONS_LEAGUE_FINAL_TICKET));

        verify(mockEligibilityService).isEligible("7");
    }

    @Test
    public void eligibleCustomerWithAllChannelsGetsAllRewards() throws Exception {

        when(mockEligibilityService.isEligible("8")).thenReturn(true);

        Portfolio portfolio = new Portfolio(Arrays.asList(ChannelSubscription.values()));

        Set<Reward> rewards = rewardService.getRewards("8", portfolio);

        assertThat(rewards.size(), is(4));

        assertThat(rewards, hasItems(Reward.values()));

        verify(mockEligibilityService).isEligible("8");
    }
    @Test
    public void eligibleCustomerWithDramaGetsDowntimeAbbayReward() throws Exception {

        when(mockEligibilityService.isEligible("9")).thenReturn(true);

        List<ChannelSubscription> channelSubscriptions = new ArrayList<>();
        channelSubscriptions.add(ChannelSubscription.DRAMA);
        Portfolio portfolio = new Portfolio(channelSubscriptions);

        Set<Reward> rewards = rewardService.getRewards("9", portfolio);

        assertThat(rewards.size(), is(1));

        assertThat(rewards, hasItem(Reward.DOWNTOWN_ABBEY_BOXSET));

        verify(mockEligibilityService).isEligible("9");
    }

    @Test
    public void ineligibleCustomerWithAllChannelsGetsNoRewards() throws Exception {

        when(mockEligibilityService.isEligible("1")).thenReturn(false);
        Portfolio portfolio = new Portfolio(Arrays.asList(ChannelSubscription.values()));

        Set<Reward> rewards = rewardService.getRewards("1", portfolio);

        assertThat(rewards.size(), is(0));
    }


    @Test
    public void ineligibleCustomerWithSportsChannelGetsNoRewards() throws Exception {

        when(mockEligibilityService.isEligible("2")).thenReturn(false);
        List<ChannelSubscription> channelSubscriptions = new ArrayList<>();
        channelSubscriptions.add(ChannelSubscription.SPORTS);
        Portfolio portfolio = new Portfolio(channelSubscriptions);

        Set<Reward> rewards = rewardService.getRewards("2", portfolio);

        assertThat(rewards.size(), is(0));
    }

    @Test
    public void ineligibleCustomerWithKidsChannelGetsNoRewards() throws Exception {

        when(mockEligibilityService.isEligible("3")).thenReturn(false);
        List<ChannelSubscription> channelSubscriptions = new ArrayList<>();
        channelSubscriptions.add(ChannelSubscription.KIDS);
        Portfolio portfolio = new Portfolio(channelSubscriptions);

        Set<Reward> rewards = rewardService.getRewards("3", portfolio);

        assertThat(rewards.size(), is(0));
    }

    @Test
    public void ineligibleCustomerWithMusicChannelGetsNoRewards() throws Exception {

        when(mockEligibilityService.isEligible("4")).thenReturn(false);
        List<ChannelSubscription> channelSubscriptions = new ArrayList<>();
        channelSubscriptions.add(ChannelSubscription.MUSIC);
        Portfolio portfolio = new Portfolio(channelSubscriptions);

        Set<Reward> rewards = rewardService.getRewards("4", portfolio);

        assertThat(rewards.size(), is(0));
    }

    @Test
    public void ineligibleCustomerWithNewsChannelGetsNoRewards() throws Exception {

        when(mockEligibilityService.isEligible("5")).thenReturn(false);
        List<ChannelSubscription> channelSubscriptions = new ArrayList<>();
        channelSubscriptions.add(ChannelSubscription.NEWS);
        Portfolio portfolio = new Portfolio(channelSubscriptions);

        Set<Reward> rewards = rewardService.getRewards("5", portfolio);

        assertThat(rewards.size(), is(0));
    }
}
