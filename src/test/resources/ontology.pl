
isSite(Url) :- string(Url).
guest_post_published(GuestPostId, Date, Site, Url) :-
 string(GuestPostId),
 date(Date),
 isSite(Site),
 string(Url),
 \+(guestPostPublished(GuestPostId, _, _, _)).

allElementsDifferent([]).
allElementsDifferent([H|T]):-
    \+member(H,T),
    allElementsDifferent(T).

guest_post_ids_unique :-
    findall(Id, guest_post_published(Id,_,_,_), Ids),
    write(Ids),
    allElementsDifferent(Ids).