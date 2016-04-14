isAgent(stacy).
isAgent(list).
isAgent(internets).

resource(r1, "Message to the list", "Pieces").
resource(r2, "Money", "2016 US dollars").

resource(r3, "Accomodation", "Days the person is allowed to live in the flat").
resource(r4, "Food", "Calories").

resource(r5, "Copy of WordPress plugin X", "Pieces").

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
    priceOfSoftwareSoldToNewlyActivatedAudience(),
    after(f1)).
hasFlow(f3,
    stacy,
    list,
    r5,
    numberOfCopiesOfSoftwareSoldToNewlyActivatedAudience(),
    after(f1)).
