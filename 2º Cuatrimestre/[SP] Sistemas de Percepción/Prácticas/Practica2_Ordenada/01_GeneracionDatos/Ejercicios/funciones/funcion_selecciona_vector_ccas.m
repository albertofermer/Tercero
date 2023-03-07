%%MIRAR A VER SI ESTO ESTA BIEN, ME DA DIFERENTE AL RESTO DE PERSONAS.

function [espacioCcas,JespacioCcas] = funcion_selecciona_vector_ccas(XoI,YoI,dim)

XoI = XoI(:,1:end-1); %%Quitamos la �ltima columna que es la de euler.


% 1.- Cuantificar de forma individual el grado de separabilidad de las caracter�sticas: para cada
% caracter�stica, se debe determinar el grado de separabilidad J que proporciona de forma
% individual

numDescriptores = size(XoI,2);

separabilidad_individual = zeros(size(XoI,2),1);

for j = 1:numDescriptores
    descriptor_individual = XoI(:,j);
    separabilidad_individual(j) = indiceJ(descriptor_individual,YoI);
end

% 2.- Pre-selecci�n de las caracter�sticas a analizar: utilizando los valores de separabilidad
% individual obtenidos en el punto anterior, se deben analizar �nicamente las caracter�sticas que
% presenten una separabilidad m�nima. Este valor de umbral debe fijarse en 0.1.
umbral = 0.1;

preseleccion = separabilidad_individual;
preseleccion(preseleccion <= umbral) = 0;

% 3.- Selecci�n del vector de caracter�sticas de dimensi�n especificada por el par�metro de entrada
% dim: considerando �nicamente las caracter�sticas que superan el umbral de separabilidad
% individual, encontrar las dim caracter�sticas que proporcionan la mayor separabilidad de forma
% conjunta
indices = 1:22;
indices = indices(preseleccion > umbral); %Selecciono los indice que cumplan la restriccion.



combinacion = combnk(indices,dim);

nCombinaciones = size(combinacion,1);
separabilidad_combinaciones = zeros(size(combinacion,1),1);
for i = 1:nCombinaciones
    
    Xnew = XoI(:,combinacion(i,:));%%X formada por los descriptores aleatorios elegidos.
    separabilidad_combinaciones(i) = indiceJ(Xnew,YoI);
    
end
    [maximo,ind] = max(separabilidad_combinaciones);
    espacioCcas = combinacion(ind,:);
    JespacioCcas = maximo;
    
    
end

