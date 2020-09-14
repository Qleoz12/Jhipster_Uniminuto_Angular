import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vuelos } from 'app/shared/model/vuelos.model';
import { VuelosService } from './vuelos.service';
import { VuelosComponent } from './vuelos.component';
import { VuelosDetailComponent } from './vuelos-detail.component';
import { VuelosUpdateComponent } from './vuelos-update.component';
import { VuelosDeletePopupComponent } from './vuelos-delete-dialog.component';
import { IVuelos } from 'app/shared/model/vuelos.model';

@Injectable({ providedIn: 'root' })
export class VuelosResolve implements Resolve<IVuelos> {
  constructor(private service: VuelosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVuelos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Vuelos>) => response.ok),
        map((vuelos: HttpResponse<Vuelos>) => vuelos.body)
      );
    }
    return of(new Vuelos());
  }
}

export const vuelosRoute: Routes = [
  {
    path: '',
    component: VuelosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.vuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VuelosDetailComponent,
    resolve: {
      vuelos: VuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.vuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VuelosUpdateComponent,
    resolve: {
      vuelos: VuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.vuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VuelosUpdateComponent,
    resolve: {
      vuelos: VuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.vuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vuelosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VuelosDeletePopupComponent,
    resolve: {
      vuelos: VuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.vuelos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
