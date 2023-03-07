function [YTest] = funcion_knn(XTest,XTrain,YTrain,k)


% 1.- CALCULO DEL VECTOR DISTANCIAS ENTRE LA INSTANCIA DE TEST Y TODAS LAS
% INSTANCIAS DE TRAIN


distancia = []; %%Cada columna es un vector distancia a un punto del XTrain. cada fila es el indice
%del Xtrain elegido.
for i= 1:size(XTest,1)
    aux = repmat(XTest(i,:),size(XTrain,1),1)
    distancia= (sum( ((aux-XTrain).^2)'))';
    
% 2.- LOCALIZAR LAS k INSTANCIAS DE XTrain MAS CERCANAS A LA INSTANCIA DE
% TEST BAJO CONSIDERACI�N
    [~,indices] = sort(distancia);
     k_indices_mas_cercanos = indices(1:k);
     
    % 3.- CREAR UN VECTOR CON LAS CODIFICACIONES DE LAS CLASES DE ESAS
% k-INSTANCIAS M�S CERCANAS

      codificaciones = YTrain(k_indices_mas_cercanos);
    % 4.- ANALIZAR ESE VECTOR PARA CONTAR EL N�MERO DE VECES QUE APARECE
% CADA CODIFICACI�N PRESENTE EN EL VECTOR (unique del vector)
    [vec_ind]=unique(codificaciones);
     % 5.- EL VALOR DE YTEST EN LA POSICI�N CORRESPONDIENTE A LA INSTANCIA DE
% XTEST QUE SE EST� ANALIZANDO ES LA CODIFICACI�N DE LA CLASE M�S NUMEROSA.

    contador = zeros(size(vec_ind));
    
    for j = 1:size(vec_ind,1)
        contador(j) = sum(codificaciones == vec_ind(j))
    end
    
       
        [valorMaximo,claseClasificada] = max(contador);
   
    % - SI HAY M�S DE UNA CLASE CON EL N�MERO M�XIMO DE VOTOS, DEVOLVER LA
% CLASE DE LA INSTANCIA M�S CERCANA A LA DE TEST (ENTRE ESAS INSTANCIAS
% DE LAS CLASES M�S NUMEROSAS)
    
        
     if sum(contador==valorMaximo) >1%%Entonces hay mas de una clase.
         
         [~,claseClasificada] = min(distancia(contador==valorMaximo));
         
     end

     YTest(i) = claseClasificada;
end



YTest = YTest';

 


end

