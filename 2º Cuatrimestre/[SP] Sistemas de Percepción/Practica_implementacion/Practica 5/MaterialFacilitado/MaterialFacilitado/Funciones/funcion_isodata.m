function  funcion_isodata(I,umbralParada)
    valorMedio = mean(I(:));
    gIni = 1; gFin= 256;
    T = calcula_valor_media_region_histograma(h,gIni,gFin);
    while true
        gIni=1;gFin=round(T);
        gMean1= calcula_valor_medio_region_histograma(h,gIni,gFin);

        gIni = round(T)+1;gFin = 256;
        gMean2 = calcula_valor_medio_region_histograma(h,gIni,gFin);

        newT = mean([gMean1 gMean2]);

        if abs(T-newT) <=umbralParada
            break;
        end
    end
    T = newT;
    T = T-1;
end