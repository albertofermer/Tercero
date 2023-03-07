function [vectorMedias, matrizCovarianza, probabilidadPriori] = funcion_ajusta_LDA (X,Y)
    dif = unique(Y);
    if size(unique(Y),1) == 3

    media1 = mean(X(Y == 1,:))';
    media2 = mean(X(Y == 2,:))';
    media3 = mean(X(Y == 3,:))';
    vectorMedias{1} = media1;
    vectorMedias{2} = media2;
    vectorMedias{3} = media3;

    cov1 = cov(X(Y == 1,:)); numDatos1 = size(X(Y == 1),1);
    cov2 = cov(X(Y == 2,:)); numDatos2 = size(X(Y == 2),1);
    cov3 = cov(X(Y == 3,:)); numDatos3 = size(X(Y == 3),1);
    matrizCovarianza = ((numDatos1-1)*cov1 + (numDatos2-1)*cov2 + (numDatos3-1)*cov3 )/ (numDatos1+numDatos2+numDatos3-3);

    
    probPriori1 = numDatos1 / (numDatos1+numDatos2+numDatos3);
    probPriori2 = numDatos2 / (numDatos1+numDatos2+numDatos3);
    probPriori3 = numDatos3 / (numDatos1+numDatos2+numDatos3);

    probabilidadPriori{1} = probPriori1;
    probabilidadPriori{2} = probPriori2;
    probabilidadPriori{3} = probPriori3;
    else
    media1 = mean(X(Y == dif(1),:))';
    media2 = mean(X(Y == dif(2),:))';

    vectorMedias= [media1];
    vectorMedias = [vectorMedias,media2];
    

    cov1 = cov(X(Y == dif(1),:)); numDatos1 = size(X(Y == dif(1)),1);
    cov2 = cov(X(Y == dif(2),:)); numDatos2 = size(X(Y == dif(2)),1);
    matrizCovarianza = ((numDatos1-1)*cov1 + (numDatos2-1)*cov2)/ (numDatos1+numDatos2-2);

    
    probPriori1 = numDatos1 / (numDatos1+numDatos2);
    probPriori2 = numDatos2 / (numDatos1+numDatos2);

    probabilidadPriori = [probPriori1];
    probabilidadPriori = [probPriori1,probPriori2]';

end