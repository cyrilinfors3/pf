import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Projectevolution } from './projectevolution.model';
import { ProjectevolutionPopupService } from './projectevolution-popup.service';
import { ProjectevolutionService } from './projectevolution.service';
import { Project, ProjectService } from '../project';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-projectevolution-dialog',
    templateUrl: './projectevolution-dialog.component.html'
})
export class ProjectevolutionDialogComponent implements OnInit {

    projectevolution: Projectevolution;
    isSaving: boolean;

    projects: Project[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private projectevolutionService: ProjectevolutionService,
        private projectService: ProjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projectService.query()
            .subscribe((res: ResponseWrapper) => { this.projects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        if (this.projectevolution.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projectevolutionService.update(this.projectevolution));
        } else {
            this.subscribeToSaveResponse(
                this.projectevolutionService.create(this.projectevolution));
        }
    }

    private subscribeToSaveResponse(result: Observable<Projectevolution>) {
        result.subscribe((res: Projectevolution) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Projectevolution) {
        this.eventManager.broadcast({ name: 'projectevolutionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-projectevolution-popup',
    template: ''
})
export class ProjectevolutionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectevolutionPopupService: ProjectevolutionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projectevolutionPopupService
                    .open(ProjectevolutionDialogComponent as Component, params['id']);
            } else {
                this.projectevolutionPopupService
                    .open(ProjectevolutionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
