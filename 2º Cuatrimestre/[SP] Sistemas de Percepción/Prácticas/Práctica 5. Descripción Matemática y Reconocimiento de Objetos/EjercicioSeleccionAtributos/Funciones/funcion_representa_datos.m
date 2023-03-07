function funcion_representa_datos(X,Y,espacioCcas,nombresProblema)

nombreDescriptores = nombresProblema.descriptores;
nombreClases = nombresProblema.clases;
simbolos = nombresProblema.simbolos;

codifClases = unique(Y);
numClases = length(codifClases);

figure,
hold on,
for i = 1:numClases

    Xi = X(Y==codifClases(i),espacioCcas);
        if(length(espacioCcas) == 2)
        
            plot(Xi(:,1),Xi(:,2),simbolos{codifClases(i)})

        else

            plot3(Xi(:,1),Xi(:,2),Xi(:,3),simbolos{codifClases(i)})

        end
        
end

legend(nombreClases);
xlabel(nombreDescriptores{espacioCcas(1)})
ylabel(nombreDescriptores{espacioCcas(2)})

if(length(espacioCcas) == 3)

    zlabel(nombreDescriptores{espacioCcas(3)})
end
grid on,
hold off
end

