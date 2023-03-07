% Metodo que se le pasa una imagen en double y calcula:
%
function [g_MinEntreMax,gmax1,gmax2] = funcion_min_entre_max(Id)

    h = imhist(Id);
    [hmax,gmax1] = max(h);

    metrica = zeros(256,1);

    for g=1:256
        metrica(g) = h(g)*(g-gmax1)^2;
    end
    
    [metricaMax,gmax2] = max(metrica);

    hmod = h;
    aux = gmax1
    if gmax1 > gmax2
	    gmax1=gmax2;
	    gmax2 = aux;
    end
    hmod(1:gmax1) = hmax;
    hmod(gmax2:256) = hmax;
    [g_MinEntreMax,T] = min(hmod);

    T = T-1;


end

