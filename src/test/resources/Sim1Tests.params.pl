agent(stacy).
agent(employer).
resource(r1, "Source code modification", "Hits of code").
process(p1).
% numericAmount(X) : is
someAmountWeDontCare()
hasFlow(f1, % ID of the flow
    p1, % Process, to which the flow belongs
    stacy, % Source of the flow
    employer, % Target of the flow
    r1, % Resource, which flows
    _, % Amount - we don't know it and we don't care
    businessDays(9), % This flow occurs on business days for 9 hours a day
    ).
