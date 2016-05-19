siegfried = character('Siegfried').
valkyrie = character('Valkyrie').

scene1 = scene('1').

% startsAt(scene1, date(2015,3,14,-,-,-,-,-,-)).

participates('scene1', 'siegfried').
participates('scene1', 'valkyrie').

scene2 = scene('2').
participates(scene2, siegfried).
startsAfter(scene1, scene2). % scene2 starts after scene1