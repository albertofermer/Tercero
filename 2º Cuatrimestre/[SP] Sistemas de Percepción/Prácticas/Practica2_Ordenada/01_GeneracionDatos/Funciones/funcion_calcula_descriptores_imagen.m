function [XImagen] = funcion_calcula_descriptores_imagen(Ietiq,N)
% 23 Descriptores a utilizar:
% Compacticidad: 1
% Excentricidad: 2
% Solidez_CHull(Solidity): 3
% Extension_BBox(Extent): 4
% Extension_BBox(Invariante Rotacion): 5
% Hu1-Hu7: 6-12
% DF1-DF10: 13-22
% NumEuler: 23

% %X contendrá tantas filas como objetos haya en todas las imágenes de entrenamiento
% disponibles y tantas columnas como descriptores matemáticos se están calculando (en
% nuestro caso 23).

XImagen = zeros(N,23); %Tantas filas como objeto 23 columnas.
stats = regionprops(Ietiq,'Area','Perimeter','Eccentricity','Solidity','Extent','EulerNumber');
areas = cat(1,stats.Area);
perimetros = cat(1,stats.Perimeter);
XImagen(:,1) = (perimetros.^2)./areas;
XImagen(:,2) = cat(1,stats.Eccentricity);
XImagen(:,3) = cat(1,stats.Solidity);
XImagen(:,4) = cat(1,stats.Extent);

X = [];

for i=1:N
        
        Ibin_i = Ietiq == i;
        extentIR = funcion_calcula_extent(Ibin_i);
        m = Funcion_Calcula_Hu(Ibin_i);
        DF = Funcion_Calcula_DF(Ibin_i,10);
        X(i,:) = [extentIR m' DF'];
        
end
XImagen(:,5:22) = X;
 XImagen(:,23) = cat(1,stats.EulerNumber);
end

