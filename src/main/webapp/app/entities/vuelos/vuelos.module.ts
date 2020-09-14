import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { VuelosComponent } from './vuelos.component';
import { VuelosDetailComponent } from './vuelos-detail.component';
import { VuelosUpdateComponent } from './vuelos-update.component';
import { VuelosDeletePopupComponent, VuelosDeleteDialogComponent } from './vuelos-delete-dialog.component';
import { vuelosRoute, vuelosPopupRoute } from './vuelos.route';

const ENTITY_STATES = [...vuelosRoute, ...vuelosPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [VuelosComponent, VuelosDetailComponent, VuelosUpdateComponent, VuelosDeleteDialogComponent, VuelosDeletePopupComponent],
  entryComponents: [VuelosDeleteDialogComponent]
})
export class PruebaJhipsterVuelosModule {}
