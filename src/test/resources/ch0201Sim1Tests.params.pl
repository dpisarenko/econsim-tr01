agent(stacy).
agent(employer).
agent(landlord).
agent(groceryStore).
agent(savingsAccount).
resource(r1, "Source code modification", "Hits of code").
resource(r2, "Money", "2016 US dollars").
resource(r3, "Accomodation", "Days the person is allowed to live in the flat").
resource(r4, "Food", "Calories").
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
    stacy,
    groceryStore,
    r4,
    2534.0,
    daily(19, 01)
).
% Savings
hasFlow(f6,
    stacy,
    savingsAccount,
    r2,
    1000.0,
    oncePerMonth(1)
).

