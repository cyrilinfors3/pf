/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProjectmessagesDetailComponent } from '../../../../../../main/webapp/app/entities/projectmessages/projectmessages-detail.component';
import { ProjectmessagesService } from '../../../../../../main/webapp/app/entities/projectmessages/projectmessages.service';
import { Projectmessages } from '../../../../../../main/webapp/app/entities/projectmessages/projectmessages.model';

describe('Component Tests', () => {

    describe('Projectmessages Management Detail Component', () => {
        let comp: ProjectmessagesDetailComponent;
        let fixture: ComponentFixture<ProjectmessagesDetailComponent>;
        let service: ProjectmessagesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfTestModule],
                declarations: [ProjectmessagesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProjectmessagesService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProjectmessagesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectmessagesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectmessagesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Projectmessages(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.projectmessages).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
