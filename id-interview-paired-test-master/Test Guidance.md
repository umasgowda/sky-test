# Paired Test
 
## Introduction

A product owner has revived some work that was started, but never made ready for production.  The people who previously worked on the code are on holiday and it has become urgent to work on this now.  The initial specification is provided and we have the code that was started.  

## Bug in code?

A note on the Jira ticket from an automation tester states that the functional test around InvalidAccountNumberException is not working as expected and so there must be a bug in the code.  Can we track down the error and fix it?

## Performance part 1

Our performance testers have noticed spikes in the latency of our service under heavy load.  They are testing in an environment where the eligibility service is stubbed, and is so that 80% of accounts are not eligible for a reward in line with the product owners estimates. They point out that there is excessive garbage collection.  Can we improve our implementation?

## Change Request
The product owner has an additional reward that needs to be returned by the reward service.  If the customer has the channel “DRAMA” then they should get a reward “DOWNTOWN_ABBEY_BOXSET”

## Performance part 2
After changing our implementation, further performance tests are run in a production like environment.  There are spikes in latency again.  What could be the cause? Can we make changes to prove it? 

## New requirements
It was identified that the eligibility service does not perform well under heavy load.  It regularly takes over 3 seconds to respond.  The team responsible for this component have no scope to change this at present.  Our rewards service must respond within a second.  A business decision is that it is preferable to return no rewards within a second, rather than potentially return some rewards after 3 or more seconds.  

* Do you agree with this decision?
* Can you think of a situation where this would be beneficial or not desirable?
* How can we go about meeting these new requirements?