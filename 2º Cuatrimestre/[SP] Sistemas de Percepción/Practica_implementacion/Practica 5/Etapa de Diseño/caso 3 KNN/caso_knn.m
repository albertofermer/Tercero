
addpath("../../MaterialFacilitado/MaterialFacilitado/Funciones/");
addpath("../../Estandarizacion/");
addpath("../../DatosGenerados");

load("conjunto_Datos_estandarizados.mat");
load("nombresProblema.mat");

%[espacioCcas,JespacioCcas] =  funcion_selecciona_vector_ccas(Z,Y,5);
espacioCcas = [3,6,9,18,19];
save("espacio_knn_solo.mat","espacioCcas");

Y(Y==7)=1;

Y(Y==3) = 2;
Y(Y==4) = 2;
Y(Y==5) = 2;
Y(Y==6) = 2;

Z(Y==8)= [];
Y(Y==8) = [];

XTrain = Z(:,espacioCcas);

%YTrain = funcion_knn(XTrain,XTrain,Y,5);

save("Xtrain_knn","XTrain","Y");

figure, 

    valoresClases = unique(Y);
    numClases = length(valoresClases);
    numAtributos = size(Z,2);
     for i=1:numClases
        Xi = Z(Y==valoresClases(i),:);
        plot3(Xi(:,1),Xi(:,2),Xi(:,3),"*")
            hold on;
            legend("Conjunto 1-7","Conjunto 2,3,4,5,6");
     end
     xlabel("solidez")
     ylabel("Hu1")
     zlabel("Hu4")