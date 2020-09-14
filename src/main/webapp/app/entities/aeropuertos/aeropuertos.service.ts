import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAeropuertos } from 'app/shared/model/aeropuertos.model';

type EntityResponseType = HttpResponse<IAeropuertos>;
type EntityArrayResponseType = HttpResponse<IAeropuertos[]>;

@Injectable({ providedIn: 'root' })
export class AeropuertosService {
  public resourceUrl = SERVER_API_URL + 'api/aeropuertos';

  constructor(protected http: HttpClient) {}

  create(aeropuertos: IAeropuertos): Observable<EntityResponseType> {
    return this.http.post<IAeropuertos>(this.resourceUrl, aeropuertos, { observe: 'response' });
  }

  update(aeropuertos: IAeropuertos): Observable<EntityResponseType> {
    return this.http.put<IAeropuertos>(this.resourceUrl, aeropuertos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAeropuertos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAeropuertos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
