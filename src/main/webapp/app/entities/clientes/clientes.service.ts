import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClientes } from 'app/shared/model/clientes.model';

type EntityResponseType = HttpResponse<IClientes>;
type EntityArrayResponseType = HttpResponse<IClientes[]>;

@Injectable({ providedIn: 'root' })
export class ClientesService {
  public resourceUrl = SERVER_API_URL + 'api/clientes';

  constructor(protected http: HttpClient) {}

  create(clientes: IClientes): Observable<EntityResponseType> {
    return this.http.post<IClientes>(this.resourceUrl, clientes, { observe: 'response' });
  }

  update(clientes: IClientes): Observable<EntityResponseType> {
    return this.http.put<IClientes>(this.resourceUrl, clientes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClientes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClientes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
