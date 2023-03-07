%%MIRAR A VER SI ESTO ESTA BIEN, ME DA DIFERENTE AL RESTO DE PERSONAS.

function [espacioCcas,JespacioCcas] = funcion_selecciona_vector_ccas(XoI,YoI,dim)

XoI = XoI(:,1:end-1); %%Quitamos la última columna que es la de euler.


% 1.- Cuantificar de forma individual el grado de separabilidad de las características: para cada
% característica, se debe determinar el grado de separabilidad J que proporciona de forma
% individual

numDescriptores = size(XoI,2);

separabilidad_individual = zeros(size(XoI,2),1);

for j = 1:numDescriptores
    descriptor_individual = XoI(:,j);
    separabilidad_individual(j) = indiceJ(descriptor_individual,YoI);
end

% 2.- Pre-selección de las características a analizar: utilizando los valores de separabilidad
% individual obtenidos en el punto anterior, se deben analizar únicamente las características que
% presenten una separabilidad mínima. Este valor de umbral debe fijarse en 0.1.
umbral = 0.1;

preseleccion = separabilidad_individual;
preseleccion(preseleccion <= umbral) = 0;

% 3.- Selección del vector de características de dimensión especificada por el parámetro de entrada
% dim: considerando únicamente las características que superan el umbral de separabilidad
% individual, encontrar las dim características que proporcionan la mayor separabilidad de forma
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

