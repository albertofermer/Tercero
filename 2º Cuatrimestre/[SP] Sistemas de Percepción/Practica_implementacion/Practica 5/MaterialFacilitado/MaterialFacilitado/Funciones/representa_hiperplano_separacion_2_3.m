function representa_hiperplano_separacion_2_3(X,coeficiente_d12)

    [~,numDimensiones] = size(X);

    x1min = min(X(:,1)); x1max = max(X(:,1));
    x2min = min(X(:,2)); x2max = max(X(:,2));

    if numDimensiones == 3
        A = coeficiente_d12(1);
        B = coeficiente_d12(2);
        C = coeficiente_d12(3);
        D = coeficiente_d12(4);
        [x1plano,x2plano] = meshgrid(x1min:0.01:x1max,x2min:0.01:x2max);
        x3plano = -(A*x1plano + B*x2plano + D)/(C+eps);
        surf(x1plano,x2plano,x3plano);
    elseif numDimensiones == 2
        A = coeficiente_d12(1);
        B = coeficiente_d12(2);
        C = coeficiente_d12(3);

        valoresX1frontera = 0:1:9;
        valoresX2frontera = -(A*valoresX1frontera+C)/B;
        plot(valoresX1frontera,valoresX2frontera,'black');
    end
    legend("hiperplano")

end