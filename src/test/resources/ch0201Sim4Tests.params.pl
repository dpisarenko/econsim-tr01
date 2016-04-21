isAgent(stacy).
isAgent(employer).
isAgent(landlord).
isAgent(groceryStore).
isAgent(savingsAccount).
isAgent(nature).
isAgent(list).
isAgent(internets).
resource(r1, "Source code modification", "Hits of code").
resource(r2, "Money", "2016 US dollars").
resource(r3, "Accomodation", "Days the person is allowed to live in the flat").
resource(r4, "Food", "Calories").
resource("r06-pc1", "People, who were exposed to Stacy''s writings once", "People").
resource(r13, "Time available for business-building", "Hours").
resource(r14, "Time spent developing software product X", "Hours").
resource(r15, "Published guest posts", "Pieces").

% Initial resource levels
initialResourceLevel(stacy, r2, 3000.0).

% Infinite resource supplies
infiniteResourceSupply(groceryStore, r4).
infiniteResourceSupply(landlord, r3).
infiniteResourceSupply(employer, r2).
infiniteResourceSupply(nature, r13).
infiniteResourceSupply(internets, "r06-pc1").

hasFlow(f1,
    stacy,
    employer,
    r1,
    _,
    businessDays).
hasFlow(f2,
    employer,
    stacy,
    r2,
    3000.0,
    oncePerMonth(30)
).
% Rent
hasFlow(f3,
    stacy,
    landlord,
    r2,
    1000.0,
    oncePerMonth(1)
).
hasFlow(f4,
    landlord,
    stacy,
    r3,
    30.0,
    oncePerMonth(1)
).
% Food
hasFlow(f5,
    stacy,
    groceryStore,
    r2,
    30.0,
    daily(19, 00)
).
hasFlow(f5,
    groceryStore,
    stacy,
    r4,
    2534.0,
    daily(19, 01)
).
% Savings
hasFlow(f6,
    stacy,
    savingsAccount,
    r2,
    500.0,
    oncePerMonth(1)
).

% Every week, Stacy gets an influx of 40 hours of time, which
% she can spend on building her business
hasFlow(f7,
    nature,
    stacy,
    r13,
    40,
    oncePerWeek("Monday")).

% Every week, Stacy transforms 20 hours of time into units of
% software development completion.
% Can we measure software development completion by the amount
% of working time we put into it?

hasTransformation(t1,
    stacy,
    20.0, % 20 hours of productive time
    r13,
    20.0, % 20 hours of hours spent on creating product X
    r14,
    oncePerWeek("Monday")).

% Every week, Stacy transforms 20 hours of productive time
% into half a guest post. This gives one guest post in
% 2 weeks.

hasTransformation(t2,
    stacy,
    20.0, % 20 hours of productive time
    r13,
    0.5, % half a guest post
    r15,
    oncePerWeek("Monday")).

% When the guest post is ready, flow 8 occurs.

% Sending out the mailing with link to a blog post
hasFlow(f8,
    stacy,
    list,
    r15,
    1.0,
    whenResourceReachesLevel(stacy, r15, 1.0) % The flow is triggered, when certain resource reaches certain level at certain agent
).

% After a guest post, 20 new people come into Stacy's list
hasFlow(f9,
    internets,
    list,
    "r06-pc1",
    20.0,
    after(f8)).
