agent(stacy).
agent(employer).
resource(r2, "Money", "2016 US dollars").
hasFlow(f2,
    employer,
    stacy,
    r2,
    3000.0,
    oncePerMonth(30)
).
