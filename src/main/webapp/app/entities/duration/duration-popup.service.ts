import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Duration } from './duration.model';
import { DurationService } from './duration.service';
@Injectable()
export class DurationPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private durationService: DurationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.durationService.find(id).subscribe((duration) => {
                if (duration.datecome) {
                    duration.datecome = {
                        year: duration.datecome.getFullYear(),
                        month: duration.datecome.getMonth() + 1,
                        day: duration.datecome.getDate()
                    };
                }
                if (duration.datego) {
                    duration.datego = {
                        year: duration.datego.getFullYear(),
                        month: duration.datego.getMonth() + 1,
                        day: duration.datego.getDate()
                    };
                }
                this.durationModalRef(component, duration);
            });
        } else {
            return this.durationModalRef(component, new Duration());
        }
    }

    durationModalRef(component: Component, duration: Duration): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.duration = duration;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
