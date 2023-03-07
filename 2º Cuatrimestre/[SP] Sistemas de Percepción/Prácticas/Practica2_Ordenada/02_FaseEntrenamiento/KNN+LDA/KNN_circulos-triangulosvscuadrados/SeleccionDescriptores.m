%%NOSE QUE SIGNIFICA EN ESTA ETAPA ALMACENA LOS 3 DESCRIPTORES QUE DEF EL
%%VEC ATRIBUTOS.

addpath('../../../01_GeneracionDatos/DatosGenerados')
addpath('../../Funciones')
addpath('../../../01_GeneracionDatos/Funciones')
load('conjunto_datos_estandarizados.mat')
load('nombresProblema.mat')

%%Voy a intentar hacerlo general para poder replicar el codigo en los otros
%%problemas.

%%1º Voy a seleccionar que clases quiero.

% clasesElegidas = [1 2 3];
% new_X = [];
% new_Y = [];
% for i = 1:size(clasesElegidas,2)
%     new_X = cat(1,new_X,Z(Y==clasesElegidas(i),:));
%     new_Y = cat(1,new_Y,Y(Y==clasesElegidas(i)));
% end
new_X = Z;
new_Y = Y;
new_Y(Y==3)=1;

% - Selección de descriptores: encontrar los tres mejores de descriptores
% para discriminar entre las muestras de los círculos y cuadrados. Esta
% etapa almacena los 3 descriptores que definen el vector de atributos.

[espacioCcas, JespacioCcas]=funcion_selecciona_vector_ccas(new_X,new_Y,4);

%Preparo X e Y para el LDA con el espacio de características conseguido.

X = new_X(:,espacioCcas);
Y = new_Y;

nombresProblemaIO = [];
nombresProblemaIO.descriptores = nombresProblema.descriptores;
nombresProblemaIO.clases = nombresProblema.clases([1 2 3]);
nombresProblemaIO.simbolos = nombresProblema.simbolos;
save('DatosGenerados/espacio_ccas_KNN_circulosTriangulos-cuadrados','espacioCcas','nombresProblemaIO','X','Y');