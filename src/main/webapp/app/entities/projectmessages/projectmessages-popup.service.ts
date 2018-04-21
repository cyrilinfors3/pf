import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Projectmessages } from './projectmessages.model';
import { ProjectmessagesService } from './projectmessages.service';

@Injectable()
export class ProjectmessagesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private projectmessagesService: ProjectmessagesService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.projectmessagesService.find(id).subscribe((projectmessages) => {
                    projectmessages.date = this.datePipe
                        .transform(projectmessages.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.projectmessagesModalRef(component, projectmessages);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.projectmessagesModalRef(component, new Projectmessages());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    projectmessagesModalRef(component: Component, projectmessages: Projectmessages): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.projectmessages = projectmessages;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
