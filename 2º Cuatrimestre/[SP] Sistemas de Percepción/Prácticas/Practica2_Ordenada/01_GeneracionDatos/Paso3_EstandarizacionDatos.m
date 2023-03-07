clear all
clc
addpath('DatosGenerados')
addpath('Funciones')
load conjunto_datos.mat
load nombresProblema.mat


medias = mean(X);
desv = std(X);

%%Para no tocar el descriptor de euler :
medias(end) =0;
desv(end) = 1;

Z = X;
[numMuestras, numDescriptores] = size(Z);
for i=1:numDescriptores-1 %%Cuidado con no tocar euler.
    
    Z(:,i) = (X(:,i)-medias(i))/desv(i); %%A cada valor le resto su media y lo diviso por su desv.
    
end

save('DatosGenerados/conjunto_datos_estandarizados','Z','Y');
save('DatosGenerados/datos_estandarizados','medias','desv');