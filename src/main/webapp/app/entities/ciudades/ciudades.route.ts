import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ciudades } from 'app/shared/model/ciudades.model';
import { CiudadesService } from './ciudades.service';
import { CiudadesComponent } from './ciudades.component';
import { CiudadesDetailComponent } from './ciudades-detail.component';
import { CiudadesUpdateComponent } from './ciudades-update.component';
import { CiudadesDeletePopupComponent } from './ciudades-delete-dialog.component';
import { ICiudades } from 'app/shared/model/ciudades.model';

@Injectable({ providedIn: 'root' })
export class CiudadesResolve implements Resolve<ICiudades> {
  constructor(private service: CiudadesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICiudades> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Ciudades>) => response.ok),
        map((ciudades: HttpResponse<Ciudades>) => ciudades.body)
      );
    }
    return of(new Ciudades());
  }
}

export const ciudadesRoute: Routes = [
  {
    path: '',
    component: CiudadesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.ciudades.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CiudadesDetailComponent,
    resolve: {
      ciudades: CiudadesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.ciudades.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CiudadesUpdateComponent,
    resolve: {
      ciudades: CiudadesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.ciudades.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CiudadesUpdateComponent,
    resolve: {
      ciudades: CiudadesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.ciudades.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ciudadesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CiudadesDeletePopupComponent,
    resolve: {
      ciudades: CiudadesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.ciudades.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
