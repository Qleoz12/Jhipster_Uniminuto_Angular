import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvionModelos } from 'app/shared/model/avion-modelos.model';

type EntityResponseType = HttpResponse<IAvionModelos>;
type EntityArrayResponseType = HttpResponse<IAvionModelos[]>;

@Injectable({ providedIn: 'root' })
export class AvionModelosService {
  public resourceUrl = SERVER_API_URL + 'api/avion-modelos';

  constructor(protected http: HttpClient) {}

  create(avionModelos: IAvionModelos): Observable<EntityResponseType> {
    return this.http.post<IAvionModelos>(this.resourceUrl, avionModelos, { observe: 'response' });
  }

  update(avionModelos: IAvionModelos): Observable<EntityResponseType> {
    return this.http.put<IAvionModelos>(this.resourceUrl, avionModelos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvionModelos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvionModelos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
