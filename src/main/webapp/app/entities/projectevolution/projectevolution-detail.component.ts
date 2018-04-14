import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Projectevolution } from './projectevolution.model';
import { ProjectevolutionService } from './projectevolution.service';

@Component({
    selector: 'jhi-projectevolution-detail',
    templateUrl: './projectevolution-detail.component.html'
})
export class ProjectevolutionDetailComponent implements OnInit, OnDestroy {

    projectevolution: Projectevolution;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private projectevolutionService: ProjectevolutionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjectevolutions();
    }

    load(id) {
        this.projectevolutionService.find(id).subscribe((projectevolution) => {
            this.projectevolution = projectevolution;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjectevolutions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectevolutionListModification',
            (response) => this.load(this.projectevolution.id)
        );
    }
}
