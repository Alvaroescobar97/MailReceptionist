select fecha
from envio
where cedulaEmisor = :cedulaEmisor
order by fecha desc
limit 1