% 
% 
% Carga la información de datosMultiplesEsferas_Clasificador y el numPíx.
% 
% 
% Requiere solo el vídeo y un script que lea el vídeo y le aplique el
% clasificador a cada frame del vídeo.
% 
% Por cada frame llama a calcula_deteccion_multiples_esferas_en_imagen y a
% la funcion_visualiza con la imagen binaria generada anteriormente.
% 
% Por cada frame llama a calcula_deteccion_multiples_esferas_en_imagen,
% eliminar las agrupaciones menores que numPix con vwareaopen.
% 
% Una vez tenemos la imagen binaria podemos aplicarle cualquier algoritmo
% como calcula_centroides o funcion_visualiza.
% 
% Ver todos los centroides, el de la agrupación mayor, color AND movimiento(Practica3.m)...
% 

%% Detección Objeto Por Color
load('./Variables Requeridas/parametros_clasificador.mat');
video = VideoReader('./Variables Requeridas/VideoSeguimiento_Verde.avi');
get(video);
addpath('./Funciones');

output = VideoWriter('Deteccion_Verde.mp4', 'MPEG-4');
output.FrameRate = video.FrameRate;
open(output);

video.CurrentTime = 0;
for i=1:video.NumFrames

    I = readFrame(video);
    Ib = bwareaopen(calcula_deteccion_multiples_esferas_en_imagen(I,datosMultiplesEsferas(:,4),datosMultiplesEsferas(:,1:3)),numPix);
    Io = funcion_visualiza(I,Ib,[255 0 0], false);
    imshow(Io);
    writeVideo(output,Io);

end

close(output);

%% Detección de Objeto por Color y Centroide
load('./Variables Requeridas/parametros_clasificador.mat');
video = VideoReader('./Variables Requeridas/VideoSeguimiento_Verde.avi');
get(video);
addpath('./Funciones');

output = VideoWriter('Deteccion_Verde_Centroide.avi', 'MPEG-4');
output.FrameRate = video.FrameRate;
open(output);

video.CurrentTime = 0;
for i=1:video.NumFrames

    I = readFrame(video);
    Ib = bwareaopen(calcula_deteccion_multiples_esferas_en_imagen(I,datosMultiplesEsferas(:,4),datosMultiplesEsferas(:,1:3)),numPix);
    % [Ietiq,N] = bwlabel(Ib);
    stats = regionprops(Ib,'Area','Centroid'); 
        centroids = cat(1,stats.Centroid);
        areas = cat(1,stats.Area);

    %Io = funcion_visualiza(I,Ib,[255 0 0], false);
    if(~isempty(centroids))
            [~,pos] = max(areas);

            x = round(centroids(pos,2));
            y = round(centroids(pos,1));
        Ibcent = zeros(size(I,1),size(I,2));
        Ibcent(x-1:x+1,y-1:y+1) = 1;
        
        Io = funcion_visualiza(I,Ibcent & Ib,[255 0 0], false);
       
    else
        Ibcent = zeros(size(I,1),size(I,2));
        Ibcent(1:3,1:3) = 1;
        Io = funcion_visualiza(I,Ibcent & Ib,[255 0 0], false);
        
    end
    imshow(Io), hold on;
    writeVideo(output,Io);

end

close(output);

%% Detecta Color En Memoria
load('./Variables Requeridas/parametros_clasificador.mat');
addpath('./Funciones');
 imaqmex('feature','-limitPhysicalMemoryUsage',false);
    datos = imaqhwinfo('winvideo');
    video = videoinput('winvideo',1,'MJPG_640x480');
    video.TriggerRepeat = inf;
    video.FramesPerTrigger = 3;
    video.FrameGrabInterval = 3;

    start(video);
    while(video.FramesAcquired<150)
    I = getdata(video,1);  I = imresize(I,0.5);
    Ib = bwareaopen(calcula_deteccion_multiples_esferas_en_imagen(I,datosMultiplesEsferas(:,4),datosMultiplesEsferas(:,1:3)),numPix);
    Io = funcion_visualiza(I,Ib,[255 0 0], false);
    imshow(Io);
    end

    stop(video);
    flushdata(video);

    rmpath('./Funciones');