%%Por comprobar.


function [T] = funcion_UmbralizacionHistograma(Imagen)

h = imhist(Imagen);
[hMax,g1] = max(h);
metrica=[];
for g=1:256
	metrica(g) = h(g)*(g-g1)^2;

end

[metricaMax,g2] = max(metrica);

hmod = h;


aux = g1;

	if g1 > g2
		g1 = g2;
		g2 = aux;
	end



hmod(1:g1) = hMax;%%He modificado esto. le he puesto un 1 en vez de un 0.
hmod(g2:256) = hMax;
[valorMinimo,T] = min(hmod);

T = T-1;

end

