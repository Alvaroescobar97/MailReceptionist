update envio
set cedulaEmisor = :cedulaEmisor,
cedulaReceptor = :cedulaReceptor,
fecha = :fecha,
tipo = :tipo,
peso = :peso,
valor = :valor
where id = :id
