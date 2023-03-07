function [d1,d2,d12,coef_d12] = funcion_calcula_hiperplanoLDA_separacion(X,Y)
%%PReguntar si esto es correcto.

    [vectorMedias,mCovar,Ppriori] = funcion_ajusta_LDA(X,Y);
    [~,d] = funcion_aplica_LDA(X,vectorMedias,mCovar,Ppriori,unique(Y));
    
    d1 = d(1);
    d2 = d(2);
    d12 = d(1)-d(2);
    
    numCaracteris = size(X,2);
    
    x1 = sym('x1','real');
    x2 = sym('x2','real');
    
    if(numCaracteris == 2)
        x1 = 0; x2 = 0; D = eval(d12);
        x1 = 1; x2 = 0; A = eval(d12)-D;
        x1 = 0; x2 = 1; B = eval(d12)-D;
        coef_d12 = [A,B,D];
    end
    if(numCaracteris==3)
        x3 = sym('x3','real');
        x1 = 0; x2 = 0; x3 = 0; D = eval(d12);
       x1 = 1; x2 = 0; x3 = 0; A = eval(d12)-D;
       x1 = 0; x2 = 1; x3 = 0; B = eval(d12)-D;
        x1 = 0; x2 = 0; x3 = 1; C = eval(d12)-D;
        coef_d12= [A,B,C,D];
    end


    
end

