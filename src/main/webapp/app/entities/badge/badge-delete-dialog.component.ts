import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Badge } from './badge.model';
import { BadgePopupService } from './badge-popup.service';
import { BadgeService } from './badge.service';

@Component({
    selector: 'jhi-badge-delete-dialog',
    templateUrl: './badge-delete-dialog.component.html'
})
export class BadgeDeleteDialogComponent {

    badge: Badge;

    constructor(
        private badgeService: BadgeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.badgeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'badgeListModification',
                content: 'Deleted an badge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-badge-delete-popup',
    template: ''
})
export class BadgeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private badgePopupService: BadgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.badgePopupService
                .open(BadgeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
