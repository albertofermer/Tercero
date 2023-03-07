
%% Generación de Vídeo de Seguimiento
imaqmex('feature','-limitPhysicalMemoryUsage',false);
video = videoinput('winvideo',1,'MJPG_640x480');
preview(video);
video.TriggerRepeat = inf;
video.FrameGrabInterval=1;
fpsMaxWebCam = 15;
fpsTrabajoWebCam = fpsMaxWebCam/video.FrameGrabInterval;

set(video,'LoggingMode','memory')

% Creación archivo de vídeo.
aviobjIN = VideoWriter('VideoSeguimiento_Verde.avi','Uncompressed AVI');
aviobjIN.FrameRate = fpsTrabajoWebCam;
aviobjOUT.FrameRate = fpsTrabajoWebCam;
duracion = 30;
numFramesGrabacionIN = duracion*aviobjIN.FrameRate;
open(aviobjIN);
start(video);
while(video.FramesAcquired<numFramesGrabacionIN)

    I = getdata(video,1); I = imresize(I,0.5);
    writeVideo(aviobjIN,I);
end
stop(video)
close(aviobjIN);

%% Generación de Imágenes de Calibración
num_Repeticiones = 10;
Imagenes_Calibracion_SinObjeto = uint8(zeros(240,320,3,num_Repeticiones));
Imagenes_Calibracion_Verde = uint8(zeros(240,320,3,num_Repeticiones));

preview(video)
start(video)
for i=1:num_Repeticiones
    pause;
    I = getsnapshot(video); I = imresize(I,0.5);
    Imagenes_Calibracion_SinObjeto(:,:,:,i) = I;
end
stop(video)


start(video)
preview(video)
for i=1:num_Repeticiones
    pause;
    I = getsnapshot(video); I = imresize(I,0.5);
    Imagenes_Calibracion_Verde(:,:,:,i) = I;
end
stop(video)

for i=1:num_Repeticiones
    subplot(1,2,1), imshow(Imagenes_Calibracion_SinObjeto(:,:,:,i)), title("Imagen " + i); hold on
    pause;
end
close all;

close all;
for i=1:num_Repeticiones
    subplot(1,2,1), imshow(Imagenes_Calibracion_Verde(:,:,:,i)), title("Imagen " + i); hold on
    pause;
end
close all;


save('./Variables Generadas/ImagenesCalibracionV.mat',"Imagenes_Calibracion_Verde","Imagenes_Calibracion_SinObjeto");


