import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { ProgramasVuelosComponent } from './programas-vuelos.component';
import { ProgramasVuelosDetailComponent } from './programas-vuelos-detail.component';
import { ProgramasVuelosUpdateComponent } from './programas-vuelos-update.component';
import { ProgramasVuelosDeletePopupComponent, ProgramasVuelosDeleteDialogComponent } from './programas-vuelos-delete-dialog.component';
import { programasVuelosRoute, programasVuelosPopupRoute } from './programas-vuelos.route';

const ENTITY_STATES = [...programasVuelosRoute, ...programasVuelosPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProgramasVuelosComponent,
    ProgramasVuelosDetailComponent,
    ProgramasVuelosUpdateComponent,
    ProgramasVuelosDeleteDialogComponent,
    ProgramasVuelosDeletePopupComponent
  ],
  entryComponents: [ProgramasVuelosDeleteDialogComponent]
})
export class PruebaJhipsterProgramasVuelosModule {}
