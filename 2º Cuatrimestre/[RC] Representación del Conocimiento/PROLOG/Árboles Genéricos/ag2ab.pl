
/*

	ab2ag(+B,+G)
	es cierto si G unifica con un árbol genérico que contiene
	los nodos del árbol binario B.

*/


ab2ag(a(E,nil,nil),a(E,[])).
ab2ag(a(E,HI,nil), a(E,[GI])) :- HI \= nil, ab2ag(HI,GI).
ab2ag(a(E,nil,HD), a(E,[GD])) :- HD \= nil, ab2ag(HD,GD).

ab2ag(a(E,HI,HD), a(E,[GI,GD])) :- ab2ag(HI,GI), ab2ag(HD,GD).