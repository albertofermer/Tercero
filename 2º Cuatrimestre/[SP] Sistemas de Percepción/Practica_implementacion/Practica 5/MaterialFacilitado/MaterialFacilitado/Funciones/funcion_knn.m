function YTest = funcion_knn(XTest,XTrain,YTrain,k)

    YTest=zeros(size(YTrain));

    [nf nc]=size(XTest);
    XTrain=XTrain';
    for i=1:nf
        XTest_i=XTest(i,:)';

        numDatosTrain=size(XTrain,2);

        XTest_i_amp=repmat(XTest_i,1,numDatosTrain);
        vectorDistancia=zeros(1,numDatosTrain);
        vectorDistancia=sqrt(sum((XTest_i_amp-XTrain).^2));
        [vectorDistanciaOrdenados, ind]=sort(vectorDistancia,'ascend');
        YTrainOrd=YTrain(ind);
        clasesKNN=YTrainOrd(1:k);

        valoresClasesKNN=unique(clasesKNN);
        conteoValoresClasesKNN=zeros(size(valoresClasesKNN));

        for j=1:length(valoresClasesKNN)
            conteoValoresClasesKNN(j)=sum(clasesKNN==valoresClasesKNN(j));
        end

        [maximo,indice]=max(conteoValoresClasesKNN);

        YTest(i,1)=valoresClasesKNN(indice);
    end


end