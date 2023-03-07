function [NumPix,G] = funcion_calcula_media_region_histograma(h,gIni,gFin)

if (gIni < 1)

    gIni = 1;

end

if(gFin > 256)
    gFin = 256;
end
NumPix = sum(h(gIni:gFin));

    if(NumPix>0)
        aux = 0;
        for g = gIni:gFin
            aux = aux + g*h(g);
        end
        G = (aux / NumPix) - 1;
    else 
        G = [];
    end
end