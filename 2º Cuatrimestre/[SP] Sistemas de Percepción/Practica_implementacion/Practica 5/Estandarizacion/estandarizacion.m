clear, clc, close all;
addpath("../DatosGenerados/");
load("conjunto_datos_entrenamiento.mat");

%X(:,end) = [];
    media = mean(X);
    desv_tipica = std(X);

save("media_desviaciones","media","desv_tipica");

Z=(X-media);

[nf nc]=size(Z);

for i=1:nf
    for j=1:nc-1
        Z(i,j)=Z(i,j)/(desv_tipica(1,j)+eps);
    end
end

save("conjunto_datos_estandarizados.mat","Z","Y");

