%%Falta que comprobar.
%%NO FUNCIONA OTSU.
function [T] = funcion_Otsu(Imagen)

    gIni = 0;
    gFin=255;
    h=imhist(Imagen);
    [gMean,numPix] = funcion_calcular_valorMedio_histograma(h,gIni,gFin);
    
    var = zeros(256,1);
    
    for g= 2:255
	T = g;
	var(g) = funcion_calcula_varianza_entre_clases(T,h,numPix,gMean);
    end
    
    [~,indice] = max(var)

    T_outsu = indice-1;


end

