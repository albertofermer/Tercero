%En este script se va a realizar todo lo relacionado con la representacion
%de los datos. 

addpath("..\DatosGenerados\");
load("conjunto_datos.mat"); 
load("nombresProblema.mat");

for i=1:11
    espacioCCas = [i;i+1];
    funcion_representa_datos(X,Y,espacioCCas,nombresProblema);
end

% Ejemplo de programación:
% Dado un conjunto de datos X-Y, y definidas las variables representativas
% del problema: numClases, codifClases, nombreClases, numDescriptores,
% nombreDescriptores

numClases = 3;
codifClases = zeros(3);
codifClases(1) = 1;
codifClases(2) = 2;
codifClases(3) = 3;
nombreClases = nombresProblema.nombre;
numDescriptores = size(X,2);
nombreDescriptores = nombresProblema.descriptores;

for j=1:numDescriptores
    % Valores máximo y mínimos para representar en la misma escala
    vMin = min(X(:,j));
    vMax = max(X(:,j));

    hFigure = figure; hold on
    bpFigure = figure; hold on

    for i=1:numClases

        Xij = X(Y==codifClases(i),j); % datos clase i del descriptor j

        figure(hFigure)
        subplot(numClases,1,i), hist(Xij),
        xlabel(nombreDescriptores{j})
        ylabel('Histograma')
        axis([ vMin vMax 0 size(X,1)/4]) % inf escala automática eje y
        title(nombreClases{i})

        figure(bpFigure)
        subplot(1,numClases,i), boxplot(Xij)
        xlabel('Diagrama de Caja')
        ylabel(nombreDescriptores{j})
        axis([ 0 2 vMin vMax ])
        title(nombreClases{i})
    end
end