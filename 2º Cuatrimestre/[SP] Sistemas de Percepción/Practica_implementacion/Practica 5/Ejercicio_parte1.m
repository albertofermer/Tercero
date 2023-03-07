
%EJERCICIO PARTE 1: 

addpath("MaterialFacilitado\MaterialFacilitado\Funciones\");
addpath("Estandarizacion\");
addpath("DatosGenerados\");
load("nombresProblema.mat");
load("conjunto_Datos_estandarizados.mat");

X = Z;
% a) circulos vs cuadrados vs triangulos. 
[espacioccasA,Jespacioccas] = funcion_selecciona_vector_ccas(X,Y,3);

xoA = X;
yoA = Y;

save("espacio_caracteristicas_A","espacioccasA","xoA","yoA","Jespacioccas");

%b) Circulos (1) vs Cuadrados (2). 
U = unique(Y);
Y(Y==3) = [];
Yob = Y;
Xob = [X(Yob==1,:);X(Yob==2,:)];
[espacioccasB,Jespacioccas] = funcion_selecciona_vector_ccas(Xob,Yob,2);


save("espacio_caracteristicas_B","espacioccasB","Xob","Yob","Jespacioccas");

%c) Circulos(1) vs Triangulos(3). 
load("conjunto_Datos_estandarizados.mat");
X = Z;
U = unique(Y);
Y(Y==2) = [];
Yoc = Y;
Xoc = [X(Yoc==1,:);X(Yoc==3,:)];
[espacioccasC,Jespacioccas] = funcion_selecciona_vector_ccas(Xoc,Yoc,2);

save("espacio_caracteristicas_C","espacioccasC","Xoc","Yoc","Jespacioccas");

%d) Cuadrados(2) vs Triangulos (3)

load("conjunto_Datos_estandarizados.mat");
X = Z;
U = unique(Y);
Y(Y==1) = [];
Yod = Y;
Xod = [X(Yod==2,:);X(Yod==3,:)];
[espacioccasD,Jespacioccas] = funcion_selecciona_vector_ccas(Xod,Yod,2);

save("espacio_caracteristicas_D","espacioccasD","Xod","Yod","Jespacioccas");

%eCirculos-Triangulos vs cuadrados.

load("conjunto_Datos_estandarizados.mat");
X = Z;
U = unique(Y);
Y(Y==3) = 1;
Yoe = Y;
Xoe = [X(Yoe==1,:);X(Yoe==2,:)];
[espacioccasE,Jespacioccas] = funcion_selecciona_vector_ccas(Xoe,Yoe,2);

save("espacio_caracteristicas_E","espacioccasE","Xoe","Yoe","Jespacioccas");

