function g_otsu = funcion_otsu(I)

    %le pasamos una imagen y nos devuelve un umbral. 
    h = imhist(uint8(I));

    gIni = 1; gFin = 256;
    [gMedio,numPix] = calcular_valor_medio_region_histograma(h,gIni,gFin);

    var = zeros(256,1);

    for g=2:255
	    T = g;
	    var(g) = calcula_varianza_entre_clases(T,h,numPix,gMedio);
    end

    [~,indice] = max(var);

    g_otsu = indice-1;

end