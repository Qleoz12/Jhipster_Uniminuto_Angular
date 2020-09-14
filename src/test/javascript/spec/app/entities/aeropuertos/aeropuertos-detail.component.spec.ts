import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AeropuertosDetailComponent } from 'app/entities/aeropuertos/aeropuertos-detail.component';
import { Aeropuertos } from 'app/shared/model/aeropuertos.model';

describe('Component Tests', () => {
  describe('Aeropuertos Management Detail Component', () => {
    let comp: AeropuertosDetailComponent;
    let fixture: ComponentFixture<AeropuertosDetailComponent>;
    const route = ({ data: of({ aeropuertos: new Aeropuertos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AeropuertosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AeropuertosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AeropuertosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aeropuertos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
