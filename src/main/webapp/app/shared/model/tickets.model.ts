import { Moment } from 'moment';

export interface ITickets {
  id?: number;
  nombre?: string;
  valor?: string;
  descuento?: number;
  estado?: boolean;
  fecha?: Moment;
  clientesNombres?: string;
  clientesId?: number;
  programasVuelosNombre?: string;
  programasVuelosId?: number;
}

export class Tickets implements ITickets {
  constructor(
    public id?: number,
    public nombre?: string,
    public valor?: string,
    public descuento?: number,
    public estado?: boolean,
    public fecha?: Moment,
    public clientesNombres?: string,
    public clientesId?: number,
    public programasVuelosNombre?: string,
    public programasVuelosId?: number
  ) {
    this.estado = this.estado || false;
  }
}
