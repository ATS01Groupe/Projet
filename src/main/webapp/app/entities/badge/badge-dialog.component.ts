import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Badge } from './badge.model';
import { BadgePopupService } from './badge-popup.service';
import { BadgeService } from './badge.service';
import { Rooms, RoomsService } from '../rooms';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-badge-dialog',
    templateUrl: './badge-dialog.component.html'
})
export class BadgeDialogComponent implements OnInit {

    badge: Badge;
    authorities: any[];
    isSaving: boolean;

    rooms: Rooms[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private badgeService: BadgeService,
        private roomsService: RoomsService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.roomsService.query()
            .subscribe((res: ResponseWrapper) => { this.rooms = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.badge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.badgeService.update(this.badge));
        } else {
            this.subscribeToSaveResponse(
                this.badgeService.create(this.badge));
        }
    }

    private subscribeToSaveResponse(result: Observable<Badge>) {
        result.subscribe((res: Badge) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Badge) {
        this.eventManager.broadcast({ name: 'badgeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackRoomsById(index: number, item: Rooms) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-badge-popup',
    template: ''
})
export class BadgePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private badgePopupService: BadgePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.badgePopupService
                    .open(BadgeDialogComponent, params['id']);
            } else {
                this.modalRef = this.badgePopupService
                    .open(BadgeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
