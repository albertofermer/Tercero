function representa_muestras_clasificacion_binaria(X,Y,nombresProblema)

    valoresClases = unique(Y);
    numClases = length(valoresClases);
    numAtributos = size(X,2);
    
    for i=1:numClases
        Xi = X(Y==valoresClases(i),:);
        if numAtributos == 2
            plot(Xi(:,1),Xi(:,2),"*")
            hold on;
        else 
            plot3(Xi(:,1),Xi(:,2),Xi(:,3),"*")
        end
    end
    if numClases > 2 
        legend("hiperplano","2",...
            "3","4","5");
    else 
        legend("plano","1",...
            "7");
    end
end

