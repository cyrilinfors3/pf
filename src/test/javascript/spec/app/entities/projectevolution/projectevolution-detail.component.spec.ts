/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PfTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProjectevolutionDetailComponent } from '../../../../../../main/webapp/app/entities/projectevolution/projectevolution-detail.component';
import { ProjectevolutionService } from '../../../../../../main/webapp/app/entities/projectevolution/projectevolution.service';
import { Projectevolution } from '../../../../../../main/webapp/app/entities/projectevolution/projectevolution.model';

describe('Component Tests', () => {

    describe('Projectevolution Management Detail Component', () => {
        let comp: ProjectevolutionDetailComponent;
        let fixture: ComponentFixture<ProjectevolutionDetailComponent>;
        let service: ProjectevolutionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PfTestModule],
                declarations: [ProjectevolutionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProjectevolutionService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProjectevolutionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectevolutionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectevolutionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Projectevolution(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.projectevolution).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
