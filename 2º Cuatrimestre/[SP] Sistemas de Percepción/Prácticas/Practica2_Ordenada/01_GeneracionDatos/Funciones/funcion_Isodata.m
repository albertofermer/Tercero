%%No comprobado, con dudas.


function [T] = funcion_Isodata(Imagen)

    umbralParada = 0;

    T = mean(Imagen(:));
    gIni = 0;
    gFin = 255;
    h = imhist(Imagen);
    gMean = funcion_calcular_valorMedio_histograma(h,gIni,gFin);
    %%Primer umbral.
    while(true)
        
        gIni = 0;
        gFin = round(T);
        
        gMean1 = funcion_calcular_valorMedio_histograma(h,gIni,gFin);
        
        gIni = round(T)+1;
        gFin = 255;
        
        gMean2 = funcion_calcular_valorMedio_histograma(h,gIni,gFin);
        
        newT = mean([gMean1 gMean2]);
        
        if (abs(T-newT) <= umbralParada)
            break;
        else
            T = newT;
        end
        
    end

    %%Preguntar porque tengo que corregir aqui, yo creo que no hace falta.
end

