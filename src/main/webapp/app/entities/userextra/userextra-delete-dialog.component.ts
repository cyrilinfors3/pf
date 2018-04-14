import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Userextra } from './userextra.model';
import { UserextraPopupService } from './userextra-popup.service';
import { UserextraService } from './userextra.service';

@Component({
    selector: 'jhi-userextra-delete-dialog',
    templateUrl: './userextra-delete-dialog.component.html'
})
export class UserextraDeleteDialogComponent {

    userextra: Userextra;

    constructor(
        private userextraService: UserextraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userextraService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userextraListModification',
                content: 'Deleted an userextra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-userextra-delete-popup',
    template: ''
})
export class UserextraDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userextraPopupService: UserextraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userextraPopupService
                .open(UserextraDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
