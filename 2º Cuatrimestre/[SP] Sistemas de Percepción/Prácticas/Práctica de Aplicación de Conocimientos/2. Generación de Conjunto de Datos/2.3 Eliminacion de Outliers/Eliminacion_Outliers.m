% Eliminar valores atipicos.
% 

ruta = '../2.1 Extraccion de Datos/Variables Generadas/';
fichero = 'XY_Verde_Reducido.mat';
load([ruta fichero]);

addpath('./Funciones');
X = X_Verde;
Y= Y_Verde;
posClaseInteres = 2;
[Xo,Yo] = detecta_outliers(X,Y,posClaseInteres);

save('./Variables_Generadas/conjunto_de_datos', "Xo","Yo")

rmpath('./Funciones');





