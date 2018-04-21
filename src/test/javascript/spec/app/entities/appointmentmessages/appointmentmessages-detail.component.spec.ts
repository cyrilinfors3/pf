/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AppointmentmessagesDetailComponent } from '../../../../../../main/webapp/app/entities/appointmentmessages/appointmentmessages-detail.component';
import { AppointmentmessagesService } from '../../../../../../main/webapp/app/entities/appointmentmessages/appointmentmessages.service';
import { Appointmentmessages } from '../../../../../../main/webapp/app/entities/appointmentmessages/appointmentmessages.model';

describe('Component Tests', () => {

    describe('Appointmentmessages Management Detail Component', () => {
        let comp: AppointmentmessagesDetailComponent;
        let fixture: ComponentFixture<AppointmentmessagesDetailComponent>;
        let service: AppointmentmessagesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfTestModule],
                declarations: [AppointmentmessagesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AppointmentmessagesService,
                    JhiEventManager
                ]
            }).overrideTemplate(AppointmentmessagesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppointmentmessagesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppointmentmessagesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Appointmentmessages(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.appointmentmessages).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
