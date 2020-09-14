import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Clientes } from 'app/shared/model/clientes.model';
import { ClientesService } from './clientes.service';
import { ClientesComponent } from './clientes.component';
import { ClientesDetailComponent } from './clientes-detail.component';
import { ClientesUpdateComponent } from './clientes-update.component';
import { ClientesDeletePopupComponent } from './clientes-delete-dialog.component';
import { IClientes } from 'app/shared/model/clientes.model';

@Injectable({ providedIn: 'root' })
export class ClientesResolve implements Resolve<IClientes> {
  constructor(private service: ClientesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClientes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Clientes>) => response.ok),
        map((clientes: HttpResponse<Clientes>) => clientes.body)
      );
    }
    return of(new Clientes());
  }
}

export const clientesRoute: Routes = [
  {
    path: '',
    component: ClientesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.clientes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClientesDetailComponent,
    resolve: {
      clientes: ClientesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.clientes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClientesUpdateComponent,
    resolve: {
      clientes: ClientesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.clientes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClientesUpdateComponent,
    resolve: {
      clientes: ClientesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.clientes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const clientesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClientesDeletePopupComponent,
    resolve: {
      clientes: ClientesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.clientes.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
