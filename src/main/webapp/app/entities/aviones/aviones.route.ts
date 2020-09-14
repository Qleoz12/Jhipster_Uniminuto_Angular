import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Aviones } from 'app/shared/model/aviones.model';
import { AvionesService } from './aviones.service';
import { AvionesComponent } from './aviones.component';
import { AvionesDetailComponent } from './aviones-detail.component';
import { AvionesUpdateComponent } from './aviones-update.component';
import { AvionesDeletePopupComponent } from './aviones-delete-dialog.component';
import { IAviones } from 'app/shared/model/aviones.model';

@Injectable({ providedIn: 'root' })
export class AvionesResolve implements Resolve<IAviones> {
  constructor(private service: AvionesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAviones> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Aviones>) => response.ok),
        map((aviones: HttpResponse<Aviones>) => aviones.body)
      );
    }
    return of(new Aviones());
  }
}

export const avionesRoute: Routes = [
  {
    path: '',
    component: AvionesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.aviones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AvionesDetailComponent,
    resolve: {
      aviones: AvionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aviones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AvionesUpdateComponent,
    resolve: {
      aviones: AvionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aviones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AvionesUpdateComponent,
    resolve: {
      aviones: AvionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aviones.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const avionesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AvionesDeletePopupComponent,
    resolve: {
      aviones: AvionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.aviones.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
