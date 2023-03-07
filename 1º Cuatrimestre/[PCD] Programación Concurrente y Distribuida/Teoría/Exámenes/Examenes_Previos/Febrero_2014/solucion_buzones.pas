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

			if libreLaser=true then begin
				libreLaser=false;
				send(permiso[id], 1); //1 es laser
			end
			else 
				if libreTinta=true then begin
					libreTinta=false;
					send(permiso[id], 2); //2 es tinta
				end
				else
					if colaLaser<=colaTinta then begin
						colaLaser:=colaLaser+1;
						send(esperaLaser,id);
					end
					else begin
						colaTinta:=colaTinta+1;
						send(esperaTinta,id);
					end;
		or
			receive(soltar,id_impre);

			if id_impre=1 then
				if not empty(colaLaser) then begin
					colaLaser:=colaLaser-1;
					receive(esperaLaser,id);
					send(permiso[id], 1);
				end
				else
					libreLaser:=true;
			else
				if not empty(colaTinta) then begin
					colaTinta:=colaTinta-1;
					receive(esperaTinta,id);
					send(permiso[id], 2);
				end
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




