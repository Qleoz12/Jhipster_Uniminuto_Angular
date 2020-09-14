import { ITickets } from 'app/shared/model/tickets.model';

export interface IClientes {
  id?: number;
  nombres?: string;
  appelidos?: string;
  documentoTipo?: string;
  documentoNumero?: string;
  tickets?: ITickets[];
}

export class Clientes implements IClientes {
  constructor(
    public id?: number,
    public nombres?: string,
    public appelidos?: string,
    public documentoTipo?: string,
    public documentoNumero?: string,
    public tickets?: ITickets[]
  ) {}
}
