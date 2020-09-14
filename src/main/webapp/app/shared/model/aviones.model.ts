export interface IAviones {
  id?: number;
  nombre?: string;
  avionModelosNombre?: string;
  avionModelosId?: number;
}

export class Aviones implements IAviones {
  constructor(public id?: number, public nombre?: string, public avionModelosNombre?: string, public avionModelosId?: number) {}
}
