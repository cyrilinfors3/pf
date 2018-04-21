import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Projectmessages } from './projectmessages.model';
import { ProjectmessagesService } from './projectmessages.service';

@Component({
    selector: 'jhi-projectmessages-detail',
    templateUrl: './projectmessages-detail.component.html'
})
export class ProjectmessagesDetailComponent implements OnInit, OnDestroy {

    projectmessages: Projectmessages;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private projectmessagesService: ProjectmessagesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjectmessages();
    }

    load(id) {
        this.projectmessagesService.find(id).subscribe((projectmessages) => {
            this.projectmessages = projectmessages;
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

    registerChangeInProjectmessages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectmessagesListModification',
            (response) => this.load(this.projectmessages.id)
        );
    }
}
