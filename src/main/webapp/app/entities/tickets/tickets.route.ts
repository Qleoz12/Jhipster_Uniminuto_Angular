import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tickets } from 'app/shared/model/tickets.model';
import { TicketsService } from './tickets.service';
import { TicketsComponent } from './tickets.component';
import { TicketsDetailComponent } from './tickets-detail.component';
import { TicketsUpdateComponent } from './tickets-update.component';
import { TicketsDeletePopupComponent } from './tickets-delete-dialog.component';
import { ITickets } from 'app/shared/model/tickets.model';

@Injectable({ providedIn: 'root' })
export class TicketsResolve implements Resolve<ITickets> {
  constructor(private service: TicketsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITickets> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tickets>) => response.ok),
        map((tickets: HttpResponse<Tickets>) => tickets.body)
      );
    }
    return of(new Tickets());
  }
}

export const ticketsRoute: Routes = [
  {
    path: '',
    component: TicketsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.tickets.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TicketsDetailComponent,
    resolve: {
      tickets: TicketsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.tickets.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TicketsUpdateComponent,
    resolve: {
      tickets: TicketsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.tickets.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TicketsUpdateComponent,
    resolve: {
      tickets: TicketsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.tickets.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ticketsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TicketsDeletePopupComponent,
    resolve: {
      tickets: TicketsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.tickets.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
