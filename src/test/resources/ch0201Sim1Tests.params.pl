agent(stacy).
agent(employer).
resource(r1, "Source code modification", "Hits of code").
resource(r2, "Money", "2016 US dollars").
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
