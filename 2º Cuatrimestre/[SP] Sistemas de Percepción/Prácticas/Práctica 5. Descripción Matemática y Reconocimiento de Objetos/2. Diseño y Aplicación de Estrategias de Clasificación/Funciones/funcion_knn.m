function [YTest] = funcion_knn(XTest,XTrain,YTrain,k)


%% 1.- CÁLCULO DEL VECTOR DISTANCIAS ENTRE EL TEST Y TODAS LAS
% INSTANCIAS DE TRAIN


distancia = []; 
for i= 1:size(XTest,1)
    aux = repmat(XTest(i,:),size(XTrain,1),1);
    distancia= (sum( ((aux-XTrain).^2)'))';
    
%% 2.- LOCALIZAR LAS k INSTANCIAS DE XTrain MÁS CERCANAS A LA INSTANCIA DE
% TEST BAJO CONSIDERACIÓN
    [~,indices] = sort(distancia);
     k_indices_mas_cercanos = indices(1:k);
     
%% 3.- CREAR UN VECTOR CON LAS CODIFICACIONES DE LAS CLASES DE ESAS
% k-INSTANCIAS MÁS CERCANAS

      codificaciones = YTrain(k_indices_mas_cercanos);
%% 4.- ANALIZAR ESE VECTOR PARA CONTAR EL NÚMERO DE VECES QUE APARECE
% CADA CODIFICACIÓN PRESENTE EN EL VECTOR.
    [vector_indices]=unique(codificaciones);

%% 5.- EL VALOR DE YTEST EN LA POSICIÓN CORRESPONDIENTE A LA INSTANCIA DE
% XTEST QUE SE ESTÁ ANALIZANDO ES LA CODIFICACIÓN DE LA CLASE MÁS NUMEROSA.

    contador = zeros(size(vector_indices));
    
    for j = 1:size(vector_indices,1)
        contador(j) = sum(codificaciones == vector_indices(j));
    end
    
       
        [valorMaximo,claseClasificada] = max(contador);
   
% - EN CASO DE EMPATE, DEVOLVER LA CLASE DE LA INSTANCIA MÁS CERCANA A LA
% DE TEST.
    
        
     if (sum(contador==valorMaximo) >1)
         
         [~,claseClasificada] = min(distancia(contador==valorMaximo));
         
     end

     YTest(i) = claseClasificada;
end



YTest = YTest';

 


end

