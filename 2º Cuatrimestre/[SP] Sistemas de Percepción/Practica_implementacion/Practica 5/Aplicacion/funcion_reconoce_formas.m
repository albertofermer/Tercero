function funcion_reconoce_formas(Nombre)

%%GENERAL: 

    addpath("../DatosGenerados/");
    load("NombresProblema.mat");
    I = imread(Nombre+".png");
    umbralParada = funcion_otsu(I);
    Ibinaria = I > umbralParada;
    [Ietiq,N] = bwlabel(Ibinaria);
    XTest = funcion_calcula_descriptores_imagen(Ietiq,N);
    
    %Para ejecutar la estandarizacion: 
    Z = XTest;
    XTest(:,end) = [];

    media = mean(XTest);
    desv_tipica = std(XTest);

    XTest=(XTest-media);

    [nf nc]=size(XTest);

    for i=1:nf
        for j=1:nc-1
            XTest(i,j)=XTest(i,j)/(desv_tipica(1,j)+eps);
        end
    end
    
    %CLASIFICACION: 
YTest = [];
    
addpath("../Etapa de Diseño/caso 3 KNN/");
            load("espacio_knn_solo.mat");
            load("Xtrain_knn.mat");
            Xt = XTest(:,espacioCcas);
            YTest = funcion_knn(Xt,XTrain,Y,5);
            YTest(YTest==0)=[];  

     Yfinal_Test = [];  
    for i=1:N 
        if Z(i,end) == 2 
            Yfinal_Test = [Yfinal_Test;8];
        else 
            if YTest(i) == 1
                addpath("../Etapa de Diseño/Caso 1 LDA/");
                load("clasificador_lda_1_7.mat");
                x1 = XTest(i,espacioCcas(1)); x2 = XTest(i,espacioCcas(2)); x3 = XTest(i,espacioCcas(3));
                r = eval(d12);
                if r > 0
                    Yfinal_Test = [Yfinal_Test;1];
                else 
                    Yfinal_Test = [Yfinal_Test;7];
                end
            elseif YTest(i)==2
                addpath("../Etapa de Diseño/Caso 2 QDA/");
                load("funciones_decision_qda.mat");
                load("Espacio_Caracteristicas_QDA.mat");
                 x1 = XTest(i,1); x2 = XTest(i,2); x3 = XTest(i,3); x4 = XTest(i,4);
                 x5 = XTest(i,5); x6 = XTest(i,6); x7 = XTest(i,7); x8 = XTest(i,8);
                 x9 = XTest(i,9); x10 = XTest(i,10); x11 = XTest(i,11); x12 = XTest(i,12);
                x13 = XTest(i,13); x14 = XTest(i,14); x15 = XTest(i,15); x16 = XTest(i,16);
                x17 = XTest(i,17); x18 = XTest(i,18); x19 = XTest(i,19); x20 = XTest(i,20);
                x21 = XTest(i,21); x22 = XTest(i,22); 
                    
                    r1 = eval(d12);
                    r2 = eval(d13);
                    r3 = eval(d23);
                    r4 = eval(d14);
                    r5 = eval(d24);
                    r6 = eval(d34);
        
                    if r1 > 0 && r2 > 0 && r4 > 0
                        Yfinal_Test = [Yfinal_Test;2];
                    elseif r1 < 0 && r3 > 0 && r5 > 0
                        Yfinal_Test = [Yfinal_Test;3];
                    elseif r2 <0 && r3 < 0 && r6 >0
                        Yfinal_Test = [Yfinal_Test;4];
                    else 
                        Yfinal_Test = [Yfinal_Test;5];
                    end
            end
        end
    end


for i=1:N
       figure, 
       color = [0,0,255];
       Io = funcion_visualiza(I,Ietiq==i,color,false);
       imshow(Io)
       if Yfinal_Test(i) == 1
           title("1");
       elseif Yfinal_Test(i) == 2
           title("2");
       elseif Yfinal_Test(i) == 3
           title("3");
        elseif Yfinal_Test(i) == 4
            title("4");
        elseif Yfinal_Test(i) == 5
            title("5");
        elseif Yfinal_Test(i) == 6
            title("6");
        elseif Yfinal_Test(i) == 7
            title("7");
       end
end





    %SOLO QDA: 
%     addpath("../Etapa de Diseño/Caso 2 QDA/");
%     load("funciones_decision_qda.mat");
%     load("Espacio_Caracteristicas_QDA.mat");
% 
%     Xt = XTest(:,espacioCcas);
% 
%     YTest = [];
%    for i=1:N
%          x1 = Xt(i,1); x2 = Xt(i,2); x3 = Xt(i,3); x4 = Xt(i,4);
%             r1 = eval(d12);
%             r2 = eval(d13);
%             r3 = eval(d23);
%         
%          if r1 > 0 && r2 > 0
%             YTest = [YTest;1];
%          elseif r1 < 0 && r3 > 0
%             YTest = [YTest;2];
%          elseif r2 <0 && r3 < 0
%             YTest = [YTest;3];
%             end
%    end
%     load("nombresProblema.mat");
%     
%    for i=1:N
%        figure, 
%        color = [0,0,255];
%        Io = funcion_visualiza(I,Ietiq==i,color,false);
%        imshow(Io)
%        if YTest(i) == 1
%            title("QDA: Circulo");
%        elseif YTest(i) == 2
%            title("QDA: Cuadrado");
%        else
%            title("QDA: Triangulo");
%        end
%    end
% 
%    pause, close all,
% 
%     %SOLO KNN
%      addpath("../Etapa de Diseño/caso 3 KNN/");
%     load("Xtrain_knn.mat");
%     load("espacio_knn_solo.mat");
% 
%     Z=XTest(:,espacioCcas);
%     YTest=funcion_knn(Z,XTrain,Y,5);
% 
% 
%     for i=1:N
%         Ibin=Ietiq==i;
%         Clase=YTest(i);
%         Io=funcion_visualiza(I,Ibin,[0 255 0],false);
%         figure,imshow(Io);
%         title("KNN"+nombresProblema.nombre{Clase});
%     end
% 
%      pause, close all,
% 
%     %CASO KNN + LDA
%      addpath("../Etapa de Diseño/caso 4 combinacion/");
%     load("x_knn.mat");
%     Y_knn = funcion_knn(XTest,X,YoI,5);
%     Y_knn(Y_knn==0) = [];
% 
%     load("espacio_caracteristicas.mat")
% 
%    Xt = XTest(:,espacioCcas);
% 
%     Xt(Y_knn==2,:) = 0;
%     load("y_lda_combinacion.mat");
%     Y_lda = [];
%     for i=1:size(Xt,1)
%         if Xt(i) ~= 0
%             x1 = Xt(i,1); x2 = Xt(i,2);x3 = Xt(i,3);
%             r = eval(d12);
%             if r > 0
%                 Y_lda=[Y_lda;1];
%             else 
%                 Y_lda=[Y_lda;3];
%             end
%         else
%             Y_lda=[Y_lda;0];
%         end
%     end
% 
%     for i=1:N
%        figure, 
%        color = [0,0,255];
%        Io = funcion_visualiza(I,Ietiq==i,color,false);
%        imshow(Io)
%        if Y_knn(i) == 2
%            title("KNN+LDA: Cuadrado");
%        elseif Y_knn(i) == 3
%           if Y_lda(i) == 1
%               title("KNN+LDA: Circulo");
%           else 
%               title("KNN+LDA: Triangulo");
%           end
%        else
%           title("Fallo");
%        end
% 
%     end
% 
% pause,close all
% 
%     %CASO SOLO LDA: 
% 
%     addpath("../Etapa de Diseño/Caso 1 LDA/");
%     load("clasificador_lda_Cir_Cuad.mat");
% 
%     X = XTest(:,espacioCcas);
% 
%     for i=1:N
%         
%         x1 = X(i,1); x2 = X(i,2); x3 = X(i,3); 
%         r = eval(d12);
%         if r > 0
%             YTest(i) = str2num(nombresProblema.symbolo{1});
%         elseif r < 0
%             YTest(i) = str2num(nombresProblema.symbolo{2});
% 
%         end
%     end
% 
%     load("clasificador_lda_Cir_Tri.mat");
% 
%      X = XTest(:,espacioCcas);
% 
%     for i=1:N
%         if YTest(i) == 1
%         x1 = X(i,1); x2 = X(i,2); x3 = X(i,3); 
%         r = eval(d12);
%         if r > 0
%             YTest(i) = str2num(nombresProblema.symbolo{1});
%         elseif r < 0
%             YTest(i) = str2num(nombresProblema.symbolo{3});
% 
%         end
%         end
%     end
% 
%     load("clasificador_lda_Cuad_Tri.mat");
% 
%      X = XTest(:,espacioCcas);
% 
%     for i=1:N
%         if YTest(i) == 2
%             x1 = X(i,1); x2 = X(i,2); x3 = X(i,3); 
%             r = eval(d12);
%             if r > 0
%                 YTest(i) = str2num(nombresProblema.symbolo{2});
%             elseif r < 0
%                 YTest(i) = str2num(nombresProblema.symbolo{3});
%             end
%         end
%     end
%     
%     for i=1:N
%        figure, 
%        color = [0,0,255];
%        Io = funcion_visualiza(I,Ietiq==i,color,false);
%        subplot(2,2,1),imshow(Io)
%        if YTest(i) == 2
%            title("LDA: Cuadrado");
%        elseif YTest(i) == 3
%           if YTest(i) == 1
%               title("LDA: Circulo");
%           else 
%               title("LDA: Triangulo");
%           end
%        else
%           title("LDA: Circulo");
%        end
%          load("clasificador_lda_Cir_Cuad.mat");
%          coeficiente = [a,b,c,d];
%          hold on, subplot(2,2,2),representa_hiperplano_separacion_2_3(X,coeficiente);
%          hold on, representa_muestras_clasificacion_binaria(Xt,Y_lda,nombresProblema);
%         hold on, xlabel(nombresProblema.descriptores{espacioCcas(1)}), ylabel(nombresProblema.descriptores{espacioCcas(2)}), zlabel(nombresProblema.descriptores{espacioCcas(3)})
%         hold on, plot3(X(i,1),X(i,2),X(i,3),'o');
%     
%          load("clasificador_lda_Cir_Tri.mat");
%          coeficiente = [a,b,c,d];
%          hold on, subplot(2,2,3),representa_hiperplano_separacion_2_3(X,coeficiente);
%          hold on, xlabel(nombresProblema.descriptores{espacioCcas(1)}), ylabel(nombresProblema.descriptores{espacioCcas(2)}), zlabel(nombresProblema.descriptores{espacioCcas(3)})
%          hold on, representa_muestras_clasificacion_binaria(Xt,Y_lda,nombresProblema);
%          hold on, plot3(X(i,1),X(i,2),X(i,3),'o');
% 
%           load("clasificador_lda_Cuad_Tri.mat");
%          coeficiente = [a,b,c,d];
%          hold on, subplot(2,2,4),representa_hiperplano_separacion_2_3(X,coeficiente);
%          hold on, representa_muestras_clasificacion_binaria(Xt,Y_lda,nombresProblema);
%          hold on, xlabel(nombresProblema.descriptores{espacioCcas(1)}), ylabel(nombresProblema.descriptores{espacioCcas(2)}), zlabel(nombresProblema.descriptores{espacioCcas(3)})
%          hold on, plot3(X(i,1),X(i,2),X(i,3),'o');
%     end
% 
% end