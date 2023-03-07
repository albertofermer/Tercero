%% Ejercicio 5
% 
% 
% 
% 

load('datos_MDE_2dimensiones.mat');
numDatos = size(X,1); porcentajeTrain = 0.7;
numDatosTrain = round(porcentajeTrain*numDatos); numerosMuestrasTrain = randsample(numDatos,numDatosTrain); 
numerosMuestrasTest = find(not(ismember(1:numDatos,numerosMuestrasTrain))); % Conjunto de Train
XTrain = X(numerosMuestrasTrain,:); 
YTrain = Y(numerosMuestrasTrain); % Conjunto de Test
XTest = X(numerosMuestrasTest,:);
YTest = Y(numerosMuestrasTest);

%% Representar las dos gráficas:

subplot(1,2,1), plot(XTrain(YTrain == 1,1),XTrain(YTrain == 1,2), '*r'), hold on 
plot(XTrain(YTrain == 2,1),XTrain(YTrain == 2,2), '*b')
subplot(1,2,2), plot(XTest(YTest == 1,1),XTest(YTest == 1,2), '*r'), hold on
plot(XTest(YTest == 2,1),XTest(YTest == 2,2), '*b')

%% LDA
% d(x) = -1/2 * DM + log(pi_k)
%

NdatosC1 = size(XTrain(YTrain == 1),1);
NdatosC2 = size(XTrain(YTrain == 2),1);

x1 = sym('x1','real');
x2 = sym('x2','real');

X = [x1;x2];

pi_1 = (NdatosC1) / (NdatosC1 + NdatosC2 );
pi_2 = (NdatosC2) / (NdatosC1 + NdatosC2 );

M1 = mean(XTrain(YTrain == 1,:))' ;
M2 = mean(XTrain(YTrain == 2,:))' ;

mCov = (NdatosC1*cov(XTrain(YTrain == 1,:)) + (NdatosC2*cov(XTrain(YTrain == 2,:))))/(NdatosC1 + NdatosC2);

d1 = expand(-0.5*(X-M1)'*pinv(mCov)*(X-M1) + log(pi_1));
d2 = expand(-0.5*(X-M2)'*pinv(mCov)*(X-M2) + log(pi_2));

d12 = d1 - d2;

%% Representación con frontera
% Cálculo de la frontera:
x1 = -2;
x2 = sym('x2','real');
x2 = solve(eval(d12) == 0);
p1 = [x1, x2];

x1 = 2;
x2 = sym('x2','real');
x2 = solve(eval(d12) == 0);
p2 = [x1,x2];

subplot(1,2,1), plot(XTrain(YTrain == 1,1),XTrain(YTrain == 1,2), '*r'), hold on 
plot(XTrain(YTrain == 2,1),XTrain(YTrain == 2,2), '*b')
plot([p1(1,1),p2(1,1)],[p1(1,2),p2(1,2)])
subplot(1,2,2), plot(XTest(YTest == 1,1),XTest(YTest == 1,2), '*r'), hold on
plot(XTest(YTest == 2,1),XTest(YTest == 2,2), '*b')
plot([p1(1,1),p2(1,1)],[p1(1,2),p2(1,2)])

% Calcula Acc

x1 = XTest(:,1);
x2 = XTest(:,2);

valores = eval(d12);
Yp = zeros(size(valores,1),1);
Yp(valores>0) = 1;
Yp(valores<0) = 2;

error = YTest - Yp;
nAciertos = sum(error==0);
tasaAciertoLDA = nAciertos/(size(XTest,1));

%% QDA
mCov1 = cov(XTrain(YTrain == 1,:));
mCov2 = cov(XTrain(YTrain == 2,:));

d1 = expand(-0.5*(X-M1)'*pinv(mCov1)*(X-M1) - log(det(mCov1))+ log(pi_1));
d2 = expand(-0.5*(X-M2)'*pinv(mCov2)*(X-M2) - log(det(mCov2)) + log(pi_2));

d12 = d1-d2;

%Acc QDA
x1 = XTest(:,1);
x2 = XTest(:,2);

valores = eval(d12);
Yp = zeros(size(valores,1),1);
Yp(valores>0) = 1;
Yp(valores<0) = 2;

error = YTest - Yp;
nAciertos = sum(error==0);
tasaAciertoQDA = nAciertos/(size(XTest,1));







