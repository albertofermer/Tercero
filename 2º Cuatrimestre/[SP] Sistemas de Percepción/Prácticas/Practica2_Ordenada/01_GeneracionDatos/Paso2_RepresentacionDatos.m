clear all
clc
addpath('DatosGenerados')
addpath('Funciones')
load conjunto_datos.mat
load nombresProblema.mat

%%Represento los datos 2 a 2 menos euler.
for i=1:2:size(X,2)-1
    funcion_representa_datos(X,Y, [i i+1], nombresProblema)
end


numClases = size(unique(Y),1);
numDescriptores = size(X,2);
codifClases = unique(Y);
nombreDescriptores = nombresProblema.descriptores;
nombreClases = nombresProblema.clases;
numMuestras = size(X,1);

for j=1:numDescriptores - 1 
    
  
    vMin = min(X(:,j)); 
    vMax = max(X(:,j));
    
    hFigure = figure; hold on
    bpFigure = figure; hold on
    
    for i=1:numClases
    
        Xij = X(Y==codifClases(i),j);
        numDatosClase = size(Xij,1);

        figure(hFigure)
        subplot(numClases,1,i), hist(Xij),
        
        xlabel(nombreDescriptores{j})
        ylabel('Histograma')
        axis([ vMin vMax 0 numMuestras/4]) 
        title(nombreClases{i})
        
        figure(bpFigure)
        subplot(1,numClases,i), boxplot(Xij)
        
        xlabel('Diagrama de Caja')
        ylabel(nombreDescriptores{j})
        axis([ 0 2 vMin vMax ])
        title(nombreClases{i})
    end
end