 % 
% 1. Leer las imagenes de calibración y aplicarle el clasificador del
% apartado 3).
% 
% 2. Decidir el radio de las esferas. 
%    (He elegido el primer radio porque es el que más se ajusta a las 
%       imágenes de calibración).
% 
%   
%
% 3. Usar vwareaopen() para filtrar las agrupaciones de píxeles menores del
% fondo. Hay que tener en cuenta la agrupación mínima de píxeles. Para
% ello:
% 
% En la imágen más alejada calcular los números de píxeles con roipoly y
% hacer un sum de esa imagen binaria. (6)
% 
%   Tendremos en cuenta el 25%, 50% y el 75% de esos píxeles.
%   (numPixelesAnalisis)
% 
% 
% 4. Guardar el numero de píxeles mínimo y el datosMultiplesEsferas.
% 

load("./Variables Requeridas/datosMultiplesEsferas.mat");
load("./Variables Requeridas/ImagenesCalibracionV.mat");
addpath('./Funciones');


% Umbral de distancia -> primer radio.
for j=1:size(Imagenes_Calibracion_Verde(),4)
    figure, I = Imagenes_Calibracion_Verde(:,:,:,j);
    subplot(2,2,1)
    imshow(I);
    title(["Imagen Original" num2str(j)])
    for i=1:size(datosEsferas(:,1:3),2)
        I1 = funcion_visualiza(I,calcula_deteccion_multiples_esferas_en_imagen(I,datosEsferas(:,3+i),datosEsferas(:,1:3)),[255 0 0],false);
        subplot(2,2,i+1)
        imshow(I1), hold on;
        %title(['Esfera de radio: ' num2str(datosEsferas(:,1))])
    end
end

I_numPix_min = roipoly(Imagenes_Calibracion_Verde(:,:,:,6));
pause;
numPix = sum(I_numPix_min,"all"); % agrupacion de píxeles mínima.
%numPix = 140;
porcentajesPixeles = round([numPix * .25, numPix * .5, numPix * .75]);


for j=1:size(Imagenes_Calibracion_Verde(),4)
    figure, I = Imagenes_Calibracion_Verde(:,:,:,j);
    
    Ib_deteccion_distancia = calcula_deteccion_multiples_esferas_en_imagen(I,datosEsferas(:,4),datosEsferas(:,1:3));
    Io = funcion_visualiza(I,Ib_deteccion_distancia,[255 0 0], false);
    subplot(2,2,1), imshow(Io);

    for i=1:length(porcentajesPixeles)
        Ib_new = bwareaopen(calcula_deteccion_multiples_esferas_en_imagen(I,datosEsferas(:,4),datosEsferas(:,1:3)),porcentajesPixeles(i));
        Io = funcion_visualiza(I,Ib_new,[255 0 0],false);
        subplot(2,2,i+1)
        imshow(Io), hold on;
        %title(['Esfera de radio: ' num2str(datosEsferas(:,1))])
    end
end

datosMultiplesEsferas = datosEsferas;
numPix = porcentajesPixeles(3);

save('./Variables Generadas/parametros_clasificador.mat',"numPix","datosMultiplesEsferas");
rmpath('./Funciones');