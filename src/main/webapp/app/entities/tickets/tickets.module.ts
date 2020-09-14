import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruebaJhipsterSharedModule } from 'app/shared/shared.module';
import { TicketsComponent } from './tickets.component';
import { TicketsDetailComponent } from './tickets-detail.component';
import { TicketsUpdateComponent } from './tickets-update.component';
import { TicketsDeletePopupComponent, TicketsDeleteDialogComponent } from './tickets-delete-dialog.component';
import { ticketsRoute, ticketsPopupRoute } from './tickets.route';

const ENTITY_STATES = [...ticketsRoute, ...ticketsPopupRoute];

@NgModule({
  imports: [PruebaJhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TicketsComponent,
    TicketsDetailComponent,
    TicketsUpdateComponent,
    TicketsDeleteDialogComponent,
    TicketsDeletePopupComponent
  ],
  entryComponents: [TicketsDeleteDialogComponent]
})
export class PruebaJhipsterTicketsModule {}
