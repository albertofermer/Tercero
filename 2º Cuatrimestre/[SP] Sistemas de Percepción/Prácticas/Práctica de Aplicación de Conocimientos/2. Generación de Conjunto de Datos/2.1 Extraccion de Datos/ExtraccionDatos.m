%% Recupero las imagenes de Calibración del ejercicio anterior.
load('./Variables Requeridas/ImagenesCalibracionV.mat');
%% Extracción de Datos para el vídeo de color Verde.

ROI_Verde = zeros(size(Imagenes_Calibracion_Verde(:,:,1,:)));
ROI_Verde_Fondo = zeros(size(Imagenes_Calibracion_Verde(:,:,1,1)));
% Seleccionar los píxeles que nos interesan
for i=1:size(Imagenes_Calibracion_Verde,4)-5
    ROI_Verde(:,:,:,i) = roipoly(Imagenes_Calibracion_Verde(:,:,:,i));
end
% Muestra las imágenes binarias de la selección
for i=1:size(Imagenes_Calibracion_Verde,4)
   imshow(ROI_Verde(:,:,:,i));
   pause;
end

for i=1:2
    ROI_Verde_Fondo(:,:,:,i) = roipoly(Imagenes_Calibracion_SinObjeto(:,:,:,i));
end

for i=1:size(Imagenes_Calibracion_Verde,4)
   imshow(ROI_Verde_Fondo(:,:,:,i));
   pause;
end
% Extracción de Datos
    % Creo los conjuntos vacíos
 DatosColor_Verde = [];
 DatosFondo_Verde = [];

% Para todas las imágenes de calibración
for i=1:size(Imagenes_Calibracion_Verde,4)
    % Extraigo las matrices de intensidad de los colores RGB
R = Imagenes_Calibracion_Verde(:,:,1,i);
G = Imagenes_Calibracion_Verde(:,:,2,i);
B = Imagenes_Calibracion_Verde(:,:,3,i);
    % Selecciono los píxeles que interesan y guardo sus valores RGB
    DatosColor_Verde = [DatosColor_Verde;i*ones(size(R(ROI_Verde(:,:,:,i)==1),1),1) double(R(ROI_Verde(:,:,:,i)==1)), double(G(ROI_Verde(:,:,:,i)==1)), double(B(ROI_Verde(:,:,:,i)==1))]; % id_imagen - R - G - B
    % Selecciono los píxeles que no interesan y guardo sus valores RGB
end
for i=1:size(ROI_Verde_Fondo,4)
     DatosFondo_Verde = [DatosFondo_Verde;i*ones(size(R(ROI_Verde_Fondo(:,:,:,i)==1),1),1) double(R(ROI_Verde_Fondo(:,:,:,i)==1)), double(G(ROI_Verde_Fondo(:,:,:,i)==1)), double(B(ROI_Verde_Fondo(:,:,:,i)==1))]; % id_imagen - R - G - B
end

    % Guardo los valores RGB de todos los píxeles
    X_Verde = [DatosColor_Verde(:,2:4);DatosFondo_Verde(:,2:4)]; % Selecciono R - G - B
    % Los píxeles que componen el área de interés se representan con 1 y
    % los que no con un 0.
    Y_Verde = [ones(size(DatosColor_Verde,1),1);zeros(size(DatosFondo_Verde,1),1)]; % Concateno un vector columna de 1's del tamaño de DatosColor con otro de ceros del tamaño de DatosFondo
    % Representación de los datos.
    datosRGBColor_Verde = X_Verde(Y_Verde==1,:);
    datosRGBFondo_Verde = X_Verde(Y_Verde==0,:);
    plot3(datosRGBColor_Verde(:,1),datosRGBColor_Verde(:,2),datosRGBColor_Verde(:,3),'.r'), hold on;
    plot3(datosRGBFondo_Verde(:,1),datosRGBFondo_Verde(:,2),datosRGBFondo_Verde(:,3),'.b'), grid on;
    xlabel('Rojo','Color','r'); ylabel('Verde','Color','g'); zlabel('Azul','Color','b'); 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

save('./Variables Generadas/XY_Verde.mat');
