import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'aviones',
        loadChildren: () => import('./aviones/aviones.module').then(m => m.PruebaJhipsterAvionesModule)
      },
      {
        path: 'avion-modelos',
        loadChildren: () => import('./avion-modelos/avion-modelos.module').then(m => m.PruebaJhipsterAvionModelosModule)
      },
      {
        path: 'clientes',
        loadChildren: () => import('./clientes/clientes.module').then(m => m.PruebaJhipsterClientesModule)
      },
      {
        path: 'tickets',
        loadChildren: () => import('./tickets/tickets.module').then(m => m.PruebaJhipsterTicketsModule)
      },
      {
        path: 'programas-vuelos',
        loadChildren: () => import('./programas-vuelos/programas-vuelos.module').then(m => m.PruebaJhipsterProgramasVuelosModule)
      },
      {
        path: 'vuelos',
        loadChildren: () => import('./vuelos/vuelos.module').then(m => m.PruebaJhipsterVuelosModule)
      },
      {
        path: 'aeropuertos',
        loadChildren: () => import('./aeropuertos/aeropuertos.module').then(m => m.PruebaJhipsterAeropuertosModule)
      },
      {
        path: 'ciudades',
        loadChildren: () => import('./ciudades/ciudades.module').then(m => m.PruebaJhipsterCiudadesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PruebaJhipsterEntityModule {}
