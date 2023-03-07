function [gMean,numPix] = calcular_valor_medio_region_histograma(h,gIni,gFin)
%En este caso se ha comentado porque en la funcion otsu ya se le pone como
%debe de ser y no es necesario sumarle 1 
	gIni = round(gIni);
	gFin = round(gFin); %al tratar con histogramas es necesario 
	%considerar las posiciones entre 1 y 256. 
	numPix = sum(h(gIni:gFin));
	
	if numPix > 0
		aux = 0;
    for g=gIni:gFin
		aux = aux+g*h(g);
    end
        
		gMean = (aux/numPix);
	else
		gMean = [];
	end

end