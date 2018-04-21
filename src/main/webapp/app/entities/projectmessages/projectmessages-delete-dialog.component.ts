import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Projectmessages } from './projectmessages.model';
import { ProjectmessagesPopupService } from './projectmessages-popup.service';
import { ProjectmessagesService } from './projectmessages.service';

@Component({
    selector: 'jhi-projectmessages-delete-dialog',
    templateUrl: './projectmessages-delete-dialog.component.html'
})
export class ProjectmessagesDeleteDialogComponent {

    projectmessages: Projectmessages;

    constructor(
        private projectmessagesService: ProjectmessagesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projectmessagesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'projectmessagesListModification',
                content: 'Deleted an projectmessages'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-projectmessages-delete-popup',
    template: ''
})
export class ProjectmessagesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectmessagesPopupService: ProjectmessagesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.projectmessagesPopupService
                .open(ProjectmessagesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
