import { IAeropuertos } from 'app/shared/model/aeropuertos.model';

export interface ICiudades {
  id?: number;
  ciudad?: string;
  pais?: string;
  aeropuertos?: IAeropuertos[];
}

export class Ciudades implements ICiudades {
  constructor(public id?: number, public ciudad?: string, public pais?: string, public aeropuertos?: IAeropuertos[]) {}
}
