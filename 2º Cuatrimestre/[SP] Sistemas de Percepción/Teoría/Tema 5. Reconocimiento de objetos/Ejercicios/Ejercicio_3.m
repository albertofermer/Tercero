%% Ejercicio 3
% Diseña un clasificador de minima distancia de mahalanobis suponiendo 3 
% clases representadas por 2 características y los siguientes datos: 
% 

x1 = sym('x1','real');
x2 = sym('x2','real');
X = [x1;x2];

M1 = [0;3];
M2 = [5;2];
M3 = [1;0];

mCov = [1/2 0 ; 0 1/4];

% Se diseña una función de decisión por cada clase (3 Clases)
 d1 = expand(- (X - M1)' * pinv(mCov) * (X - M1));
 d2 = expand(- (X - M2)' * pinv(mCov) * (X - M2));
 d3 = expand(- (X - M3)' * pinv(mCov) * (X - M3));

% Funciones discriminantes 2 a 2
d12 = d1 - d2;
d13 = d1 - d3;
d23 = d2 - d3;


plot([0,10],[-30/8,85/4]), hold on
plot([0,10],[17/12,37/12]), hold on
plot([0,10],[4,-6]), hold on
axis([-2,14,-2,10])