import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITickets } from 'app/shared/model/tickets.model';

type EntityResponseType = HttpResponse<ITickets>;
type EntityArrayResponseType = HttpResponse<ITickets[]>;

@Injectable({ providedIn: 'root' })
export class TicketsService {
  public resourceUrl = SERVER_API_URL + 'api/tickets';

  constructor(protected http: HttpClient) {}

  create(tickets: ITickets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tickets);
    return this.http
      .post<ITickets>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tickets: ITickets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tickets);
    return this.http
      .put<ITickets>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITickets>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITickets[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tickets: ITickets): ITickets {
    const copy: ITickets = Object.assign({}, tickets, {
      fecha: tickets.fecha != null && tickets.fecha.isValid() ? tickets.fecha.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tickets: ITickets) => {
        tickets.fecha = tickets.fecha != null ? moment(tickets.fecha) : null;
      });
    }
    return res;
  }
}
