isAgent(stacy).
isAgent(list).
isAgent(internets).
isAgent(landlord).
isAgent(groceryStore).
percentageOfReaders(0.5).
interactionsBeforePurchase(7).
percentageOfBuyers(0.1).
priceOfOneCopyOfSoftware(40.0).

resource(r1, "Message to the list", "Pieces").
resource(r2, "Money", "2016 US dollars").
resource(r3, "Accomodation", "Days the person is allowed to live in the flat").
resource(r4, "Food", "Calories").

resource(r5, "Copy of WordPress plugin X", "Pieces").

% c in pc means "cohort"
resource("r06-pc1", "People, who were exposed to Stacy''s writings once", "People").
resource("r07-pc2", "People, who were exposed to Stacy''s writings twice", "People").
resource("r08-pc3", "People, who were exposed to Stacy''s writings 3 times", "People").
resource("r09-pc4", "People, who were exposed to Stacy''s writings 4 times", "People").
resource("r10-pc5", "People, who were exposed to Stacy''s writings 5 times", "People").
resource("r11-pc6", "People, who were exposed to Stacy''s writings 6 times", "People").
resource("r12-pc7", "People, who were exposed to Stacy''s writings seven times", "People").

initialResourceLevel(list, "r06-pc1", 143).
initialResourceLevel(list, "r07-pc2", 143).
initialResourceLevel(list, "r08-pc3", 143).
initialResourceLevel(list, "r09-pc4", 143).
initialResourceLevel(list, "r10-pc5", 143).
initialResourceLevel(list, "r11-pc6", 143).
initialResourceLevel(list, "r12-pc7", 142).

initialResourceLevel(stacy, r2, 3000.0).

infiniteResourceSupply(stacy, r5).
infiniteResourceSupply(list, r2).

% Sending out the mailing with link to a blog post
hasFlow(f1,
    stacy,
    list,
    r1,
    1,
    oncePerWeek("Monday")).

% Subscribes buy copies of Stacy's software
hasFlow(f2,
    list,
    stacy,
    r2,
    _, % priceOfSoftwareSoldToNewlyActivatedAudience()
    after(f1)).
hasFlow(f3,
    stacy,
    list,
    r5,
    _, % numberOfCopiesOfSoftwareSoldToNewlyActivatedAudience()
    after(f1)).

% Rent
hasFlow(f4,
    stacy,
    landlord,
    r2,
    1000.0,
    oncePerMonth(1)
).
hasFlow(f5,
    landlord,
    stacy,
    r3,
    30.0,
    oncePerMonth(1)
).
% Food
hasFlow(f6,
    stacy,
    groceryStore,
    r2,
    30.0,
    daily(19, 00)
).
hasFlow(f7,
    groceryStore,
    stacy,
    r4,
    2534.0,
    daily(19, 01)
).