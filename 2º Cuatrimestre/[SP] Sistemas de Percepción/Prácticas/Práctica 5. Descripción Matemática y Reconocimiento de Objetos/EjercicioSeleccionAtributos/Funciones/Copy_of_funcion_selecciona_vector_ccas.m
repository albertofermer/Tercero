function [espacioCcas, JespacioCcas]=Copy_of_funcion_selecciona_vector_ccas(XoI,YoI,dim)

% % Sacamos los datos de interés:
% 
% [~,numDescriptores] = size(XoI);
% 
% %% 1. Ranking de Características Individuales
% 
% outputs = YoI';
% valoresJ = zeros(1,numDescriptores);
% 
% for j =1:numDescriptores
% 
%         inputs = XoI(:,j);
%         valoresJ(1,j) = indiceJ(inputs,outputs);
% 
% end
% 
% %% 2. Preselección de Características
% 
%     valoresFiltrados = valoresJ(valoresJ > 0.1);
%     [~,indicesFiltrado] = sort(valoresFiltrados, 'descend');
% 
% %% 3. Seleccionar la mejor combinatoria
% 
% vector_tama = size(indicesFiltrado,2);
% combinaciones = combnk(1:vector_tama,dim);
% numero_Combinaciones = size(combinaciones,1);
% 
% for i=1:numero_Combinaciones
% 
%         columnasOI = indicesFiltrado(1,combinaciones(i,:));
%         inputs = XoI(:,columnasOI);
%         valoresFiltrados(i) = indiceJ(inputs,outputs);
% 
% end
% 
% [valoresJOrd,indices] = sort(valoresFiltrados,'descend');
% espacioCcas = indicesFiltrado(combinaciones(indices(1),:));
% JespacioCcas = valoresJOrd(1);




% El umbral que debe considerar la función es el siguiente:
umbral = 0.1;

% Obtenemos el 
[~, numDescriptores] = size(XoI);







valoresJ=zeros(1,numDescriptores);

%% 1. Ranking Individual de Descriptores

    for i=1:numDescriptores
        inputs = XoI(:,i);
        valoresJ(1,i) = indiceJ(inputs,YoI');
    end

%% 2. Preselección de Características.
    %Solo elegimos las que tienen un valor superior al umbral.
    indices = 1:23;
    indices = indices(valoresJ>=umbral)
    valoresJ = valoresJ(valoresJ>=umbral)

%% Selección del vector de características de dimensión dim.

    combinaciones = combnk(indices,dim);
    [numeroCombinaciones, ~]=size(combinaciones);
    Jaux = zeros(numeroCombinaciones,1);
    for i=1:numeroCombinaciones
        espacio=combinaciones(i,:);  
        X=XoI(:,espacio);
        Jaux(i,1) = indiceJ(X,YoI);
    end



   [JespacioCcas, indMaximo]=max(Jaux);

    espacioCcas=combinaciones(indMaximo,:);
        
end