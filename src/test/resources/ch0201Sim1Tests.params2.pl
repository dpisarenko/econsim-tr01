agent(stacy).
agent(employer).
resource(r2, "Money", "2016 US dollars").
MonthlySalary = 3000.
hasFlow(f2,
    employer,
    stacy,
    r2,
    MonthlySalary,
    oncePerMonth(30)
).
