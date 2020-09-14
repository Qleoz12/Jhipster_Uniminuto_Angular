import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VuelosService } from 'app/entities/vuelos/vuelos.service';
import { IVuelos, Vuelos } from 'app/shared/model/vuelos.model';

describe('Service Tests', () => {
  describe('Vuelos Service', () => {
    let injector: TestBed;
    let service: VuelosService;
    let httpMock: HttpTestingController;
    let elemDefault: IVuelos;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(VuelosService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vuelos(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 0, 0, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaSalida: currentDate.format(DATE_TIME_FORMAT),
            fechaArribo9: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Vuelos', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaSalida: currentDate.format(DATE_TIME_FORMAT),
            fechaArribo9: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaSalida: currentDate,
            fechaArribo9: currentDate
          },
          returnedFromService
        );
        service
          .create(new Vuelos(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Vuelos', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            diasSemana: 'BBBBBB',
            fechaSalida: currentDate.format(DATE_TIME_FORMAT),
            fechaArribo9: currentDate.format(DATE_TIME_FORMAT),
            plazasVacias: 1,
            orden: 1,
            estadoVuelo: 1,
            esEscala: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaSalida: currentDate,
            fechaArribo9: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Vuelos', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            diasSemana: 'BBBBBB',
            fechaSalida: currentDate.format(DATE_TIME_FORMAT),
            fechaArribo9: currentDate.format(DATE_TIME_FORMAT),
            plazasVacias: 1,
            orden: 1,
            estadoVuelo: 1,
            esEscala: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaSalida: currentDate,
            fechaArribo9: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Vuelos', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
