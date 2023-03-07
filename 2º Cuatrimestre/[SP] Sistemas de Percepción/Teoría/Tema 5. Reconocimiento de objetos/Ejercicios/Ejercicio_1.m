%% Ejercicio 1 
%
% Teniendo en cuenta la muestra de la tabla, diseñar un clasificador de 
% mínima distancia Euclídea. 
% 


datosX1_C1 = [1 2 2 2 2 3 3 4 5 1];
datosX2_C1 = [3 1 2 3 4 2 3 3 2 2];
datos_C1 = [datosX1_C1 ; datosX2_C1]';

M1 = mean(datos_C1)';

datosX1_C2 = [ 4 5 5 4 6 6 6 7 4 8];
datosX2_C2 = [ 5 5 6 7 5 6 7 6 6 7];
datos_C2 = [datosX1_C2 ; datosX2_C2]';

M2 = mean(datos_C2)';

Y = [1 ; 2];

% Como es un problema de clasificación, debemos realizar una función de
% decisión para cada clase del problema (2 clases).

x1 = sym('x1','real');
x2 = sym('x2','real');
X = [x1;x2];
d1 = expand(-(X-M1)' * (X-M1) );
d2 = expand(-(X-M2)' * (X-M2) );
% Calcula la función discriminante.
d12 = d1 - d2;


x1 = 0; x2 = 0;
C = eval(d12);

x1 = 1 ; x2 = 0;
A = eval(d12)-C;


 %Representa los datos
plot(datosX1_C1,datosX2_C1,'*r'), hold on, % Clase 1
plot(datosX1_C2,datosX2_C2,'*b'), hold on, % Clase 2
plot([215/24,0],[0,191/28])                % 2 puntos de la recta d12 == 0
axis([0,10,0,10])                          % Los ejes de 0 a 10.
