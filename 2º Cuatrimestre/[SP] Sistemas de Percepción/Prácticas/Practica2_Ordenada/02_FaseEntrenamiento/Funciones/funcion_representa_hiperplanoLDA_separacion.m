function funcion_representa_hiperplanoLDA_separacion(coef_d12,X)
     A = coef_d12(1);
     B = coef_d12(2);
     C = coef_d12(3);
    
if(size(coef_d12,2)==3)%%Comprobar que esto son los numero de coef.
    %%Si tiene 3 coeficientes la grafica será 2d.
    
    
    Yprediccion = A*X(:,1)+B*X(:,2)+C;
    
    XC1 = X(Yprediccion>0,:);
    XC2 = X(Yprediccion<0,:);
    Xnose = X(Yprediccion==0,:);
    
    plot(XC1(:,1),XC1(:,2),'.r');
     hold on;
    plot(XC2(:,1),XC2(:,2),'.b');
    plot(Xnose(:,1),Xnose(:,2),'.black');
     
     
    
     
end

if(size(coef_d12,2) == 4) 
    D = coef_d12(4);
    
    Yprediccion = A*X(:,1)+B*X(:,2)+C*X(:,3)+D;
    
     XC1 = X(Yprediccion>0,:);
     XC2 = X(Yprediccion<0,:);
     Xnose = X(Yprediccion==0,:);
    
    x1min = min(X(:,1));
    x1max = max(X(:,1));

    x2min = min(X(:,2));
    x2max = max(X(:,2));

    [x1Plano, x2Plano] = meshgrid(x1min:0.01:x1max,x2min:0.01:x2max);
    x3Plano = -(A*x1Plano + B*x2Plano + D)/ (C+eps);
    
    plot3(XC1(:,1),XC1(:,2),XC1(:,3),'.b');
    hold on;
    plot3(XC2(:,1),XC2(:,2),XC2(:,3),'.r');
    plot3(Xnose(:,1),Xnose(:,2),Xnose(:,3),'.black');
    surf(x1Plano,x2Plano, x3Plano)
    
end


end

