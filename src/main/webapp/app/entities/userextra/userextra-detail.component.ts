import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Userextra } from './userextra.model';
import { UserextraService } from './userextra.service';

@Component({
    selector: 'jhi-userextra-detail',
    templateUrl: './userextra-detail.component.html'
})
export class UserextraDetailComponent implements OnInit, OnDestroy {

    userextra: Userextra;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private userextraService: UserextraService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserextras();
    }

    load(id) {
        this.userextraService.find(id).subscribe((userextra) => {
            this.userextra = userextra;
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

    registerChangeInUserextras() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userextraListModification',
            (response) => this.load(this.userextra.id)
        );
    }
}
