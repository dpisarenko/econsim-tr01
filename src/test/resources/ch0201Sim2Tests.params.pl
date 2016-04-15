isAgent(stacy).
isAgent(list).
isAgent(internets).
percentageOfReaders(0.5).
interactionsBeforePurchase(7).
percentageOfBuyers(0.1).

resource(r1, "Message to the list", "Pieces").
resource(r2, "Money", "2016 US dollars").

resource(r3, "Accomodation", "Days the person is allowed to live in the flat").
resource(r4, "Food", "Calories").

resource(r5, "Copy of WordPress plugin X", "Pieces").

% c in pc means "cohort"
resource(r06-pc1, "People, who were exposed to Stacy's writings once", "People").
resource(r07-pc2, "People, who were exposed to Stacy's writings twice", "People").
resource(r08-pc2, "People, who were exposed to Stacy's writings 3 times", "People").
resource(r09-pc2, "People, who were exposed to Stacy's writings 4 times", "People").
resource(r10-pc2, "People, who were exposed to Stacy's writings 5 times", "People").
resource(r11-pc2, "People, who were exposed to Stacy's writings 6 times", "People").
resource(r12-pc2, "People, who were exposed to Stacy's writings 7 or more times", "People").

hasFlow(f1,
    stacy,
    list,
    r1,
    1,
    oncePerWeek("Monday")).

resource(r2, "Money", "2016 US dollars").
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

initialResourceLevel(list, r06-pc1, 143).
initialResourceLevel(list, r07-pc2, 143).
initialResourceLevel(list, r08-pc2, 143).
initialResourceLevel(list, r09-pc2, 143).
initialResourceLevel(list, r10-pc2, 143).
initialResourceLevel(list, r11-pc2, 143).
initialResourceLevel(list, r12-pc2, 142).

initialResourceLevel(stacy, r2, 3000.0).