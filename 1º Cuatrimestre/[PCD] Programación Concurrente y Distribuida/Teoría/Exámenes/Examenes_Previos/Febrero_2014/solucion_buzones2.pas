//Tener en cuenta que si se hace una solución donde el proceso pregunta y luego se encola no será valida,
// porque durante la respuesta, la situación puede haber cambiado.

program Edicion;

const
	np=5;

var 
	pedir: mailbox of integer;
	soltar: mailbox of integer;
	esperaLaser: mailbox of integer;
	esperaTinta: mailbox of integer;
	permiso: array[1..5] of mailbox of integer;

process type P1(id:integer);
var
	id_impre:integer;
begin
	for i:=1 to 3 do	
	begin
		send(pedir, id);
		receive(permiso[id], id_impre)

		writeln('-->Proceso tipo P1 ',id,' usando impresora tipo ',id_impre);

		send(soltar, id_impre);
	end
end; 

process Controlador;
var
	id_impre:integer;
	id:integer;
	libreLaser:boolean;
	libreTinta:boolean;
	colaLaser:integer;
	colaTinta:integer;


begin
	libreLaser:=true;
	libreTinta:=true;
	colaLaser:=0;
	colaTinta:=0;

	repeat
		select
			receive(pedir,id);

			if colaLaser<=colaTinta then begin
				colaLaser:=colaLaser+1;
				send(esperaLaser,id);
			end
			else begin
				colaTinta:=colaTinta+1;
				send(esperaTinta,id);
			end;
		or
			when libreLaser=true ==>
				receive(esperaLaser,id);
				libreLaser=false;
				colaLaser:=colaLaser-1;
				send(permiso[id], 1); //1 es laser
		or
			when libreTinta=true ==>
				receive(esperaTinta,id);
				colaTinta:=colaTinta-1;
				libreTinta=false;
				send(permiso[id], 2); //2 es tinta
		or
			receive(soltar,id_impre);

			if id_impre=1 then
					libreLaser:=true;
			else
				libreTinta:=true;
				
		end select;
				
	forever
end;

var
   i: integer;
   PR1: array[1..np] of P1;

begin
  cobegin
	Controlador;
    for i := 1 to np do PR1[i](i);
  coend
end.




