import { IAviones } from 'app/shared/model/aviones.model';

export interface IAvionModelos {
  id?: number;
  nombre?: string;
  capacidad?: number;
  aviones?: IAviones[];
}

export class AvionModelos implements IAvionModelos {
  constructor(public id?: number, public nombre?: string, public capacidad?: number, public aviones?: IAviones[]) {}
}
