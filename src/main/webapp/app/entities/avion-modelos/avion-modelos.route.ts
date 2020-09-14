import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AvionModelos } from 'app/shared/model/avion-modelos.model';
import { AvionModelosService } from './avion-modelos.service';
import { AvionModelosComponent } from './avion-modelos.component';
import { AvionModelosDetailComponent } from './avion-modelos-detail.component';
import { AvionModelosUpdateComponent } from './avion-modelos-update.component';
import { AvionModelosDeletePopupComponent } from './avion-modelos-delete-dialog.component';
import { IAvionModelos } from 'app/shared/model/avion-modelos.model';

@Injectable({ providedIn: 'root' })
export class AvionModelosResolve implements Resolve<IAvionModelos> {
  constructor(private service: AvionModelosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAvionModelos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AvionModelos>) => response.ok),
        map((avionModelos: HttpResponse<AvionModelos>) => avionModelos.body)
      );
    }
    return of(new AvionModelos());
  }
}

export const avionModelosRoute: Routes = [
  {
    path: '',
    component: AvionModelosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pruebaJhipsterApp.avionModelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AvionModelosDetailComponent,
    resolve: {
      avionModelos: AvionModelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.avionModelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AvionModelosUpdateComponent,
    resolve: {
      avionModelos: AvionModelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.avionModelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AvionModelosUpdateComponent,
    resolve: {
      avionModelos: AvionModelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.avionModelos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const avionModelosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AvionModelosDeletePopupComponent,
    resolve: {
      avionModelos: AvionModelosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pruebaJhipsterApp.avionModelos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
