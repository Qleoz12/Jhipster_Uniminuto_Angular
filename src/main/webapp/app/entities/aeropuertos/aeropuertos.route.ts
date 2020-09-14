import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Aeropuertos } from 'app/shared/model/aeropuertos.model';
import { AeropuertosService } from './aeropuertos.service';
import { AeropuertosComponent } from './aeropuertos.component';
import { AeropuertosDetailComponent } from './aeropuertos-detail.component';
import { AeropuertosUpdateComponent } from './aeropuertos-update.component';
import { AeropuertosDeletePopupComponent } from './aeropuertos-delete-dialog.component';
import { IAeropuertos } from 'app/shared/model/aeropuertos.model';

@Injectable({ providedIn: 'root' })
export class AeropuertosResolve implements Resolve<IAeropuertos> {
  constructor(private service: AeropuertosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAeropuertos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Aeropuertos>) => response.ok),
        map((aeropuertos: HttpResponse<Aeropuertos>) => aeropuertos.body)
      );
    }
    return of(new Aeropuertos());
  }
}

export const aeropuertosRoute: Routes = [
  {
    path: '',
    component: AeropuertosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.aeropuertos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AeropuertosDetailComponent,
    resolve: {
      aeropuertos: AeropuertosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aeropuertos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AeropuertosUpdateComponent,
    resolve: {
      aeropuertos: AeropuertosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aeropuertos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AeropuertosUpdateComponent,
    resolve: {
      aeropuertos: AeropuertosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aeropuertos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const aeropuertosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AeropuertosDeletePopupComponent,
    resolve: {
      aeropuertos: AeropuertosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aeropuertos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
