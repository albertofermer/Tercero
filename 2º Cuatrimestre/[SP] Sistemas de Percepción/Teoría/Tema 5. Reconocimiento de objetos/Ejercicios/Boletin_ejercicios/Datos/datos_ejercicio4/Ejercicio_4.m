%% Ejercicio 4
%  
% 2 Experimentos de 2 y 3 predictores cada uno. 
% 
% 
% 

%% Representa Datos Experimento 1.

load('datos_biomarcadores_exp1.mat');
datosC1_E1 = datos((clases == 1),:);
datosC2_E1 = datos((clases == 2),:);
plot(datosC1_E1(:,1),datosC1_E1(:,2), '*r'), hold on
plot(datosC2_E1(:,1),datosC2_E1(:,2), '*b'), grid on

%% Representa Datos Experimento 2

load('datos_biomarcadores_exp2.mat');
Clases = unique(clases);
datosC1_E2 = datos((clases == 1),:);
datosC2_E2 = datos((clases == 2),:);
figure, plot3(datosC1_E2(:,1),datosC1_E2(:,2),datosC1_E2(:,3), '*r'), hold on
plot3(datosC2_E2(:,1),datosC2_E2(:,2),datosC2_E2(:,3), '*b'), grid on

%% Clasificador LDA
% 
% d = - 1/2 * Dm + log(pi_k)
% Dm = (X-M)' * pinv(mCov) * (X-M) 
% Como hay dos clases, tenemos que hacer una función de decisión para 
% cada una.

datosE1_Total = [datosC1_E1;datosC2_E1];

M11 = mean(datosC1_E1)';
M12 = mean(datosC2_E1)';

x11 = sym('x11','real');
x12 = sym('x12','real');

X1 = [x11;x12];

% Cálculo función del experimento 1.
    % Matriz de Covariazas
mCov = ((size(datosC1_E1,1)*cov(datosC1_E1)) + (size(datosC2_E1,1)*cov(datosC2_E1)))/(size(datosE1_Total,1));
d1 = expand(-(X1-M11)' * pinv(mCov) * (X1-M11)); % Como son equiprobables no añadimos log(pi_k) ni el 1/2.
d2 = expand(-(X1-M12)' * pinv(mCov) * (X1-M12));

d12 = expand(d1 - d2);

% Cálculo de la frontera
x11 = -2;
x12 = solve(eval(d12) == 0);

p1 = [x11 x12];

x11 = 2;
x12 = sym('x12','real');
x12 = solve(eval(d12) == 0);

p2 = [x11 x12];

% Representa los datos Experimento 1:
plot(datosC1_E1(:,1),datosC1_E1(:,2), '*r'), hold on
plot(datosC2_E1(:,1),datosC2_E1(:,2), '*b'), hold on
plot([p1(1,1),p2(1,1)],[p1(1,2),p2(1,2)]), grid on

% Cálculo de Acc

x11 = datosE1_Total(:,1);
x12 = datosE1_Total(:,2);
valores = eval(d12);
valores(valores>0) = 1; valores(valores<0) = 2;

error = valores - clases;
numAciertos = sum(error == 0);
tasaAcierto = numAciertos/size(datosE1_Total,1);


%% LDA Experimento 2
% Clasificador LDA
% 
% d = - 1/2 * Dm + log(pi_k)
% Dm = (X-M)' * pinv(mCov) * (X-M) 
% Como hay dos clases, tenemos que hacer una función de decisión para 
% cada una.

datosE2_Total = [datosC1_E2;datosC2_E2];

M21 = mean(datosC1_E2)';
M22 = mean(datosC2_E2)';

x21 = sym('x21','real');
x22 = sym('x22','real');
x23 = sym('x23','real');

X2 = [x21;x22;x23];

% Cálculo función del experimento 2.
    % Matriz de Covariazas
mCov = ((size(datosC1_E2,1)*cov(datosC1_E2)) + (size(datosC2_E2,1)*cov(datosC2_E2)))/(size(datosE2_Total,1));
d1 = expand(-(X2-M21)' * pinv(mCov) * (X2-M21)); % Clases equiprobables.
d2 = expand(-(X2-M22)' * pinv(mCov) * (X2-M22));

d12 = expand(d1 - d2);

% Representa los datos Experimento 2:
plot3(datosC1_E2(:,1),datosC1_E2(:,2), datosC1_E2(:,3), '*r'), hold on
plot3(datosC2_E2(:,1),datosC2_E2(:,2), datosC2_E2(:,3), '*b'),  hold on

% Ax21 + Bx22 + Cx23 + D == 0
x21 = 0; x22 = 0; x23 = 0; D = eval(d12);
x21 = 0; x22 = 0; x23 = 1; C = eval(d12)-D;
x21 = 0; x22 = 1; x23 = 0; B = eval(d12)-D;
x21 = 1; x22 = 0; x23 = 0; A = eval(d12)-D;

[x1Plano, x2Plano] = meshgrid(-2:2,-2:2);
x3Plano = -(A*x1Plano + B*x2Plano + D) / (C + eps);


surf(x1Plano, x2Plano, x3Plano)


% Cálculo de Acc

x21 = datosE2_Total(:,1);
x22 = datosE2_Total(:,2);
x23 = datosE2_Total(:,3);
valores = eval(d12);
valores(valores>0) = 1; valores(valores<0) = 2;

error = valores - clases;
numAciertos = sum(error == 0);
tasaAcierto = numAciertos/size(datosE2_Total,1);



%% QDA %%
% 
% d(x) = -*Dm + log(det(mCov_k))
% 
%   Experimento 1
load('datos_biomarcadores_exp1.mat');
datosC1_E1 = datos((clases == 1),:);
datosC2_E1 = datos((clases == 2),:);

datosE1_Total = [datosC1_E1;datosC2_E1];

M11 = mean(datosC1_E1)';
M12 = mean(datosC2_E1)';

x11 = sym('x11','real');
x12 = sym('x12','real');

X1 = [x11;x12];

% Cálculo función del experimento 1.
    % Matriz de Covariazas
mCov1 = cov(datosC1_E1);
mCov2 = cov(datosC2_E1);
d1 = expand(-(X1-M11)' * pinv(mCov1) * (X1-M11) + log(det(mCov1))); % Como son equiprobables no añadimos log(pi_k) ni el 1/2.
d2 = expand(-(X1-M12)' * pinv(mCov2) * (X1-M12)) + log(det(mCov2));

d12 = expand(d1 - d2);

% Cálculo de la frontera
p = [];
for i = -2:0.1:2
x11 = i;
x12 = sym('x12','real');
x12 = solve(eval(d12) == 0);
p = [x11 x12];
end


% Representa los datos Experimento 1:
plot(datosC1_E1(:,1),datosC1_E1(:,2), '*r'), hold on
plot(datosC2_E1(:,1),datosC2_E1(:,2), '*b'), hold on
plot([p1(1,1),p2(1,1)],[p1(1,2),p2(1,2)]), grid on

% Cálculo de Acc

x11 = datosE1_Total(:,1);
x12 = datosE1_Total(:,2);
valores = eval(d12);
valores(valores>0) = 1; valores(valores<0) = 2;

error = valores - clases;
numAciertos = sum(error == 0);
tasaAcierto = numAciertos/size(datosE1_Total,1);


%% QDA Experimento 2
load('datos_biomarcadores_exp2.mat');
Clases = unique(clases);
datosC1_E2 = datos((clases == 1),:);
datosC2_E2 = datos((clases == 2),:);

datosE2_Total = [datosC1_E2;datosC2_E2];

M21 = mean(datosC1_E2)';
M22 = mean(datosC2_E2)';

x21 = sym('x21','real');
x22 = sym('x22','real');
x23 = sym('x23','real');

X2 = [x21;x22;x23];

% Cálculo función del experimento 2.
    % Matriz de Covariazas
mCov1 = cov(datosC1_E2);
mCov2 = cov(datosC2_E2);

d1 = expand(-(X2-M21)' * pinv(mCov1) * (X2-M21) + log(det(mCov1))); % Clases equiprobables.
d2 = expand(-(X2-M22)' * pinv(mCov2) * (X2-M22) + log(det(mCov2)));

d12 = expand(d1 - d2);

% Cálculo de Acc

x21 = datosE2_Total(:,1);
x22 = datosE2_Total(:,2);
x23 = datosE2_Total(:,3);
valores = eval(d12);
valores(valores>0) = 1; valores(valores<0) = 2;

error = valores - clases;
numAciertos = sum(error == 0);
tasaAcierto = numAciertos/size(datosE2_Total,1);






