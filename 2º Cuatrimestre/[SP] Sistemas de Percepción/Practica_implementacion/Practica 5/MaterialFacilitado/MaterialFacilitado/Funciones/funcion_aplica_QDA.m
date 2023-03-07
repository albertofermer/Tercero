function Y_QDA = funcion_aplica_QDA(X,vMedias,mCovas,ProbPrioris,valoresClases)
    Z = X;

    x1 = sym('x1','real'); x2 = sym('x2','real');x3 = sym('x3','real');
    x4 = sym('x4','real'); x5 = sym('x5','real');x6 = sym('x6','real');
    x7 = sym('x7','real'); x8 = sym('x8','real');x9 = sym('x9','real');
    x10 = sym('x10','real'); x11 = sym('x11','real');x12 = sym('x12','real');
    x13 = sym('x13','real'); x14 = sym('x14','real');x15 = sym('x15','real');
    x16 = sym('x16','real'); x17 = sym('x17','real');x18 = sym('x18','real');
    x19 = sym('x19','real'); x20 = sym('x20','real');x21 = sym('x21','real');
    x22 = sym('x22','real');
    X = [x1;x2;x3;x4;x5;x6;x7;x8;x9;x10;x11;x12;x13;x14;x15;x16;x17;x18;x19;x20;x21;x22];
    for i=1:4
        %v = valoresClases(i);
        d(1,1,i) = -0.5*[X-vMedias(:,i)]'*pinv(mCovas(:,:,i))*[X-vMedias(:,i)]-0.5*log(det(mCovas(:,:,i)))*log(ProbPrioris(i));
    end
    
        d12 = d(1,1,1)-d(1,1,2);
        d13 = d(1,1,1)-d(1,1,3);
        d14 = d(1,1,1)-d(1,1,4);
        d23 = d(1,1,2)-d(1,1,3);
        d24 = d(1,1,2)-d(1,1,4);
        d34 = d(1,1,3)-d(1,1,4);
        save("funciones_decision_qda","d12","d13","d23","d14","d34","d24");

        Y_QDA = [];

       for i=1:size(Z,1)
         x1 = Z(i,1); x2 = Z(i,2); x3 = Z(i,3); x4 = Z(i,4);
         x5 = Z(i,5); x6 = Z(i,6); x7 = Z(i,7); x8 = Z(i,8);
         x9 = Z(i,9); x10 = Z(i,10); x11 = Z(i,11); x12 = Z(i,12);
         x13 = Z(i,13); x14 = Z(i,14); x15 = Z(i,15); x16 = Z(i,16);
         x17 = Z(i,17); x18 = Z(i,18); x19 = Z(i,19); x20 = Z(i,20);
         x21 = Z(i,21); x22 = Z(i,22); 
            r1 = eval(d12);
            r2 = eval(d13);
            r3 = eval(d23);
            r4 = eval(d14);
            r5 = eval(d24);
            r6 = eval(d34);
        
         if r1 > 0 && r2 > 0 && r4 > 0
            Y_QDA = [Y_QDA;2];
         elseif r1 < 0 && r3 > 0 && r5 > 0
            Y_QDA = [Y_QDA;3];
         elseif r2 <0 && r3 < 0 && r6 >0
            Y_QDA = [Y_QDA;4];
         else 
             Y_QDA = [Y_QDA;5];
         end
       end
  

    

end