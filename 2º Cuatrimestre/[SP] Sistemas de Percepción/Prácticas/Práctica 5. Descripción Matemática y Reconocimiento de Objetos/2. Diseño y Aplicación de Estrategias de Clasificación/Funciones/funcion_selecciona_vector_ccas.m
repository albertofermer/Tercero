function [espacioCcas, JespacioCcas]=funcion_selecciona_vector_ccas(XoI,YoI,dim)

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

[numMuestras, numDescriptores] = size(XoI);




combinaciones=combnk(1:numDescriptores,dim);

[nf nc]=size(combinaciones);

Jaux=zeros(nf,1);

    for i=1:nf
        espacio=combinaciones(i,:);  
        X=XoI(:,espacio);
        Jaux(i,1)=indiceJ(X,YoI);
    end

   espacioCcas = combinaciones(Jaux>=umbral,:);

   [JespacioCcas indMaximo]=max(Jaux);

    espacioCcas=combinaciones(indMaximo,:);
        
end