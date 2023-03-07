function [d1,d2,d12,coef_d12] = funcion_calcula_hiperplanoLDA_2Clases_2_3_Dim(X,Y)
%%PReguntar si esto es correcto.

    [vectorMedias,matrizCov,ProbabilidadPriori] = funcion_ajusta_LDA(X,Y);
    [~,d] = funcion_aplica_LDA(X,vectorMedias,matrizCov,ProbabilidadPriori,unique(Y));
    
    d1 = d(1);
    d2 = d(2);
    d12 = d(1)-d(2);
    
    numDescriptores = size(X,2);
    
    X1 = sym('X1','real');
    X2 = sym('X2','real');
    
    if(numDescriptores == 2)
        X1 = 0; X2 = 0; D = eval(d12);
        X1 = 1; X2 = 0; A = eval(d12)-D;
        X1 = 0; X2 = 1; B = eval(d12)-D;
        coef_d12 = [A,B,D];
    end
    if(numDescriptores==3)
        X3 = sym('X3','real');
        X1 = 0; X2 = 0; X3 = 0; D = eval(d12);
       X1 = 1; X2 = 0; X3 = 0; A = eval(d12)-D;
       X1 = 0; X2 = 1; X3 = 0; B = eval(d12)-D;
        X1 = 0; X2 = 0; X3 = 1; C = eval(d12)-D;
        coef_d12= [A,B,C,D];
    end


    
end

