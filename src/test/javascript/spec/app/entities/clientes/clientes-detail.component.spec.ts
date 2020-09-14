import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { ClientesDetailComponent } from 'app/entities/clientes/clientes-detail.component';
import { Clientes } from 'app/shared/model/clientes.model';

describe('Component Tests', () => {
  describe('Clientes Management Detail Component', () => {
    let comp: ClientesDetailComponent;
    let fixture: ComponentFixture<ClientesDetailComponent>;
    const route = ({ data: of({ clientes: new Clientes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [ClientesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClientesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClientesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clientes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
