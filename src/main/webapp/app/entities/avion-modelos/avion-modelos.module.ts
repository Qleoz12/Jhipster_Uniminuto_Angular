import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { AvionModelosComponent } from './avion-modelos.component';
import { AvionModelosDetailComponent } from './avion-modelos-detail.component';
import { AvionModelosUpdateComponent } from './avion-modelos-update.component';
import { AvionModelosDeletePopupComponent, AvionModelosDeleteDialogComponent } from './avion-modelos-delete-dialog.component';
import { avionModelosRoute, avionModelosPopupRoute } from './avion-modelos.route';

const ENTITY_STATES = [...avionModelosRoute, ...avionModelosPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AvionModelosComponent,
    AvionModelosDetailComponent,
    AvionModelosUpdateComponent,
    AvionModelosDeleteDialogComponent,
    AvionModelosDeletePopupComponent
  ],
  entryComponents: [AvionModelosDeleteDialogComponent]
})
export class PruebaJhipsterAvionModelosModule {}
