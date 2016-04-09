agent(stacy).
agent(employer).
resource(r1, "Source code modification", "Hits of code").
X = businessDays(9).
hasFlow(f1,
    stacy,
    employer,
    r1,
    _,
    businessDays).
