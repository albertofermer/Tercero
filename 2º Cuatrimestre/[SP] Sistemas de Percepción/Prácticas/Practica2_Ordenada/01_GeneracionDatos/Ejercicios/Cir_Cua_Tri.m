addpath("../DatosGenerados");
addpath("funciones");
load("conjunto_datos_estandarizados.mat");
load("nombresProblema.mat")
%%Tengo que usar lo de los nombres?


simbolos = nombresProblema.simbolos;

% Círculos vs cuadrados vs triángulos

[espacioCcas,Separabilidad]=funcion_selecciona_vector_ccas(Z,Y,3);

funcion_representa_datos(Z,Y,espacioCcas,nombresProblema);
title('Circulos vs Cuadrados vs Triangulos')
%%Círculos vs cuadrados

Ycuadrado_circulo = Y(Y~= 3);
Zcuadrado_circulo = Z((Y~= 3),:);

[espacioCcas]=funcion_selecciona_vector_ccas(Zcuadrado_circulo,Ycuadrado_circulo,3);

funcion_representa_datos(Zcuadrado_circulo,Ycuadrado_circulo,espacioCcas,nombresProblema);
title('Circulos vs Cuadrados')

% Círculos vs triángulos

Ycirculo_triangulo = Y(Y~= 2);
Zcirculo_triangulo = Z((Y~= 2),:);

[espacioCcas]=funcion_selecciona_vector_ccas(Zcirculo_triangulo,Ycirculo_triangulo,3);
funcion_representa_datos(Zcirculo_triangulo,Ycirculo_triangulo,espacioCcas,nombresProblema);
title('Circulos vs Triangulos')
% Cuadrados vs triángulos 

Ycuadrados_triangulos = Y(Y~= 1);
Zcuadrados_triangulos = Z((Y~= 1),:);

[espacioCcas,JespacioCcas]=funcion_selecciona_vector_ccas(Zcuadrados_triangulos,Ycuadrados_triangulos,3);
funcion_representa_datos(Zcuadrados_triangulos,Ycuadrados_triangulos,espacioCcas,nombresProblema);
title('Cuadrado vs Triangulos')
%Circulos-triangulos vs cuadrados

YcirculosTriangulos_cuadrados = Y;
YcirculosTriangulos_cuadrados(Y==3) = 1;


[espacioCcas,JespacioCcas]=funcion_selecciona_vector_ccas(Z,YcirculosTriangulos_cuadrados,3);
funcion_representa_datos(Z,YcirculosTriangulos_cuadrados,espacioCcas,nombresProblema);
title('Circulo-Triangulos vs Cuadrado')