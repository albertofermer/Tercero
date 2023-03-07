function V = calcula_varianza_entre_clases(T,h,numPix,G)
    
    N1 = 0; % Calculo el número de píxeles de la primera clase.
    for g=1:T
        N1 = N1 + h(g);
    end
    
    N2 = 0; % Calculo el número de píxeles de la segunda clase.
    for g=T+1:256
        N2 = N2 + h(g);
    end
    
    if(N1 == 0 || N2 == 0)

        V = 0;

    else
    w1 = N1/numPix; % Probabilidad de ocurrencia de la primera clase
    w2 = N2/numPix; % Probabilidad de ocurrencia de la segunda clase
    
    [~,g1] = funcion_calcula_media_region_histograma( ...
                                                    h,1,T); % Valor de gris 
                                                            % medio de la 
                                                            % primera clase
                                                            
    [~,g2] = funcion_calcula_media_region_histograma( ...
                                                 h,T+1,256); %Valor de
                                                             %gris medio
                                                             %de la
                                                             %segunda clase
    
    V = w1*((g1-G)^2) + w2*((g2-G)^2); % Varianza entre clases
    end

end