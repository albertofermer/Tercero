function [vectorMedias, matricesCovarianzas,probabilidadPriori] = funcion_ajusta_QDA(X,Y)
    dif = unique(Y);

    media1 = mean(X(Y == dif(1),:))';
    media2 = mean(X(Y == dif(2),:))';
    media3 = mean(X(Y == dif(3),:))';
    media4 = mean(X(Y == dif(4),:))';
    vectorMedias= [media1,media2,media3,media4];
    

    cov1 = cov(X(Y == dif(1),:)); numDatos1 = size(X(Y == dif(1)),1);
    cov2 = cov(X(Y == dif(2),:)); numDatos2 = size(X(Y == dif(2)),1);
    cov3 = cov(X(Y == dif(3),:)); numDatos3 = size(X(Y == dif(3)),1);
    cov4 = cov(X(Y == dif(4),:)); numDatos4 = size(X(Y == dif(4)),1);
    matricesCovarianzas(:,:,1) = cov1;
    matricesCovarianzas(:,:,2) = cov2;
    matricesCovarianzas(:,:,3) = cov3;
    matricesCovarianzas(:,:,4) = cov4;

    
    probPriori1 = numDatos1 / (numDatos1+numDatos2+numDatos3);
    probPriori2 = numDatos2 / (numDatos1+numDatos2+numDatos3);
    probPriori3 = numDatos3 / (numDatos1+numDatos2+numDatos3);
    probPriori4 = numDatos4 / (numDatos1+numDatos2+numDatos3);

    probabilidadPriori = [probPriori1,probPriori2,probPriori3,probPriori4]';

    
end