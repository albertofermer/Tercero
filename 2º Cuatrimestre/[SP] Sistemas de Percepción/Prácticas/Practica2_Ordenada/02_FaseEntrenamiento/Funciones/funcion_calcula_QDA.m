function [YQDA,d] = funcion_calcula_QDA(X_QDA,vectorMedias,mCov,Pprio,valoresClases)



YQDA = zeros(size(X_QDA,1),1);

X =[];
%%Variables simbolicas.
for i = 1:size(X_QDA,2)
    char = strcat('x',int2str(i));
    var = sym(char,'real');
    
    X = [X;var];
end


for i = 1:size(valoresClases)
    
    d(i)= -0.5 * (X-vectorMedias(:,i))' * pinv(mCov(:,:,i)) * (X-vectorMedias(:,i)) - 0.5 * log(det(mCov(:,:,i))) + log(Pprio(i));    
end

%x1 = XTest(i,1);x2=XTest(i,2);x3=XTest(i,3); valor = eval(d12);

%%Para cada dato




for i = 1:size(X_QDA,1)
    
    ecuacion= expand( subs( d',X,X_QDA(i,:)' ) );
%     x=1;
%     ecuacion = subs(d,X(x),X_QDA(i,x));%%Primera ec
%     %%Para cada variable simbolica:
%     for x = 2:size(X_QDA,2)
%         
%        ecuacion= expand(subs(ecuacion,X(x),X_QDA(i,x)));
%         
%     end
    [~,claseMax]=max(ecuacion);
    YQDA(i) = valoresClases(claseMax);
    
    
    
    
%         for j = 1:size(valoresClases)
% 
%             valoresD(j) = eval(d(j));
%             
%         end
%     
%         [~,claseMax]=max(valoresD);
%         YQDA(i) = valoresClase(claseMax);
%         
%     
    
    
end

end

