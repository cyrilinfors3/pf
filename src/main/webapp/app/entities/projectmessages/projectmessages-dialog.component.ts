import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Projectmessages } from './projectmessages.model';
import { ProjectmessagesPopupService } from './projectmessages-popup.service';
import { ProjectmessagesService } from './projectmessages.service';

@Component({
    selector: 'jhi-projectmessages-dialog',
    templateUrl: './projectmessages-dialog.component.html'
})
export class ProjectmessagesDialogComponent implements OnInit {

    projectmessages: Projectmessages;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private projectmessagesService: ProjectmessagesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.projectmessages.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projectmessagesService.update(this.projectmessages));
        } else {
            this.subscribeToSaveResponse(
                this.projectmessagesService.create(this.projectmessages));
        }
    }

    private subscribeToSaveResponse(result: Observable<Projectmessages>) {
        result.subscribe((res: Projectmessages) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Projectmessages) {
        this.eventManager.broadcast({ name: 'projectmessagesListModification', content: 'OK'});
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
    selector: 'jhi-projectmessages-popup',
    template: ''
})
export class ProjectmessagesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectmessagesPopupService: ProjectmessagesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projectmessagesPopupService
                    .open(ProjectmessagesDialogComponent as Component, params['id']);
            } else {
                this.projectmessagesPopupService
                    .open(ProjectmessagesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
