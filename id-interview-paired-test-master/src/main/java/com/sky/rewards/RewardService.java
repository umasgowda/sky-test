package com.sky.rewards;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RewardService {

    private static final Map<ChannelSubscription, Reward> rewardMap = Map.of(ChannelSubscription.SPORTS, Reward.CHAMPIONS_LEAGUE_FINAL_TICKET, ChannelSubscription.MUSIC, Reward.KARAOKE_PRO_MICROPHONE, ChannelSubscription.MOVIES, Reward.PIRATES_OF_THE_CARIBBEAN_COLLECTION,
            ChannelSubscription.DRAMA, Reward.DOWNTOWN_ABBEY_BOXSET);

    private EligibilityService eligibilityService;

    public RewardService(EligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    public Set<Reward> getRewards(String accountNumber, Portfolio portfolio) throws InvalidAccountNumberException {
        try {
            if (eligibilityService.isEligible(accountNumber)) {
                return portfolio.getChannelSubscriptions().stream()
                        .map(rewardMap::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
            } else {
                return Collections.emptySet();
            }
        } catch (TechnicalFailureException te) {
            return new HashSet<>();
        } catch (InvalidAccountNumberException invalidAccountNumberException) {
            System.out.println("Supplied account number is invalid " + accountNumber);
            throw new InvalidAccountNumberException("EligibilityService", invalidAccountNumberException.getCause());
        }
    }

}
