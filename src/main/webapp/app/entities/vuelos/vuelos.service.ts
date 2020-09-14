import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVuelos } from 'app/shared/model/vuelos.model';

type EntityResponseType = HttpResponse<IVuelos>;
type EntityArrayResponseType = HttpResponse<IVuelos[]>;

@Injectable({ providedIn: 'root' })
export class VuelosService {
  public resourceUrl = SERVER_API_URL + 'api/vuelos';

  constructor(protected http: HttpClient) {}

  create(vuelos: IVuelos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vuelos);
    return this.http
      .post<IVuelos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vuelos: IVuelos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vuelos);
    return this.http
      .put<IVuelos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVuelos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVuelos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vuelos: IVuelos): IVuelos {
    const copy: IVuelos = Object.assign({}, vuelos, {
      fechaSalida: vuelos.fechaSalida != null && vuelos.fechaSalida.isValid() ? vuelos.fechaSalida.toJSON() : null,
      fechaArribo9: vuelos.fechaArribo9 != null && vuelos.fechaArribo9.isValid() ? vuelos.fechaArribo9.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaSalida = res.body.fechaSalida != null ? moment(res.body.fechaSalida) : null;
      res.body.fechaArribo9 = res.body.fechaArribo9 != null ? moment(res.body.fechaArribo9) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vuelos: IVuelos) => {
        vuelos.fechaSalida = vuelos.fechaSalida != null ? moment(vuelos.fechaSalida) : null;
        vuelos.fechaArribo9 = vuelos.fechaArribo9 != null ? moment(vuelos.fechaArribo9) : null;
      });
    }
    return res;
  }
}
