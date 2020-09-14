import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { AvionesComponent } from './aviones.component';
import { AvionesDetailComponent } from './aviones-detail.component';
import { AvionesUpdateComponent } from './aviones-update.component';
import { AvionesDeletePopupComponent, AvionesDeleteDialogComponent } from './aviones-delete-dialog.component';
import { avionesRoute, avionesPopupRoute } from './aviones.route';

const ENTITY_STATES = [...avionesRoute, ...avionesPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AvionesComponent,
    AvionesDetailComponent,
    AvionesUpdateComponent,
    AvionesDeleteDialogComponent,
    AvionesDeletePopupComponent
  ],
  entryComponents: [AvionesDeleteDialogComponent]
})
export class PruebaJhipsterAvionesModule {}
