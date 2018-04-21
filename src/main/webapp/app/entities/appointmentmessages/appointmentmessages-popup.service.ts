import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Appointmentmessages } from './appointmentmessages.model';
import { AppointmentmessagesService } from './appointmentmessages.service';

@Injectable()
export class AppointmentmessagesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private appointmentmessagesService: AppointmentmessagesService

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
                this.appointmentmessagesService.find(id).subscribe((appointmentmessages) => {
                    appointmentmessages.date = this.datePipe
                        .transform(appointmentmessages.date, 'yyyy-MM-ddTHH:mm:ss');
                    appointmentmessages.createdate = this.datePipe
                        .transform(appointmentmessages.createdate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.appointmentmessagesModalRef(component, appointmentmessages);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.appointmentmessagesModalRef(component, new Appointmentmessages());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    appointmentmessagesModalRef(component: Component, appointmentmessages: Appointmentmessages): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.appointmentmessages = appointmentmessages;
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
