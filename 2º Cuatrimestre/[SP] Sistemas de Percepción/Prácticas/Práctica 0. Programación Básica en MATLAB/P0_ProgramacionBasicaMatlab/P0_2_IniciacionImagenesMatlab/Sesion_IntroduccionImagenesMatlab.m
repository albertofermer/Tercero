% GUARDA EN LA VARIABLE I1, UNA MATRIZ DE 40 FILAS Y 80 COLUMNAS
% CON NÚMEROS ALEATORIOS ENTEROS ENTRE 0 Y 255, TIPO UINT8 

I1 = uint8(255*rand(40,80))


% MUESTRA LA INFORMACIÓN COMO UNA IMAGEN
imshow(I1)

%Representa la información en un eje de coordenadas diferente. Cambia el
%sentido del eje de las ordenadas. Derecha positivo, abajo positivo.
%Fila: Eje Y.
%Columna: Eje X.

% GUARDA EN LA VARIABLE I2, TRES MATRICES DE 40 FILAS Y 80 COLUMNAS 
% CON NÚMEROS ALEATORIOS ENTEROS
% ENTRE 0 Y 255, TIPO UINT8 
I2 = uint8(255*rand(40,80,3));
%I2_1 = I2(:,:,1); imshow(I2_1);
v = I2(6,75,:)
% MUESTRA LA INFORMACIÓN COMO UNA IMAGEN
figure, imshow(I2)
%se pone otro figure para generar otra ventana y no eliminar la anterior.

clear, clc, close all

% Leer imagenes
%La resolución se lee al revés que el valor del workspace.
% La resolución de I es 659x345 px y la de Ic es 500x338 px en color (x3).
I=imread('X.jpg'); % Imagen en escala de grises - ver variable en workspace
imshow(I);
Ic = imread('P1_1.jpg'); % Imagen en color
imshow(Ic)
% GUARDA EN VARIABLES LA DIMENSION DE LAS VARIABLES I, Ic



[NumFilas NumColumnas NumComp] = size (I);
[NumFilas NumColumnas NumComp] = size (Ic);

% Visualizar variables
imshow(I),figure,imshow(Ic)
imtool(I),imtool(Ic) % inspect pixel values y coordenadas

% Acceder al valor de intensidad de coordenadas
close all, imshow(Ic) % MARCAR CUALQUIER PUNTO PARA VER SU INFORMACIÓN
I(3,5) % Fila 3, columna 5
Ic(3,5) % Comprobar que solo hay un valor, ¿que representa ese valor?

Ic(3,5,1) % componente roja
Ic(3,5,2) % componente verde
Ic(3,5,3) % componente azul

% Con imtool(I), obtenemos Pixel info: (141,200) 147
I(200,141)

% Modificar valores

% Poner rectangulo blanco en imagen intensidad EN 100:150,200:400.

Imod = I;
Imod(100:150,200:400) = 255; 
imshow(I),figure,imshow(Imod)
close all;

% Poner rectangulo azul en imagen color

Iazul = Ic;
Iazul(100:150,200:400,1:2) = 0;
Iazul(100:150,200:400,3) = 255;
imshow(Iazul)

Ic_mod = Ic;
Ic_mod(100:150,200:400,1:2) = 0;
Ic_mod(100:150,200:400,3) = 255;
imshow(Ic),figure, imshow(Ic_mod)

% Seleccionar una componente de color
Ir = Ic(:,:,1);
Ig = Ic(:,:,2);
Ib = Ic(:,:,3);
imshow(Ic),figure,imshow(Ir),figure,imshow(Ig),figure,imshow(Ib)
close all;


% Operando con imagenes
I(200,300) % Valor 151

% Guarda en una variable el valor de intensidad de ese pixel +10
A = I(200,300) + 10

% Guarda en otra variable el valor de intensidad de ese pixel +105
B = I(200,300) + 105

% ¿Que observas? MatLab asume que B es unint8 porque I es una matriz de
% unint.

% IMPORTANTE: ANTES DE OPERAR CON VALORES DE IMAGENES HAY QUE HACER UNA
% CONVERSION DEL FORMATO DE LOS DATOS - DE UINT8 A DOUBLE

I = double (I);
B = I(200,300) + 105

%Se puede formalizar una imagen dividiendo todos los valores entre 255.
%obteniendo un rango de 0:1.
%De esta forma se puede admitir una imagen de tipo double.

Id = I/255;
imshow(Id)

% Histogramas
I = uint8(I);
imtool(I)
%pOI -> Point of Interest
pOI = (I==50);
sum(pOI(:)) %Numero de 50 que hay en la matriz
imshow(pOI)
% ¿Cuáles son los posibles valores que puede tener un elemento de la
% matriz?

%conteoValores = zeros(1,256)
%conteoValores(51) = sum(pOI(:)) 
valores_posibles = 0:255;


% Guarda en las posiciones de un vector llamado  conteoValores
% el número de veces que aparece en I cada uno de sus posiles valores.
% Para ello utiliza la función de matlab imhist
% Representa con stem la información de conteoValores

I=imread('X.jpg'); % Imagen en escala de grises - ver variable en workspace

conteoValores = imhist(I);

% Accede al numero de pixeles que hay en I con un nivel de gris 128
conteoValores(128+1)



% Representacion histograma con imhist
imshow(I), figure,imhist(I)
[nf,nc] = size(I);
pOI = zeros(nf,nc);
np = 0;
for i= 1:nf
    for j= 1:nc
        if(I(i,j)<100)
            pOI(i,j) = 1;
            np = np +1;
        end
    end
end

I_100 = (I<100);
sum(I_100(:))
imshow(uint8(255*I_100)); %Es lo mismo que imshow(I_100).
% Representación histograma con stem

stem (valores_posibles, conteoValores, '.r'), hold on, imhist(I)



% EJEMPLO BASICO DE PROCESAMIENTO: SEGMENTAR TODOS LOS PIXELES
% PERTENECIENTES A LETRAS X EN IMAGEN I
% CREAR UNA IMAGEN BINARIA QUE LOCALICE 


[N M] = size(I);

Ibin = double(N,M);

for i=1:N
    for j=1:M
        if I(i,j) < 100
            Ibin(i,j) = 1;
        end
    end
end

% PROGRAMACION MAS EFICAZ: 
Ibin = I < 100; % datos tipo logico
Ibin = double(I<100); % para tenerlos tipo double como antes

% Visualiza la informacion
imshow(uint8(Ibin));

imshow(uint8(255*Ibin));

% GUARDAR LA INFORMACION
save ImagenSegmentada I Ibin
imwrite(uint8(255*Ibin),'ImagenBinaria.tif');
load("ImagenSegmentada.mat")

clear, clc


I = uint8(255*rand(3,4))
I
minimo = min(I(:))

Ic = uint8(255*rand(3,4,3))
Ic

[minimoC,pos] = min(Ic(:))
v = Ic(1,2,:) % vector de las tres matrices con el valor minimo.
imshow(Ic);

%Obtener la imagen complementaria de I.
%Primero en escala de grises y luego en color.
%255 -> 0
%0 -> 255
%Utilizar la ecuación de una recta. y-y_0 = m(x-x_0). Pendiente -1. 
% Ic = 255-I

Icomc = 255 - Ic;
imshow(Icomc);

Icom = 255 - I;
imshow(Icom);

imshow(rgb2lab(Ic));
imshow(rgb2hsv(Ic));






