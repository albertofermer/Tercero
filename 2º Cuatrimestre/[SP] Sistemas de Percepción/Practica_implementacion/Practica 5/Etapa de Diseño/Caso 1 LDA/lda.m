addpath("../../MaterialFacilitado/MaterialFacilitado/Funciones/");
addpath("../../DatosGenerados/");
addpath("../../Estandarizacion/");
load("conjunto_Datos_estandarizados.mat")
load("nombresProblema.mat");

%CLASIFICADOR CIRCULO-CUADRADO: 
X = Z;
%Encontramos los tres mejores descriptores que definen este problema. 
pos = find(Y==1);
valor1 = 1;
XoI1 = X(Y==valor1,:);
YoI1 = Y(Y==valor1,:);
pos = find(Y==7);
valor2 = 7;
XoI2 = X(Y==valor2,:);
YoI2 = Y(Y==valor2,:);
XoI = [XoI1;XoI2];
YoI = [YoI1;YoI2];
dim = 3;
[espacioCcas,JespacioCcas] =  funcion_selecciona_vector_ccas(XoI,YoI,dim);

Xt = XoI(:,espacioCcas(1));
Xt = [Xt, XoI(:,espacioCcas(2))];
Xt = [Xt, XoI(:,espacioCcas(3))];

[vectorMedias, matrizCovarianza, probabilidadPriori] = funcion_ajusta_LDA(Xt,YoI);

valoresClases = [valor1;valor2];
[Y_lda,d12] = funcion_aplica_LDA(Xt,vectorMedias,matrizCovarianza,probabilidadPriori,valoresClases);

x1 = 0; x2=0;x3=0; d = eval(d12);
x1 = 1; x2=0;x3=0; a = eval(d12)-d;
x1 = 0; x2=1;x3=0; b = eval(d12)-d;
x1 = 0; x2=0;x3=1; c = eval(d12)-d;

save("clasificador_lda_1_7","d","a","b","c","d12","espacioCcas","Y_lda","Xt");
figure,
coeficiente = [a,b,c,d];
representa_hiperplano_separacion_2_3(Xt,coeficiente);
hold on, representa_muestras_clasificacion_binaria(Xt,Y_lda,nombresProblema)
xlabel("Solidez"), ylabel("DF5"),zlabel("DF8")

% %CLASIFICADOR CIRCULO-TRIANGULO: 
% X = Z;
% %Encontramos los tres mejores descriptores que definen este problema. 
% pos = find(nombresProblema.nombre == "Circulo");
% valor1 = nombresProblema.symbolo{pos};
% XoI1 = X(Y==str2num(valor1),:)
% YoI1 = Y(Y==str2num(valor1),:)
% pos = find(nombresProblema.nombre == "Triangulo");
% valor2 = nombresProblema.symbolo{pos};
% XoI2 = X(Y==str2num(valor2),:);
% YoI2 = Y(Y==str2num(valor2),:);
% XoI = [XoI1;XoI2];
% YoI = [YoI1;YoI2];
% dim = 3;
% [espacioCcas,JespacioCcas] =  funcion_selecciona_vector_ccas(XoI,YoI,dim);
% 
% Xt = XoI(:,espacioCcas(1));
% Xt = [Xt, XoI(:,espacioCcas(2))];
% Xt = [Xt, XoI(:,espacioCcas(3))];
% 
% [vectorMedias, matrizCovarianza, probabilidadPriori] = funcion_ajusta_LDA(Xt,YoI);
% 
% valoresClases = [valor1;valor2];
% [Y_lda,d12] = funcion_aplica_LDA(Xt,vectorMedias,matrizCovarianza,probabilidadPriori,valoresClases);
% 
% x1 = 0; x2=0;x3=0; d = eval(d12);
% x1 = 1; x2=0;x3=0; a = eval(d12)-d;
% x1 = 0; x2=1;x3=0; b = eval(d12)-d;
% x1 = 0; x2=0;x3=1; c = eval(d12)-d;
% 
% save("clasificador_lda_Cir_Tri.mat","d","a","b","c","d12","espacioCcas","Y_lda","Xt");
% 
% %CLASIFICADOR CUADRADO-TRIANGULO: 
% X = Z;
% %Encontramos los tres mejores descriptores que definen este problema. 
% pos = find(nombresProblema.nombre == "Cuadrado");
% valor1 = nombresProblema.symbolo{pos};
% XoI1 = X(Y==str2num(valor1),:)
% YoI1 = Y(Y==str2num(valor1),:)
% pos = find(nombresProblema.nombre == "Triangulo");
% valor2 = nombresProblema.symbolo{pos};
% XoI2 = X(Y==str2num(valor2),:);
% YoI2 = Y(Y==str2num(valor2),:);
% XoI = [XoI1;XoI2];
% YoI = [YoI1;YoI2];
% dim = 3;
% [espacioCcas,JespacioCcas] =  funcion_selecciona_vector_ccas(XoI,YoI,dim);
% 
% Xt = XoI(:,espacioCcas(1));
% Xt = [Xt, XoI(:,espacioCcas(2))];
% Xt = [Xt, XoI(:,espacioCcas(3))];
% 
% 
% [vectorMedias, matrizCovarianza, probabilidadPriori] = funcion_ajusta_LDA(Xt,YoI);
% 
% valoresClases = [valor1;valor2];
% [Y_lda,d12] = funcion_aplica_LDA(Xt,vectorMedias,matrizCovarianza,probabilidadPriori,valoresClases);
% 
% x1 = 0; x2=0;x3=0; d = eval(d12);
% x1 = 1; x2=0;x3=0; a = eval(d12)-d;
% x1 = 0; x2=1;x3=0; b = eval(d12)-d;
% x1 = 0; x2=0;x3=1; c = eval(d12)-d;
% 
% save("clasificador_lda_Cuad_Tri.mat","d","a","b","c","d12","espacioCcas","Y_lda","Xt");
% 
