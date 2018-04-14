/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserextraDetailComponent } from '../../../../../../main/webapp/app/entities/userextra/userextra-detail.component';
import { UserextraService } from '../../../../../../main/webapp/app/entities/userextra/userextra.service';
import { Userextra } from '../../../../../../main/webapp/app/entities/userextra/userextra.model';

describe('Component Tests', () => {

    describe('Userextra Management Detail Component', () => {
        let comp: UserextraDetailComponent;
        let fixture: ComponentFixture<UserextraDetailComponent>;
        let service: UserextraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfTestModule],
                declarations: [UserextraDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserextraService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserextraDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserextraDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserextraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Userextra(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userextra).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
