export interface IAeropuertos {
  id?: number;
  nombre?: string;
  ciudadesCiudad?: string;
  ciudadesId?: number;
}

export class Aeropuertos implements IAeropuertos {
  constructor(public id?: number, public nombre?: string, public ciudadesCiudad?: string, public ciudadesId?: number) {}
}
