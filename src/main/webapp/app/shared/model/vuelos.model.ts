import { Moment } from 'moment';

export interface IVuelos {
  id?: number;
  nombre?: string;
  diasSemana?: string;
  fechaSalida?: Moment;
  fechaArribo9?: Moment;
  plazasVacias?: number;
  orden?: number;
  estadoVuelo?: number;
  esEscala?: boolean;
  aeropuertoSalidaNombre?: string;
  aeropuertoSalidaId?: number;
  aeropuertoArriboNombre?: string;
  aeropuertoArriboId?: number;
  idAvionNombre?: string;
  idAvionId?: number;
  idProgramaNombre?: string;
  idProgramaId?: number;
}

export class Vuelos implements IVuelos {
  constructor(
    public id?: number,
    public nombre?: string,
    public diasSemana?: string,
    public fechaSalida?: Moment,
    public fechaArribo9?: Moment,
    public plazasVacias?: number,
    public orden?: number,
    public estadoVuelo?: number,
    public esEscala?: boolean,
    public aeropuertoSalidaNombre?: string,
    public aeropuertoSalidaId?: number,
    public aeropuertoArriboNombre?: string,
    public aeropuertoArriboId?: number,
    public idAvionNombre?: string,
    public idAvionId?: number,
    public idProgramaNombre?: string,
    public idProgramaId?: number
  ) {
    this.esEscala = this.esEscala || false;
  }
}
