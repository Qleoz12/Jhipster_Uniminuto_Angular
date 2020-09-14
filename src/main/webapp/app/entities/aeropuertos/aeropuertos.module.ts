import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { AeropuertosComponent } from './aeropuertos.component';
import { AeropuertosDetailComponent } from './aeropuertos-detail.component';
import { AeropuertosUpdateComponent } from './aeropuertos-update.component';
import { AeropuertosDeletePopupComponent, AeropuertosDeleteDialogComponent } from './aeropuertos-delete-dialog.component';
import { aeropuertosRoute, aeropuertosPopupRoute } from './aeropuertos.route';

const ENTITY_STATES = [...aeropuertosRoute, ...aeropuertosPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AeropuertosComponent,
    AeropuertosDetailComponent,
    AeropuertosUpdateComponent,
    AeropuertosDeleteDialogComponent,
    AeropuertosDeletePopupComponent
  ],
  entryComponents: [AeropuertosDeleteDialogComponent]
})
export class PruebaJhipsterAeropuertosModule {}
