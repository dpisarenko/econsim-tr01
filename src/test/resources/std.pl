containsOnly(X,Y) :- forall(sub_atom(X,_,1,_,C), sub_atom(Y,_,1,_,C)).