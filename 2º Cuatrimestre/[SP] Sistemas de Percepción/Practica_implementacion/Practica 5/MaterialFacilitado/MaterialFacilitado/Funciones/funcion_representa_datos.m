function funcion_representa_datos(X,Y,espacioCcas,nombresProblema)
    figure,
    if size(espacioCcas,1) == 2
        plot(X(Y==1,espacioCcas(1)),X(Y==1,espacioCcas(2)),'*r',...
            X(Y==2,espacioCcas(1)),X(Y==2,espacioCcas(2)),'*g',...
            X(Y==3,espacioCcas(1)),X(Y==3,espacioCcas(2)),'*b')
        legend("Circulo","Cuadrado","Triangulo");
        xlabel(nombresProblema.descriptores(1,espacioCcas(1)));
        ylabel(nombresProblema.descriptores(1,espacioCcas(2)));
    else 
    end
end