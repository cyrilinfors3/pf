import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Appointmentmessages } from './appointmentmessages.model';
import { AppointmentmessagesPopupService } from './appointmentmessages-popup.service';
import { AppointmentmessagesService } from './appointmentmessages.service';

@Component({
    selector: 'jhi-appointmentmessages-dialog',
    templateUrl: './appointmentmessages-dialog.component.html'
})
export class AppointmentmessagesDialogComponent implements OnInit {

    appointmentmessages: Appointmentmessages;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private appointmentmessagesService: AppointmentmessagesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.appointmentmessages.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appointmentmessagesService.update(this.appointmentmessages));
        } else {
            this.subscribeToSaveResponse(
                this.appointmentmessagesService.create(this.appointmentmessages));
        }
    }

    private subscribeToSaveResponse(result: Observable<Appointmentmessages>) {
        result.subscribe((res: Appointmentmessages) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Appointmentmessages) {
        this.eventManager.broadcast({ name: 'appointmentmessagesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-appointmentmessages-popup',
    template: ''
})
export class AppointmentmessagesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appointmentmessagesPopupService: AppointmentmessagesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.appointmentmessagesPopupService
                    .open(AppointmentmessagesDialogComponent as Component, params['id']);
            } else {
                this.appointmentmessagesPopupService
                    .open(AppointmentmessagesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
