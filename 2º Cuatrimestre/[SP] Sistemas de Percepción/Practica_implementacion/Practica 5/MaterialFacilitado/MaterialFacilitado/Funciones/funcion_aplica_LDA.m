function [Y_LDA,d12] = funcion_aplica_LDA(X,vMedias,Covarianza,probPriori,valoresClases)

      Z = X;
   % if size(valoresClases,1) ==3
        x1 = sym('x1','real'); x2 = sym('x2','real');x3 = sym('x3','real');
        X = [x1;x2;x3];
    %else 
        %x1 = sym('x1','real'); x2 = sym('x2','real');
        %X = [x1;x2];
   % end
    for i=1:size(valoresClases,1)
        v = valoresClases(i);
        d(1,1,i) = expand(-0.5*[X-vMedias(:,i)]'*pinv(Covarianza)*[X-vMedias(:,i)]+log(probPriori(i)));
    end

     d12 = d(1,1,1)-d(1,1,2);
    Y_LDA = [];
     for i=1:size(Z,1)
       x1 = Z(i,1); x2 = Z(i,2); x3 = Z(i,3);
       r1 = eval(d12);
       if r1 > 0
           Y_LDA = [Y_LDA;valoresClases(1)];
       else 
           Y_LDA = [Y_LDA;valoresClases(2)];
       end

      end


end