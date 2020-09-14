import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProgramasVuelos } from 'app/shared/model/programas-vuelos.model';
import { ProgramasVuelosService } from './programas-vuelos.service';
import { ProgramasVuelosComponent } from './programas-vuelos.component';
import { ProgramasVuelosDetailComponent } from './programas-vuelos-detail.component';
import { ProgramasVuelosUpdateComponent } from './programas-vuelos-update.component';
import { ProgramasVuelosDeletePopupComponent } from './programas-vuelos-delete-dialog.component';
import { IProgramasVuelos } from 'app/shared/model/programas-vuelos.model';

@Injectable({ providedIn: 'root' })
export class ProgramasVuelosResolve implements Resolve<IProgramasVuelos> {
  constructor(private service: ProgramasVuelosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProgramasVuelos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProgramasVuelos>) => response.ok),
        map((programasVuelos: HttpResponse<ProgramasVuelos>) => programasVuelos.body)
      );
    }
    return of(new ProgramasVuelos());
  }
}

export const programasVuelosRoute: Routes = [
  {
    path: '',
    component: ProgramasVuelosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.programasVuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProgramasVuelosDetailComponent,
    resolve: {
      programasVuelos: ProgramasVuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.programasVuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProgramasVuelosUpdateComponent,
    resolve: {
      programasVuelos: ProgramasVuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.programasVuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProgramasVuelosUpdateComponent,
    resolve: {
      programasVuelos: ProgramasVuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.programasVuelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const programasVuelosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProgramasVuelosDeletePopupComponent,
    resolve: {
      programasVuelos: ProgramasVuelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.programasVuelos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
