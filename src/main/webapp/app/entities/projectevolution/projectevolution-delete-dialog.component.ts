import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Projectevolution } from './projectevolution.model';
import { ProjectevolutionPopupService } from './projectevolution-popup.service';
import { ProjectevolutionService } from './projectevolution.service';

@Component({
    selector: 'jhi-projectevolution-delete-dialog',
    templateUrl: './projectevolution-delete-dialog.component.html'
})
export class ProjectevolutionDeleteDialogComponent {

    projectevolution: Projectevolution;

    constructor(
        private projectevolutionService: ProjectevolutionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projectevolutionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'projectevolutionListModification',
                content: 'Deleted an projectevolution'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-projectevolution-delete-popup',
    template: ''
})
export class ProjectevolutionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectevolutionPopupService: ProjectevolutionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.projectevolutionPopupService
                .open(ProjectevolutionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
