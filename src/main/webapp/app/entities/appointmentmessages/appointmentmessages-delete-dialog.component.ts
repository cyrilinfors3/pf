import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Appointmentmessages } from './appointmentmessages.model';
import { AppointmentmessagesPopupService } from './appointmentmessages-popup.service';
import { AppointmentmessagesService } from './appointmentmessages.service';

@Component({
    selector: 'jhi-appointmentmessages-delete-dialog',
    templateUrl: './appointmentmessages-delete-dialog.component.html'
})
export class AppointmentmessagesDeleteDialogComponent {

    appointmentmessages: Appointmentmessages;

    constructor(
        private appointmentmessagesService: AppointmentmessagesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appointmentmessagesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'appointmentmessagesListModification',
                content: 'Deleted an appointmentmessages'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-appointmentmessages-delete-popup',
    template: ''
})
export class AppointmentmessagesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appointmentmessagesPopupService: AppointmentmessagesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.appointmentmessagesPopupService
                .open(AppointmentmessagesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
