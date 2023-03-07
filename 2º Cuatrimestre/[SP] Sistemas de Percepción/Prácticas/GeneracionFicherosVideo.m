% Generación y Lectura de archivos de video con matlab

imaqmex('feature','-limitPhysicalMemoryUsage',false);
video = videoinput('winvideo',1,'MJPG_640x480');
preview(video);
video.TriggerRepeat = inf;
video.FrameGrabInterval=1;
fpsMaxWebCam = 15;
fpsTrabajoWebCam = fpsMaxWebCam/video.FrameGrabInterval;

set(video,'LoggingMode','memory')

% Creación archivo de vídeo.
aviobjIN = VideoWriter('Entrada.avi','Uncompressed AVI');
aviobjOUT = VideoWriter('Salida.avi','Uncompressed AVI');
aviobjIN.FrameRate = fpsTrabajoWebCam;
aviobjOUT.FrameRate = fpsTrabajoWebCam;
duracion = 10;
numFramesGrabacionIN = duracion*aviobjIN.FrameRate;
numFramesGrabacionOUT = duracion*aviobjOUT.FrameRate;

open(aviobjIN);
open(aviobjOUT);
start(video);

while(video.FramesAcquired<numFramesGrabacionOUT)

    I = getdata(video,1);
    writeVideo(aviobjIN,I);
    writeVideo(aviobjOUT,255-I);
end

stop(video)
close(aviobjIN);
close(aviobjOUT);


% Cambiando el LoggingMode. Secuencia de video con
% una caja negra de 3x3 moviendose aleatoriamente.

video = videoinput('winvideo',1,'MJPG_640x480');
preview(video);
video.TriggerRepeat = inf;
video.FrameGrabInterval=3;
fpsMaxWebCam = 30;
fpsTrabajoWebCam = fpsMaxWebCam/video.FrameGrabInterval;

set(video,'LoggingMode','disk&memory')
video.DiskLogger = aviobjIN;

% Creación archivo de vídeo.
aviobjIN = VideoWriter('Entrada.avi','Uncompressed AVI');
aviobjOUT = VideoWriter('Salida.avi','Uncompressed AVI');
aviobjIN.FrameRate = fpsTrabajoWebCam;
aviobjOUT.FrameRate = fpsTrabajoWebCam;
duracion = 10;
numFramesGrabacionIN = duracion*aviobjIN.FrameRate;
%numFramesGrabacionOUT = duracion*aviobjOUT.FrameRate;

open(aviobjIN);
open(aviobjOUT);
Resolucion = video.VideoResolution;
Valores = rand(numFramesGrabacionIN,1);
NumFilas = Resolucion(2);
NumColumnas = Resolucion(1);

ValoresX= round((NumColumnas-1)*Valores)+1;
Valores = rand(numFramesGrabacionIN,1);
ValoresY= round((NumFilas-1)*Valores)+1;

start(video);

for i=1:numFramesGrabacionIN;

    x = ValoresX(i); y = ValoresY(i);
    I = getdata(video,1);
    imshow(I), hold on, plot(x,y,'.r');

    if (y>2 && y<NumFilas-1) && (x>2 && x<NumColumnas-1)
        I(y-2:y+2, x-2:x+2,:) = 0;
    end
    writeVideo(aviobjIN,I);
    
end

stop(video)
%close(aviobjIN);
%close(aviobjOUT);

%% Lectura de Vídeo


video = videoinput('winvideo',1,'MJPG_640x480');
preview(video);
video.TriggerRepeat = inf;
video.FrameGrabInterval=3;
fpsMaxWebCam = 30;
fpsTrabajoWebCam = fpsMaxWebCam/video.FrameGrabInterval;

clear, clc;
aviobjIN = VideoReader('Entrada.avi');
get(aviobjIN);

fpsTrabajoWebCam = 15;
aviobjIN.FrameRate = fpsTrabajoWebCam;
aviobjOUT.FrameRate = fpsTrabajoWebCam;
duracion = 10;
numFramesGrabacionIN = duracion*aviobjIN.FrameRate;

NumFilasFrame = aviobjIN.Height;
NumeroColumnasFrame = aviobjIN.Width;
FPS = aviobjIN.FrameRate;

% Leer un Frame específico


I1 = readFrame(aviobjIN);
aviobjIN
I2 = readFrame(aviobjIN);
aviobjIN

NumFrames = 10;
aviobjIN.CurrentTime=(NumFrames-1)/aviobjIN.FrameRate;
I10 = readFrame(aviobjIN);

aviobjO = videoWriter('Ejemplo.avi','Uncompressed AVI');
aviobjO.FrameRate = FPS;
open(aviobjO)

aviobjIN.CurrentTime = 0;
for i=1:NumeroFrames
    I=readFrame(aviobjIN);
    writeVideo(aviobjO,255-I);
end

close(aviobjO);









