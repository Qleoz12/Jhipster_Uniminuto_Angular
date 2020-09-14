import { ITickets } from 'app/shared/model/tickets.model';

export interface IProgramasVuelos {
  id?: number;
  nombre?: string;
  totalDias?: number;
  tickets?: ITickets[];
  aeropuertoSalidaNombre?: string;
  aeropuertoSalidaId?: number;
  aeropuertoArriboNombre?: string;
  aeropuertoArriboId?: number;
}

export class ProgramasVuelos implements IProgramasVuelos {
  constructor(
    public id?: number,
    public nombre?: string,
    public totalDias?: number,
    public tickets?: ITickets[],
    public aeropuertoSalidaNombre?: string,
    public aeropuertoSalidaId?: number,
    public aeropuertoArriboNombre?: string,
    public aeropuertoArriboId?: number
  ) {}
}
