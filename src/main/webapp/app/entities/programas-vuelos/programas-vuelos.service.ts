import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProgramasVuelos } from 'app/shared/model/programas-vuelos.model';

type EntityResponseType = HttpResponse<IProgramasVuelos>;
type EntityArrayResponseType = HttpResponse<IProgramasVuelos[]>;

@Injectable({ providedIn: 'root' })
export class ProgramasVuelosService {
  public resourceUrl = SERVER_API_URL + 'api/programas-vuelos';

  constructor(protected http: HttpClient) {}

  create(programasVuelos: IProgramasVuelos): Observable<EntityResponseType> {
    return this.http.post<IProgramasVuelos>(this.resourceUrl, programasVuelos, { observe: 'response' });
  }

  update(programasVuelos: IProgramasVuelos): Observable<EntityResponseType> {
    return this.http.put<IProgramasVuelos>(this.resourceUrl, programasVuelos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProgramasVuelos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProgramasVuelos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
