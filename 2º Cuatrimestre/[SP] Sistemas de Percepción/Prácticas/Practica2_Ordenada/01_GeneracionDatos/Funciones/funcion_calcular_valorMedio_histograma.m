%%Una comprobacion realziada correctamente.

function [gMean,numPix] = funcion_calcular_valorMedio_histograma(histograma,gIni,gFin)

%%Corregimos el uint8.

gIni = round(gIni+1);
gFin = round(gFin+1);

numPix = sum(histograma(gIni:gFin)); %%Obtenemos todos los pixeles que conformen el rango.

if numPix > 0
    aux = 0;
    
    for i = gIni:gFin
        aux = aux + i * histograma(i);
    end

    gMean = (aux/numPix)-1;

else
    gMean = [];
end
end

