import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { ClientesComponent } from './clientes.component';
import { ClientesDetailComponent } from './clientes-detail.component';
import { ClientesUpdateComponent } from './clientes-update.component';
import { ClientesDeletePopupComponent, ClientesDeleteDialogComponent } from './clientes-delete-dialog.component';
import { clientesRoute, clientesPopupRoute } from './clientes.route';

const ENTITY_STATES = [...clientesRoute, ...clientesPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClientesComponent,
    ClientesDetailComponent,
    ClientesUpdateComponent,
    ClientesDeleteDialogComponent,
    ClientesDeletePopupComponent
  ],
  entryComponents: [ClientesDeleteDialogComponent]
})
export class PruebaJhipsterClientesModule {}
