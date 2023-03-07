function [YTest] = funcion_knn(XTest,XTrain,YTrain,k)


% 1.- CALCULO DEL VECTOR DISTANCIAS ENTRE LA INSTANCIA DE TEST Y TODAS LAS
% INSTANCIAS DE TRAIN


distancia = []; %%Cada columna es un vector distancia a un punto del XTrain. cada fila es el indice
%del Xtrain elegido.
for i= 1:size(XTest,1)
    aux = repmat(XTest(i,:),size(XTrain,1),1)
    distancia= (sum( ((aux-XTrain).^2)'))';
    
% 2.- LOCALIZAR LAS k INSTANCIAS DE XTrain MAS CERCANAS A LA INSTANCIA DE
% TEST BAJO CONSIDERACIÓN
    [~,indices] = sort(distancia);
     k_indices_mas_cercanos = indices(1:k);
     
    % 3.- CREAR UN VECTOR CON LAS CODIFICACIONES DE LAS CLASES DE ESAS
% k-INSTANCIAS MÁS CERCANAS

      codificaciones = YTrain(k_indices_mas_cercanos);
    % 4.- ANALIZAR ESE VECTOR PARA CONTAR EL NÚMERO DE VECES QUE APARECE
% CADA CODIFICACIÓN PRESENTE EN EL VECTOR (unique del vector)
    [vec_ind]=unique(codificaciones);
     % 5.- EL VALOR DE YTEST EN LA POSICIÓN CORRESPONDIENTE A LA INSTANCIA DE
% XTEST QUE SE ESTÁ ANALIZANDO ES LA CODIFICACIÓN DE LA CLASE MÁS NUMEROSA.

    contador = zeros(size(vec_ind));
    
    for j = 1:size(vec_ind,1)
        contador(j) = sum(codificaciones == vec_ind(j))
    end
    
       
        [valorMaximo,claseClasificada] = max(contador);
   
    % - SI HAY MÁS DE UNA CLASE CON EL NÚMERO MÁXIMO DE VOTOS, DEVOLVER LA
% CLASE DE LA INSTANCIA MÁS CERCANA A LA DE TEST (ENTRE ESAS INSTANCIAS
% DE LAS CLASES MÁS NUMEROSAS)
    
        
     if sum(contador==valorMaximo) >1%%Entonces hay mas de una clase.
         
         [~,claseClasificada] = min(distancia(contador==valorMaximo));
         
     end

     YTest(i) = claseClasificada;
end



YTest = YTest';

 


end

