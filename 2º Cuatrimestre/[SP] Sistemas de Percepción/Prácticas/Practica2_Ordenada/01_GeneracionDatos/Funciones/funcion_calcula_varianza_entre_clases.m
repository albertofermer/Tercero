function [var] = funcion_calcula_varianza_entre_clases(T,h,numPix,gMean)

    
    
    C1 = h(1:T);
    C2 = h(T+1:256);
    
    N1 = sum(C1);
    N2 = sum(C2);
    
    W1 = N1/numPix;
    W2 = N2/numPix;
    
    aux = 0;
    for i = 1:T
        aux = aux + i * h(i);
    end
    M1 = aux;
    
    aux = 0;
    for i = T+1:256
        aux = aux + i * h(i);
    end

    M2 = aux;
    
    var = W1 * (M1 - gMean).^2 + W2 * (M2-gMean).^2;


end

