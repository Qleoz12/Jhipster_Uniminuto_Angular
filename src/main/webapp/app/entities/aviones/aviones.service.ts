import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAviones } from 'app/shared/model/aviones.model';

type EntityResponseType = HttpResponse<IAviones>;
type EntityArrayResponseType = HttpResponse<IAviones[]>;

@Injectable({ providedIn: 'root' })
export class AvionesService {
  public resourceUrl = SERVER_API_URL + 'api/aviones';

  constructor(protected http: HttpClient) {}

  create(aviones: IAviones): Observable<EntityResponseType> {
    return this.http.post<IAviones>(this.resourceUrl, aviones, { observe: 'response' });
  }

  update(aviones: IAviones): Observable<EntityResponseType> {
    return this.http.put<IAviones>(this.resourceUrl, aviones, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAviones>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAviones[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
