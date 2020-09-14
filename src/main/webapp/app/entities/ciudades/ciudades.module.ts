import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { CiudadesComponent } from './ciudades.component';
import { CiudadesDetailComponent } from './ciudades-detail.component';
import { CiudadesUpdateComponent } from './ciudades-update.component';
import { CiudadesDeletePopupComponent, CiudadesDeleteDialogComponent } from './ciudades-delete-dialog.component';
import { ciudadesRoute, ciudadesPopupRoute } from './ciudades.route';

const ENTITY_STATES = [...ciudadesRoute, ...ciudadesPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CiudadesComponent,
    CiudadesDetailComponent,
    CiudadesUpdateComponent,
    CiudadesDeleteDialogComponent,
    CiudadesDeletePopupComponent
  ],
  entryComponents: [CiudadesDeleteDialogComponent]
})
export class PruebaJhipsterCiudadesModule {}
