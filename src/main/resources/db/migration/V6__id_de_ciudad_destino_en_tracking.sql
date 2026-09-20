-- El evento de creación del envío ya traía destinationCityId, pero la
-- proyección de tracking solo guardaba el texto de la ciudad ("BOGOTÁ -
-- CUNDINAMARCA"), no el identificador. Sin el id no se puede agrupar por
-- ciudad de forma confiable (comparar texto es frágil: tildes, mayúsculas).
--
-- Se agrega destination_city_id con el mismo patrón que origin_city_id: se
-- guarda el id además de la etiqueta ya existente.

alter table reports_shipment_tracking add column destination_city_id bigint;

update reports_shipment_tracking r
set destination_city_id = s.recipient_city_id
from shipments s
where s.tracking_number = r.tracking_number;

-- Una proyección puede tener filas que ya no existan como envío; se reconstruye con
-- POST /api/admin/reconstruir-proyecciones en lugar de bloquear la migración.
delete from reports_shipment_tracking where destination_city_id is null;

alter table reports_shipment_tracking alter column destination_city_id set not null;

alter table reports_shipment_tracking add constraint fk_reports_tracking_destination_city
    foreign key (destination_city_id) references cities (id);
